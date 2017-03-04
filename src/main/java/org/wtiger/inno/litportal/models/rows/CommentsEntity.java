package org.wtiger.inno.litportal.models.rows;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
@Entity
@Table(name = "comments", schema = "public", catalog = "litportal")
public class CommentsEntity implements TableRow {
    private UUID commentUuid;
    private UUID postUuid;
    private UUID parentCommentUuid;
    private String body;
    private Timestamp date;
    private UUID userUuid;
    private PostsEntity postsByPostUuid;
    private CommentsEntity commentsByParentCommentUuid;
    private Collection<CommentsEntity> commentssByCommentUuid;
    private UsersEntity usersByUserUuid;

    @Id
    @Column(name = "comment_uuid")
    public UUID getCommentUuid() {
        return commentUuid;
    }

    public void setCommentUuid(UUID commentUuid) {
        this.commentUuid = commentUuid;
    }

    @Basic
    @Column(name = "post_uuid", insertable = false, updatable = false)
    public UUID getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(UUID postUuid) {
        this.postUuid = postUuid;
    }

    @Basic
    @Column(name = "parent_comment_uuid", insertable = false, updatable = false)
    public UUID getParentCommentUuid() {
        return parentCommentUuid;
    }

    public void setParentCommentUuid(UUID parentCommentUuid) {
        this.parentCommentUuid = parentCommentUuid;
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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "user_uuid", insertable = false, updatable = false)
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

        CommentsEntity that = (CommentsEntity) o;

        if (commentUuid != null ? !commentUuid.equals(that.commentUuid) : that.commentUuid != null) return false;
        if (postUuid != null ? !postUuid.equals(that.postUuid) : that.postUuid != null) return false;
        if (parentCommentUuid != null ? !parentCommentUuid.equals(that.parentCommentUuid) : that.parentCommentUuid != null)
            return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (userUuid != null ? !userUuid.equals(that.userUuid) : that.userUuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentUuid != null ? commentUuid.hashCode() : 0;
        result = 31 * result + (postUuid != null ? postUuid.hashCode() : 0);
        result = 31 * result + (parentCommentUuid != null ? parentCommentUuid.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (userUuid != null ? userUuid.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "post_uuid", referencedColumnName = "post_uuid", nullable = false)
    public PostsEntity getPostsByPostUuid() {
        return postsByPostUuid;
    }

    public void setPostsByPostUuid(PostsEntity postsByPostUuid) {
        this.postsByPostUuid = postsByPostUuid;
    }

    @ManyToOne
    @JoinColumn(name = "parent_comment_uuid", referencedColumnName = "comment_uuid")
    public CommentsEntity getCommentsByParentCommentUuid() {
        return commentsByParentCommentUuid;
    }

    public void setCommentsByParentCommentUuid(CommentsEntity commentsByParentCommentUuid) {
        this.commentsByParentCommentUuid = commentsByParentCommentUuid;
    }

    @OneToMany(mappedBy = "commentsByParentCommentUuid")
    public Collection<CommentsEntity> getCommentssByCommentUuid() {
        return commentssByCommentUuid;
    }

    public void setCommentssByCommentUuid(Collection<CommentsEntity> commentssByCommentUuid) {
        this.commentssByCommentUuid = commentssByCommentUuid;
    }

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    public UsersEntity getUsersByUserUuid() {
        return usersByUserUuid;
    }

    public void setUsersByUserUuid(UsersEntity usersByUserUuid) {
        this.usersByUserUuid = usersByUserUuid;
    }
}
