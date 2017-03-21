package org.wtiger.inno.litportal.models.pojo;

import java.util.UUID;

/**
 * Created by olymp on 04.03.2017.
 */
public class UserPojo implements Pojo {
    private String login;
    private String password;
    private Short role;
    private String email;
    private String visibleName;
    private UUID userUuid;
    private Integer version;
    private Boolean enabled;

    public UserPojo() {
    }

    public UserPojo(UUID userUuid, String login, String password,
                    Short role, String email, String visibleName, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.visibleName = visibleName;
        this.userUuid = userUuid;
        this.enabled = enabled;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
