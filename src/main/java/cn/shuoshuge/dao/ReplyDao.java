package cn.shuoshuge.dao;

import cn.shuoshuge.entity.Reply;
import cn.shuoshuge.entity.User;
import cn.shuoshuge.util.Config;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReplyDao {

    public void create_reply(Reply reply) {

        String sql = "insert into t_reply(topic_id,content,user_id) values(?,?,?)";
        DbHelp.update(sql,reply.getTopic_id(),reply.getContent(),reply.getUser_id());

    }


    public List<Reply> findAll(String id) {
        //tu.id=tr.user_id 不是用来做判断的，只是用来等值连接的，多表查询必须

        String sql = "select tu.id,tu.username,tu.avatar,tr.* from t_user tu,t_reply tr where tu.id=tr.user_id and tr.topic_id=? ";
        return DbHelp.query(sql, new AbstractListHandler<Reply>() {
            @Override
            protected Reply handleRow(ResultSet resultSet) throws SQLException {
                Reply reply = new BasicRowProcessor().toBean(resultSet,Reply.class);
                User user = new User();
                user.setAvatar(Config.get("qiniu.name") + resultSet.getString("avatar"));
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString("username"));
                reply.setUser(user);
                return reply;
            }
        }, Integer.valueOf(id));
    }
}
