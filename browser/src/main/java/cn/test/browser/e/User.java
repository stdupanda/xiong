package cn.test.browser.e;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -971417562593340214L;

    private Long id;
    private String name;
    private String password;
    private String gmtCreate;

    //
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password + ", gmtCreate=" + gmtCreate + "]";
    }

    //
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
