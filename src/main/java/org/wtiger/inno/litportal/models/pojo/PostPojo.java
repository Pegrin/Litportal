package org.wtiger.inno.litportal.models.pojo;


import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
public class PostPojo implements Pojo {
    private UUID postUuid;
    private UUID groupUuid;
    private Timestamp date;
    private String head;
    private String body;
    private String newBodyRequest;
    private Boolean commit;
    private UUID userUuid;
    private GroupPojo groupsByGroupUuid;
    private UserPojo usersByUserUuid;
    private Integer version;

    public PostPojo() {
    }

    public PostPojo(UUID postUuid, UUID groupUuid, Timestamp date, String head, String body, String newBodyRequest, Boolean commit, UUID userUuid) {
        this.postUuid = postUuid;
        this.groupUuid = groupUuid;
        this.date = date;
        this.head = head;
        this.body = body;
        this.newBodyRequest = newBodyRequest;
        this.commit = commit;
        this.userUuid = userUuid;
    }

    public UUID getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(UUID postUuid) {
        this.postUuid = postUuid;
    }

    public UUID getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(UUID groupUuid) {
        this.groupUuid = groupUuid;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    public String getNewBodyRequest() {
        return newBodyRequest;
    }

    public void setNewBodyRequest(String newBodyRequest) {
        this.newBodyRequest = newBodyRequest;
    }

    public Boolean getCommit() {
        return commit;
    }

    public void setCommit(Boolean commit) {
        this.commit = commit;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public GroupPojo getGroupsByGroupUuid() {
        return groupsByGroupUuid;
    }

    public void setGroupsByGroupUuid(GroupPojo groupsByGroupUuid) {
        this.groupsByGroupUuid = groupsByGroupUuid;
    }

    public UserPojo getUsersByUserUuid() {
        return usersByUserUuid;
    }

    public void setUsersByUserUuid(UserPojo usersByUserUuid) {
        this.usersByUserUuid = usersByUserUuid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
