package org.wtiger.inno.litportal.models.jaxb.rows;

import org.wtiger.inno.litportal.models.hibernate.TableRow;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"login", "password", "email"
        , "visible_name", "role"})
public class TableRowJaxbUsers implements TableRow {
    private String login;
    private String password;
    private Long role;
    private String email;
    private String visible_name;
    @XmlAttribute
    private String user_uuid;

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

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVisible_name() {
        return visible_name;
    }

    public void setVisible_name(String visible_name) {
        this.visible_name = visible_name;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }
}
