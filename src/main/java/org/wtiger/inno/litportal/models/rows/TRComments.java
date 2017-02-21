package org.wtiger.inno.litportal.models.rows;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Comment")
@XmlType(propOrder = {"user_uuid", "post_uuid", "parent_comment_uuid", "date", "body"})
public class TRComments {
    private String comment_uuid;
    private String post_uuid;
    private String parent_comment_uuid;
    private String body;
    private java.util.Date date;
    private String user_uuid;

    @XmlAttribute
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
