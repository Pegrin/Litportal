package org.wtiger.inno.litportal.models.hibernate;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
@Entity
@Table(name = "posts", schema = "public", catalog = "litportal")
public class PostsEntity implements TableRow {
    private UUID postUuid;
    private UUID groupUuid;
    private Timestamp date;
    private String head;
    private String body;
    private String newBodyRequest;
    private Boolean commit;
    private UUID userUuid;
    private GroupsEntity groupsByGroupUuid;
    private UsersEntity usersByUserUuid;
    private int version;

    public PostsEntity() {
    }

    public PostsEntity(UUID postUuid, UUID groupUuid, Timestamp date, String head, String body, String newBodyRequest, Boolean commit, UUID userUuid) {
        this.postUuid = postUuid;
        this.groupUuid = groupUuid;
        this.date = date;
        this.head = head;
        this.body = body;
        this.newBodyRequest = newBodyRequest;
        this.commit = commit;
        this.userUuid = userUuid;
    }

    @Id
    @Column(name = "post_uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(UUID postUuid) {
        this.postUuid = postUuid;
    }

    @Basic
    @Column(name = "group_uuid")
    public UUID getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(UUID groupUuid) {
        this.groupUuid = groupUuid;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "head")
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Basic
    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Basic
    @Column(name = "new_body_request")
    public String getNewBodyRequest() {
        return newBodyRequest;
    }

    public void setNewBodyRequest(String newBodyRequest) {
        this.newBodyRequest = newBodyRequest;
    }

    @Basic
    @Column(name = "commit")
    public Boolean getCommit() {
        return commit;
    }

    public void setCommit(Boolean commit) {
        this.commit = commit;
    }

    @Basic
    @Column(name = "user_uuid", nullable = false)
    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostsEntity that = (PostsEntity) o;

        if (postUuid != null ? !postUuid.equals(that.postUuid) : that.postUuid != null) return false;
        if (groupUuid != null ? !groupUuid.equals(that.groupUuid) : that.groupUuid != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (head != null ? !head.equals(that.head) : that.head != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (newBodyRequest != null ? !newBodyRequest.equals(that.newBodyRequest) : that.newBodyRequest != null)
            return false;
        if (commit != null ? !commit.equals(that.commit) : that.commit != null) return false;
        if (userUuid != null ? !userUuid.equals(that.userUuid) : that.userUuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = postUuid != null ? postUuid.hashCode() : 0;
        result = 31 * result + (groupUuid != null ? groupUuid.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (newBodyRequest != null ? newBodyRequest.hashCode() : 0);
        result = 31 * result + (commit != null ? commit.hashCode() : 0);
        result = 31 * result + (userUuid != null ? userUuid.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "group_uuid", referencedColumnName = "group_uuid", insertable = false, updatable = false)
    public GroupsEntity getGroupsByGroupUuid() {
        return groupsByGroupUuid;
    }

    public void setGroupsByGroupUuid(GroupsEntity groupsByGroupUuid) {
        this.groupsByGroupUuid = groupsByGroupUuid;
    }

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", insertable = false, updatable = false)
    public UsersEntity getUsersByUserUuid() {
        return usersByUserUuid;
    }

    public void setUsersByUserUuid(UsersEntity usersByUserUuid) {
        this.usersByUserUuid = usersByUserUuid;
    }

    @Version
    @Column
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
