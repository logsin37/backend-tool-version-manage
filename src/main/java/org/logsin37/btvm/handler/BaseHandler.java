package org.logsin37.btvm.handler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.logsin37.btvm.cmd.CommendLineProcessor;
import org.logsin37.btvm.constant.ErrorMessage;
import org.logsin37.btvm.di.ApplicationContext;
import org.logsin37.btvm.entity.Config;
import org.logsin37.btvm.entity.Module;
import org.logsin37.btvm.entity.Version;
import org.logsin37.btvm.util.Assert;

public class BaseHandler {

    public void handle(CommandLine cmd, Config config) {
        Assert.notNull(config, ErrorMessage.NOT_NULL, "config");
        Assert.notNull(cmd, ErrorMessage.NOT_NULL, "cmd");
        final List<String> cmdArgList = cmd.getArgList();

        if (cmd.hasOption("list")) {
            if(cmdArgList.contains("m") || cmdArgList.contains("module")) {
                listModules(config);
                ApplicationContext.stop();
                return;
            } else if(cmd.hasOption("module")){
                final String moduleOptValue = cmd.getOptionValue("module");
                listVersions(config, moduleOptValue);
                ApplicationContext.stop();
                return;
            }
        }

        // 如果不符合已有命令格式，提示“输入错误”并打印帮助文档
        System.err.println("Wrong Input");
        final CommendLineProcessor commandLineProcessor = ApplicationContext.getBean(CommendLineProcessor.class);
        commandLineProcessor.printHelpText();
        ApplicationContext.stop();
    }

    private void listModules(Config config) {
        Assert.notNull(config, ErrorMessage.NOT_NULL, "config");
        final List<org.logsin37.btvm.entity.Module> modules = Optional.ofNullable(config.getModules()).orElse(Collections.emptyList());
        System.out.println("Available modules:");
        for (org.logsin37.btvm.entity.Module module : modules) {
            System.out.println("- " + module.getName() + " (" + module.getShortName() + ") " + module.getDescription());
        }
    }

    private void listVersions(Config config, String moduleOptValue) {
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
