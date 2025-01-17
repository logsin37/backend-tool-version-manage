package org.logsin37.btvm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.cli.*;
import org.json.JSONObject;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.entity.Config;
import org.logsin37.btvm.entity.Module;
import org.logsin37.btvm.entity.Version;
import org.logsin37.btvm.util.Assert;

public class Main {

    public static final String CONFIG_JSON_PATH = System.getProperty("user.home") + "/.btvm/conf.json";

    public static void main(String[] args) {
        final Options options = new Options();

        options.addOption("m", "module", false, "Set Maven version");
        options.addOption("h", "help", false, "Show help message");
        options.addOption("l", "list", false, "List modules or versions");

        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd;
        final List<String> cmdArgList;
        try {
            cmd = parser.parse(options, args);
            cmdArgList = cmd.getArgList();
        } catch (ParseException e) {
            System.err.println("Parsing command failed. Reason: " + e.getMessage());
            return;
        }

        if (cmd.hasOption("help")) {
            printHelpText(options);
            return;
        }

        final String configJson;
        try {
            // 检查配置文件是否存在，若不存在则生成
            File configFile = new File(CONFIG_JSON_PATH);
            if (!configFile.exists()) {
                final File parentFile = configFile.getParentFile();
                if (!parentFile.exists()) {
                    final boolean parentFileCreateResult = parentFile.mkdirs();
                    if (!parentFileCreateResult) {
                        System.err.println("Failed to create data directory: " + parentFile.getAbsolutePath());
                        return;
                    }
                }
                String defaultConfig = new String(Files.readAllBytes(Paths.get("src/main/resources/default_conf.json")));
                configJson = defaultConfig;
                try (FileWriter fileWriter = new FileWriter(configFile)) {
                    fileWriter.write(defaultConfig);
                }
            } else {
                configJson = new String(Files.readAllBytes(Paths.get(CONFIG_JSON_PATH)));
            }
        } catch (IOException e) {
            System.err.println("Failed to read configuration file. Reason: " + e.getMessage());
            return;
        }

        final JSONObject configJsonObject = new JSONObject(configJson);
        final Config config = new Config();
        config.initFromJson(configJsonObject);



        if (cmd.hasOption("list")) {
            if(cmdArgList.contains("m") || cmdArgList.contains("module")) {
                listModules(config);
                return;
            } else if(cmd.hasOption("module")){
                final String moduleOptValue = cmd.getOptionValue("module");
                listVersions(config, moduleOptValue);
                return;
            }
        }

        // 如果不符合已有命令格式，提示“输入错误”并打印帮助文档
        System.err.println("Wrong Input");
        printHelpText(options);
    }

    /**
     * 打印帮助文档
     * @param options      命令行参数
     */
    private static void printHelpText(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("btvm", options);
    }

    private static void listModules(Config config) {
        Assert.notNull(config, ErrorMessage.NOT_NULL, "config");
        final List<Module> modules = Optional.ofNullable(config.getModules()).orElse(Collections.emptyList());
        System.out.println("Available modules:");
        for (Module module : modules) {
            System.out.println("- " + module.getName() + " (" + module.getShortName() + ") " + module.getDescription());
        }
    }

    private static void listVersions(Config config, String moduleOptValue) {
        Assert.notNull(config, ErrorMessage.NOT_NULL, "config");
        final Module module;
        if(moduleOptValue == null || moduleOptValue.isEmpty()) {
            module = config.defaultModule();
            if(module == null) {
                System.err.println("module option value is empty and no default module found");
                return;
            }
        } else {
            module = config.getModules().stream()
                    .filter(m -> m.getName().equals(moduleOptValue) || m.getShortName().equals(moduleOptValue))
                    .findFirst()
                    .orElse(null);
            if(module == null) {
                System.err.println("invalid module name: " + moduleOptValue);
                return;
            }
        }
        final List<Version> versions = Optional.ofNullable(module.getVersions()).orElse(Collections.emptyList());
        System.out.println("Available versions for module " + module.getName() + " (" + module.getShortName() + ") " + module.getDescription() + " :");
        for (Version version : versions) {
            System.out.println("- " + version.getName() + " (" + version.getShortName() + ")");
        }
    }

}