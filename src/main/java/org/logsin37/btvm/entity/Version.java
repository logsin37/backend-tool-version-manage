package org.logsin37.btvm.entity;

import org.json.JSONObject;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.util.Assert;
import org.logsin37.btvm.util.JSONUtil;

public class Version implements JsonEntity {

    @Override
    public void initFromJson(JSONObject jsonObject) {
        Assert.notNull(jsonObject, ErrorMessage.NOT_NULL, "version jsonObject");
        this.name = jsonObject.getString("name");
        this.shortName = JSONUtil.safeGetString(jsonObject, "shortName");
        this.homePath = jsonObject.getString("homePath");
        this.binPath = jsonObject.getString("binPath");
        this.createDate = JSONUtil.safeGetString(jsonObject, "createDate");
    }

    String name;
    String shortName;
    String homePath;
    String binPath;
    String createDate;

    public String getName() {
        return name;
    }

    public Version setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Version setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getHomePath() {
        return homePath;
    }

    public Version setHomePath(String homePath) {
        this.homePath = homePath;
        return this;
    }

    public String getBinPath() {
        return binPath;
    }

    public Version setBinPath(String binPath) {
        this.binPath = binPath;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Version setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

}
