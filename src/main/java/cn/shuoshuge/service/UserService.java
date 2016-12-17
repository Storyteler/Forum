package cn.shuoshuge.service;

import cn.shuoshuge.dao.UserDao;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.exception.ServiceException;
import cn.shuoshuge.util.Config;
import cn.shuoshuge.util.EmailUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserService {

    UserDao userDao = new UserDao();
    //发送激活邮件的TOKEN缓存
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
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

        String username = cache.getIfPresent(token);
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
}
