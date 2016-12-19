package cn.shuoshuge.service;

import cn.shuoshuge.dao.LoginLogDao;
import cn.shuoshuge.dao.UserDao;
import cn.shuoshuge.entity.LoginLog;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.util.Config;
import cn.shuoshuge.util.EmailUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao = new UserDao();
    //发送激活邮件的TOKEN缓存
    private static Cache<String,Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();
    //在用户找回密码时，防止恶意发送邮件
    private static  Cache<String,Object> activeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60,TimeUnit.SECONDS)
            .build();
    //找回密码时的token
    private static Cache<String ,Object> passwordCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30,TimeUnit.MINUTES)
            .build();


    public boolean judgeUsername(String username) {
        //得到config中键no.loginup.usernames的值
        String name = Config.get("no.loginup.usernames");
        //创建一个String集合，asList是将一个数组转换为一个集合
        List<String> list = Arrays.asList(name.split(","));
        User user = userDao.findByUsername(username);
        //contains是看list集合中是否有该元素
        if(list.contains(username)) {
            return false;
        } else if (user == null) {
            return true;
        }
            return false;
    }


    public boolean judgeEmail(String email) {

        User user = userDao.findByEmail(email);
        if(user == null) {
            return true;
        } else {
            return false;
        }

    }

    public void save(String username, String password, String email, String phone) {

        User user = new User();
        user.setUsername(username);
        //对其密码加盐值，保证用户的账户安全
        user.setPassword(DigestUtils.md5Hex(password + Config.get("user.password.salt")));
        user.setEmail(email);
        user.setPhone(phone);
        user.setState(User.USER_STATE_UNACTIVE);
        user.setAvatar(User.DEFAULT_AVATAR);
        userDao.save(user);

        //提高用户体验，开启多个线程，使用户不必等太久
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一个token
                String uuid = UUID.randomUUID().toString();
                //激活邮件的跳转地址
                String url = "http://shuoshuge.cn/active?token=" + uuid;
                //存入缓存区,生命周期为六小时
                cache.put(uuid,username);
                //转换为html
                String html = "<h2>Dear," + username + "<a href='" + url +"'>请点击链接激活账号</a></h2>";
                //给用户发送电子邮件
                EmailUtil.sendHtmlEmail(email,"用户激活文件",html);
            }
        });
        //开启多线程
        thread.start();

    }

    public void activeUser(String token) {

        String username = (String) cache.getIfPresent(token);
        if(username == null) {
            throw new ServiceException("验证信息已失效或不存在");
        } else {
            User user = userDao.findByUsername(username);
            if(user == null) {
                throw new ServiceException("账户找不到");
            } else {
                user.setState(User.USER_STATE_ACTIVE);
                userDao.update(user);
                cache.invalidate(token);
            }
        }

    }

    public User validateUser(String username, String password,String ip) {

        User user = userDao.findByUsername(username);
        if(user == null) {
            throw new ServiceException("用户名或密码错误");
        } else if(!user.getPassword().equals(DigestUtils.md5Hex(password + Config.get("user.password.salt")))) {
            throw new ServiceException("用户名或密码错误");
        } else {
            if(user.getState().equals(User.USER_STATE_UNACTIVE)) {
                throw new ServiceException("账户未激活，请前往邮箱激活");
            } else if(user.getState().equals(User.USER_STATE_DISABLED)) {
                throw new ServiceException("账户已被禁用");
            } else {

                LoginLog loginLog = new LoginLog();
                loginLog.setIp(ip);
                loginLog.setUser_id(user.getId());
                LoginLogDao loginLogDao = new LoginLogDao();
                loginLogDao.save(loginLog);

                logger.info("{}登陆,ip:{}",username,ip);

                return user;
            }
        }
    }

    public void foundPassword(String value,String type,String sessionID) {
        if (activeCache.getIfPresent(sessionID) == null) {
            if ("phone".equals(type)) {
                //TODO 手机找回
            } else {
                User user = userDao.findByEmail(value);
                if (user == null) {
                    throw new ServiceException("邮箱地址输入有误");
                } else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://shuoshuge.cn/reset?token=" + uuid;
                            passwordCache.put(uuid,user);
                            String html = user.getUsername() + "<br>请点击链接<a href='"+ url +"'>重置密码</a><br>如果非本人请求请无视这封邮件";
                            EmailUtil.sendHtmlEmail(value,"重制密码邮件",html);
                        }
                    });
                    thread.start();
                }
            }
            activeCache.put(sessionID,"feng");
        } else {
            throw new ServiceException("操作频率过快，请稍后重试");
        }

    }

    public User foundUser(String token) {
        User user = (User) passwordCache.getIfPresent(token);
        if (user == null) {
            throw new ServiceException("此链接已失效，请重新发送邮件");
        } else {
            return user;
        }
    }

    public void reset(String token, String password) {
        User user = (User) passwordCache.getIfPresent(token);
        if(user == null) {
            throw new ServiceException("重置信息已失效，请重新找回");
        } else {
            user.setPassword(DigestUtils.md5Hex(password + Config.get("user.password.salt")));
            userDao.update(user);
            logger.info("{}修改了密码",user.getUsername());
        }
    }

    public void updateEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        userDao.update(user);
    }

    public void updatePassword(User user, String password, String newPassword) {
        String oldPassword = DigestUtils.md5Hex(password + Config.get("user.password.salt"));
        if (user != null) {
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(DigestUtils.md5Hex(newPassword + Config.get("user.password.salt")));
                userDao.update(user);
            } else {
                throw new ServiceException("请输入正确的原始密码");
            }
        } else {
            throw new ServiceException("请登录后再试");
        }
    }

    public void updateAtavar(User user, String fileKey) {

        user.setAvatar(fileKey);
        userDao.update(user);

    }
}
