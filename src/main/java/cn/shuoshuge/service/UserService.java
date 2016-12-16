package cn.shuoshuge.service;

import cn.shuoshuge.dao.UserDao;
import cn.shuoshuge.entity.User;
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
//创建缓存区
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();


    public boolean judgeUsername(String username) {
//得到config中username键的值
        String name = Config.get("no.loginup.usernames");
        List<String> list = Arrays.asList(name.split(","));
        User user = userDao.findByUsername(username);
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
        user.setPassword(DigestUtils.md5Hex(password + Config.get("user.password.salt")));
        user.setEmail(email);
        user.setPhone(phone);
        user.setState(User.USER_STATE_UNACTIVE);
        user.setAvatar(User.DEFAULT_AVATAR);
        userDao.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String uuid = UUID.randomUUID().toString();
                String url = "http://shuoshuge.cn/login?" + uuid;
                cache.put(uuid,url);
                String html = "<h2>Dear," + username + "<a href='" + url +"'>请点击链接激活账号</a></h2>";

                EmailUtil.sendHtmlEmail(email,"用户激活文件",html);
            }
        });

        thread.start();

    }
}
