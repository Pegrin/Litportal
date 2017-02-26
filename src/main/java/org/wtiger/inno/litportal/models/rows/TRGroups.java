package org.wtiger.inno.litportal.models.rows;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Group")
@XmlType(propOrder = {"parent_group_uuid", "head", "body"})

public class TRGroups implements TableRow {
    private String group_uuid;
    private String parent_group_uuid;
    private String head;
    private String body;

    @XmlAttribute
    public String getGroup_uuid() {
        return group_uuid;
    }

    public void setGroup_uuid(String group_uuid) {
        this.group_uuid = group_uuid;
    }

    public String getParent_group_uuid() {
        return parent_group_uuid;
    }

    public void setParent_group_uuid(String parent_group_uuid) {
        this.parent_group_uuid = parent_group_uuid;
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
}
