package org.wtiger.inno.litportal.models.rows;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
@Entity
@Table(name = "groups", schema = "public", catalog = "litportal")
public class GroupsEntity implements TableRow {
    private UUID groupUuid;
    private UUID parentGroupUuid;
    private String head;
    private String body;
    private GroupsEntity groupsByParentGroupUuid;
    private Collection<GroupsEntity> groupssByGroupUuid;
    private Collection<PostsEntity> postssByGroupUuid;

    @Id
    @Column(name = "group_uuid")
    public UUID getGroupUuid() {
        return groupUuid;
    }

    public void setGroupUuid(UUID groupUuid) {
        this.groupUuid = groupUuid;
    }

    @Basic
    @Column(name = "parent_group_uuid", insertable = false, updatable = false)
    public UUID getParentGroupUuid() {
        return parentGroupUuid;
    }

    public void setParentGroupUuid(UUID parentGroupUuid) {
        this.parentGroupUuid = parentGroupUuid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupsEntity that = (GroupsEntity) o;

        if (groupUuid != null ? !groupUuid.equals(that.groupUuid) : that.groupUuid != null) return false;
        if (parentGroupUuid != null ? !parentGroupUuid.equals(that.parentGroupUuid) : that.parentGroupUuid != null)
            return false;
        if (head != null ? !head.equals(that.head) : that.head != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupUuid != null ? groupUuid.hashCode() : 0;
        result = 31 * result + (parentGroupUuid != null ? parentGroupUuid.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "parent_group_uuid", referencedColumnName = "group_uuid")
    public GroupsEntity getGroupsByParentGroupUuid() {
        return groupsByParentGroupUuid;
    }

    public void setGroupsByParentGroupUuid(GroupsEntity groupsByParentGroupUuid) {
        this.groupsByParentGroupUuid = groupsByParentGroupUuid;
    }

    @OneToMany(mappedBy = "groupsByParentGroupUuid")
    public Collection<GroupsEntity> getGroupssByGroupUuid() {
        return groupssByGroupUuid;
    }

    public void setGroupssByGroupUuid(Collection<GroupsEntity> groupssByGroupUuid) {
        this.groupssByGroupUuid = groupssByGroupUuid;
    }

    @OneToMany(mappedBy = "groupsByGroupUuid")
    public Collection<PostsEntity> getPostssByGroupUuid() {
        return postssByGroupUuid;
    }

    public void setPostssByGroupUuid(Collection<PostsEntity> postssByGroupUuid) {
        this.postssByGroupUuid = postssByGroupUuid;
    }
}
