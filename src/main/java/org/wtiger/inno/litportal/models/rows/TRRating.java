package org.wtiger.inno.litportal.models.rows;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RatingRow")
@XmlType(propOrder = {"post_uuid", "user_uuid", "value"})
@Deprecated
public class TRRating {
    private String post_uuid;
    private Long value;
    private String user_uuid;

    public String getPost_uuid() {
        return post_uuid;
    }

    public void setPost_uuid(String post_uuid) {
        this.post_uuid = post_uuid;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
}
