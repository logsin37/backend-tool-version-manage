package org.logsin37.btvm.util;

import org.json.JSONObject;

public class JSONUtil {

    public static String safeGetString(JSONObject jsonObject, String key) {
        if(jsonObject == null || key == null || key.isEmpty()) {
            return null;
        }
        return jsonObject.has(key) ? jsonObject.getString(key) : null;
    }

    public static boolean safeGetBoolean(JSONObject jsonObject, String key) {
        if(jsonObject == null || key == null || key.isEmpty()) {
            return false;
        }
        return jsonObject.has(key) && jsonObject.getBoolean(key);
    }
}
