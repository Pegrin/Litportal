package org.wtiger.inno.litportal.models.pojo;

import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
public class GroupPojo implements Pojo {
    private UUID groupUuid;
    private UUID parentGroupUuid;
    private String head;
    private String body;
    private GroupPojo groupsByParentGroupUuid;
    private Integer version;

    public GroupPojo() {
    }

    public GroupPojo(UUID groupUuid, UUID parentGroupUuid, String head, String body) {
        this.groupUuid = groupUuid;
        this.parentGroupUuid = parentGroupUuid;
        this.head = head;
        this.body = body;
    }

    public UUID getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(UUID groupUuid) {
        this.groupUuid = groupUuid;
    }

    public UUID getParentGroupUuid() {
        return parentGroupUuid;
    }

    public void setParentGroupUuid(UUID parentGroupUuid) {
        this.parentGroupUuid = parentGroupUuid;
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

    public GroupPojo getGroupsByParentGroupUuid() {
        return groupsByParentGroupUuid;
    }

    public void setGroupsByParentGroupUuid(GroupPojo groupsByParentGroupUuid) {
        this.groupsByParentGroupUuid = groupsByParentGroupUuid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
