package org.wtiger.inno.litportal.models.pojo;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
public class CommentPojo implements Pojo {
    private UUID commentUuid;
    private UUID postUuid;
    private UUID parentCommentUuid;
    private String body;
    private Timestamp date;
    private UUID userUuid;
    private PostPojo postsByPostUuid;
    private CommentPojo commentsByParentCommentUuid;
    private UserPojo usersByUserUuid;
    private Integer version;

    public CommentPojo() {
    }

    public CommentPojo(UUID commentUuid, UUID postUuid, UUID parentCommentUuid, String body, Timestamp date, UUID userUuid) {
        this.commentUuid = commentUuid;
        this.postUuid = postUuid;
        this.parentCommentUuid = parentCommentUuid;
        this.body = body;
        this.date = date;
        this.userUuid = userUuid;
    }

    public UUID getCommentUuid() {
        return commentUuid;
    }

    public void setCommentUuid(UUID commentUuid) {
        this.commentUuid = commentUuid;
    }

    public UUID getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(UUID postUuid) {
        this.postUuid = postUuid;
    }

    public UUID getParentCommentUuid() {
        return parentCommentUuid;
    }

    public void setParentCommentUuid(UUID parentCommentUuid) {
        this.parentCommentUuid = parentCommentUuid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public PostPojo getPostsByPostUuid() {
        return postsByPostUuid;
    }

    public void setPostsByPostUuid(PostPojo postsByPostUuid) {
        this.postsByPostUuid = postsByPostUuid;
    }

    public CommentPojo getCommentsByParentCommentUuid() {
        return commentsByParentCommentUuid;
    }

    public void setCommentsByParentCommentUuid(CommentPojo commentsByParentCommentUuid) {
        this.commentsByParentCommentUuid = commentsByParentCommentUuid;
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
