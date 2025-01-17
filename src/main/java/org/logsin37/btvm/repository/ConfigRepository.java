package org.logsin37.btvm.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.di.ApplicationContext;
import org.logsin37.btvm.entity.Config;
import org.logsin37.btvm.util.Assert;

public class ConfigRepository {

    private static final String DEFAULT_CONFIG_JSON_PATH = "src/main/resources/default_conf.json";
    private final String configJsonPath;

    public ConfigRepository(String configJsonPath) {
        Assert.notNull(configJsonPath, ErrorMessage.NOT_NULL ,"configJsonPath");
        this.configJsonPath = configJsonPath;
    }

    public Config loadConfig() {
        final String configJson;
        try {
            // 检查配置文件是否存在，若不存在则生成
            File configFile = new File(configJsonPath);
            if (!configFile.exists()) {
                final File parentFile = configFile.getParentFile();
                if (!parentFile.exists()) {
                    final boolean parentFileCreateResult = parentFile.mkdirs();
                    if (!parentFileCreateResult) {
                        System.err.println("Failed to create data directory: " + parentFile.getAbsolutePath());
                        ApplicationContext.stop();
                        return null;
                    }
                }
                String defaultConfig = new String(Files.readAllBytes(Paths.get(DEFAULT_CONFIG_JSON_PATH)));
                configJson = defaultConfig;
                try (FileWriter fileWriter = new FileWriter(configFile)) {
                    fileWriter.write(defaultConfig);
                }
            } else {
                configJson = new String(Files.readAllBytes(Paths.get(configJsonPath)));
            }
        } catch (IOException e) {
            System.err.println("Failed to read configuration file. Reason: " + e.getMessage());
            ApplicationContext.stop();
            return null;
        }

        final JSONObject configJsonObject = new JSONObject(configJson);
        final Config config = new Config();
        config.initFromJson(configJsonObject);
        return config;
    }
}
