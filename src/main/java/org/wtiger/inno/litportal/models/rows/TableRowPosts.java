package org.wtiger.inno.litportal.models.rows;


import java.util.Date;

public class TableRowPosts implements TableRow {
    private String post_uuid;
    private String group_uuid;
    private java.util.Date date;
    private String head;
    private String body;
    private String new_body_request;
    private String commit;
    private String user_uuid;
    private TableRowUsers author;

    public TableRowPosts() {

    }

    public TableRowPosts(String post_uuid, String group_uuid, Date date, String head, String body, String new_body_request, String commit, String user_uuid) {
        this.post_uuid = post_uuid;
        this.group_uuid = group_uuid;
        this.date = date;
        this.head = head;
        this.body = body;
        this.new_body_request = new_body_request;
        this.commit = commit;
        this.user_uuid = user_uuid;
    }

    public TableRowPosts(String post_uuid, String group_uuid, Date date, String head, String body, String new_body_request, String commit, String user_uuid, TableRowUsers author) {
        this.post_uuid = post_uuid;
        this.group_uuid = group_uuid;
        this.date = date;
        this.head = head;
        this.body = body;
        this.new_body_request = new_body_request;
        this.commit = commit;
        this.user_uuid = user_uuid;
        this.author = author;
    }

    public String getPost_uuid() {
        return post_uuid;
    }

    public void setPost_uuid(String post_uuid) {
        this.post_uuid = post_uuid;
    }

    public String getGroup_uuid() {
        return group_uuid;
    }

    public void setGroup_uuid(String group_uuid) {
        this.group_uuid = group_uuid;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNew_body_request() {
        return new_body_request;
    }

    public void setNew_body_request(String new_body_request) {
        this.new_body_request = new_body_request;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public TableRowUsers getAuthor() {
        return author;
    }

    public void setAuthor(TableRowUsers author) {
        this.author = author;
    }
}
