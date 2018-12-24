package com.huyingbao.module.wan.ui.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class GitRepo {
    private int id;
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private GitUser owner;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public GitUser getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
