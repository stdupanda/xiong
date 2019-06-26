package cn.test.browser.e;

import java.io.Serializable;

public class Record implements Serializable {

    private static final long serialVersionUID = 1293218492548560704L;

    private Integer id;
    private Integer userId;
    private String title;
    private String url;

    private String gmtCreate;

    //
    @Override
    public String toString() {
        return "Record [id=" + id + ", userId=" + userId + ", title=" + title + ", url=" + url + ", gmtCreate="
                + gmtCreate + "]";
    }

    //
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
