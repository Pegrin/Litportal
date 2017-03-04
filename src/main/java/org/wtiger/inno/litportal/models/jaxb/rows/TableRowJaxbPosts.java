package org.wtiger.inno.litportal.models.jaxb.rows;


import org.wtiger.inno.litportal.models.rows.TableRow;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Post")
@XmlType(propOrder = {"user_uuid", "group_uuid", "date", "commit"
        , "head", "body", "new_body_request", "author"})
public class TableRowJaxbPosts implements TableRow {
    private String post_uuid;
    private String group_uuid;
    private java.util.Date date;
    private String head;
    private String body;
    private String new_body_request;
    private String commit;
    private String user_uuid;
    private TableRowJaxbUsers author;

    @XmlAttribute
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

    public TableRowJaxbUsers getAuthor() {
        return author;
    }

    public void setAuthor(TableRowJaxbUsers author) {
        this.author = author;
    }
}
