package org.wtiger.inno.litportal.models.rows;

import java.util.Date;

public class TableRowComments implements TableRow {
    private String comment_uuid;
    private String post_uuid;
    private String parent_comment_uuid;
    private String body;
    private java.util.Date date;
    private String user_uuid;

    public TableRowComments(String comment_uuid, String post_uuid, String parent_comment_uuid, String body, Date date, String user_uuid) {
        this.comment_uuid = comment_uuid;
        this.post_uuid = post_uuid;
        this.parent_comment_uuid = parent_comment_uuid;
        this.body = body;
        this.date = date;
        this.user_uuid = user_uuid;
    }

    public String getComment_uuid() {
        return comment_uuid;
    }

    public void setComment_uuid(String comment_uuid) {
        this.comment_uuid = comment_uuid;
    }

    public String getPost_uuid() {
        return post_uuid;
    }

    public void setPost_uuid(String post_uuid) {
        this.post_uuid = post_uuid;
    }

    public String getParent_comment_uuid() {
        return parent_comment_uuid;
    }

    public void setParent_comment_uuid(String parent_comment_uuid) {
        this.parent_comment_uuid = parent_comment_uuid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
}
