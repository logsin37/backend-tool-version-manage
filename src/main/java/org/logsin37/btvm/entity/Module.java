package org.logsin37.btvm.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.util.Assert;
import org.logsin37.btvm.util.JSONUtil;

public class Module implements JsonEntity {

    @Override
    public void initFromJson(JSONObject jsonObject) {
        Assert.notNull(jsonObject, ErrorMessage.NOT_NULL, "module jsonObject");
        this.name = jsonObject.getString("name");
        this.shortName = JSONUtil.safeGetString(jsonObject, "shortName");
        this.description = JSONUtil.safeGetString(jsonObject,"description");
        if(jsonObject.has("defaultFlag")) {
            this.defaultFlag = JSONUtil.safeGetBoolean(jsonObject, "defaultFlag");
        }
        this.versions = new ArrayList<>();
        final JSONArray versionJsonArray = jsonObject.getJSONArray("versions");
        for (int i = 0; i < versionJsonArray.length(); i++) {
            final JSONObject versionJsonObject = versionJsonArray.getJSONObject(i);
            final Version version = new Version();
            version.initFromJson(versionJsonObject);
            this.versions.add(version);
        }
    }

    String name;
    String shortName;
    String description;
    boolean defaultFlag;
    List<Version> versions;

    public String getName() {
        return name;
    }

    public Module setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Module setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Module setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isDefaultFlag() {
        return defaultFlag;
    }

    public Module setDefaultFlag(boolean defaultFlag) {
        this.defaultFlag = defaultFlag;
        return this;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public Module setVersions(List<Version> versions) {
        this.versions = versions;
        return this;
    }

}
