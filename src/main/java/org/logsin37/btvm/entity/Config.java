package org.logsin37.btvm.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.util.Assert;

public class Config implements JsonEntity {

    public static final String FIELD_NAME_MODULES = "modules";

    @Override
    public void initFromJson(JSONObject jsonObject) {
        Assert.notNull(jsonObject, ErrorMessage.NOT_NULL, "config jsonObject");
        this.modules = new ArrayList<>();
        final JSONArray moduleJsonArray = jsonObject.getJSONArray(FIELD_NAME_MODULES);
        for(int i = 0; i < moduleJsonArray.length(); i++) {
            final JSONObject moduleJsonObject = moduleJsonArray.getJSONObject(i);
            Assert.notNull(moduleJsonObject, ErrorMessage.NOT_NULL, "module jsonObject");
            Module module = new Module();
            module.initFromJson(moduleJsonObject);
            this.modules.add(module);
        }
    }

    public Module defaultModule() {
        if(modules == null || modules.isEmpty()) {
            return null;
        }
        return modules.stream().filter(Module::isDefaultFlag).findFirst().orElse(modules.getFirst());
    }

    List<Module> modules;

    public List<Module> getModules() {
        return modules;
    }

    public Config setModules(List<Module> modules) {
        this.modules = modules;
        return this;
    }

}
