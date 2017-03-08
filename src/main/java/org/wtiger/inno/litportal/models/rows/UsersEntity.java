package org.wtiger.inno.litportal.models.rows;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
@Entity
@Table(name = "users", schema = "public", catalog = "litportal")
public class UsersEntity implements TableRow {
    private String login;
    private String password;
    private short role;
    private String email;
    private String visibleName;
    private UUID userUuid;
    private Collection<CommentsEntity> commentssByUserUuid;
    private Collection<PostsEntity> postssByUserUuid;

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role")
    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "visible_name")
    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    @Id
    @Column(name = "user_uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

        UsersEntity that = (UsersEntity) o;

        if (role != that.role) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (visibleName != null ? !visibleName.equals(that.visibleName) : that.visibleName != null) return false;
        if (userUuid != null ? !userUuid.equals(that.userUuid) : that.userUuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) role;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (visibleName != null ? visibleName.hashCode() : 0);
        result = 31 * result + (userUuid != null ? userUuid.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByUserUuid")
    public Collection<CommentsEntity> getCommentssByUserUuid() {
        return commentssByUserUuid;
    }

    public void setCommentssByUserUuid(Collection<CommentsEntity> commentssByUserUuid) {
        this.commentssByUserUuid = commentssByUserUuid;
    }

    @OneToMany(mappedBy = "usersByUserUuid")
    public Collection<PostsEntity> getPostssByUserUuid() {
        return postssByUserUuid;
    }

    public void setPostssByUserUuid(Collection<PostsEntity> postssByUserUuid) {
        this.postssByUserUuid = postssByUserUuid;
    }
}
