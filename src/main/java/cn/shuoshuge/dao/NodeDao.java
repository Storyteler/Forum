package cn.shuoshuge.dao;


import cn.shuoshuge.entity.Node;
import cn.shuoshuge.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class NodeDao {

    public Node findNodeByName(String name) {
        String sql = "select * from t_node where name = ?";
        return DbHelp.query(sql,new BeanHandler<>(Node.class),name);
    }

    public List<Node> findAll() {

        String sql = "select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<>(Node.class));
    }

    public Node findNodeById(Integer node_id) {
        String sql = "select * from t_node where id=?";
        return DbHelp.query(sql,new BeanHandler<>(Node.class),node_id);
    }
}
