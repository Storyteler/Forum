package cn.shuoshuge.entity;

import java.sql.Timestamp;

public class Topic {

    private Integer id;
    private String title;
    private String content;
    private Timestamp createtime;
    private Integer click_num;
    private Integer collect_num;
    private Integer thanks_num;
    private Integer reply_num;
    private Timestamp last_replytime;
    private Integer user_id;
    private Integer node_id;
    private User user;
    private Node node;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getClick_num() {
        return click_num;
    }

    public void setClick_num(Integer click_num) {
        this.click_num = click_num;
    }

    public Integer getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(Integer collect_num) {
        this.collect_num = collect_num;
    }

    public Integer getThanks_num() {
        return thanks_num;
    }

    public void setThanks_num(Integer thanks_num) {
        this.thanks_num = thanks_num;
    }

    public Integer getReply_num() {
        return reply_num;
    }

    public void setReply_num(Integer reply_num) {
        this.reply_num = reply_num;
    }

    public Timestamp getLast_replytime() {
        return last_replytime;
    }

    public void setLast_replytime(Timestamp last_replytime) {
        this.last_replytime = last_replytime;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getNode_id() {
        return node_id;
    }

    public void setNode_id(Integer node_id) {
        this.node_id = node_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
