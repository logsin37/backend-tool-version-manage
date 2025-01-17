package org.logsin37.btvm.di;

import org.apache.commons.cli.Options;
import org.logsin37.btvm.cmd.CommendLineProcessor;
import org.logsin37.btvm.handler.BaseHandler;
import org.logsin37.btvm.repository.ConfigRepository;

class ApplicationConfiguration {

    static void init() {
        ApplicationContext.registerBean(CommendLineProcessor.class, commendLineProcessor());
        ApplicationContext.registerBean(ConfigRepository.class, configRepository());
        ApplicationContext.registerBean(BaseHandler.class, baseHandler());
    }

    private static CommendLineProcessor commendLineProcessor() {
        Options options = new Options();
        options.addOption("m", "module", false, "Set Maven version");
        options.addOption("h", "help", false, "Show help message");
        options.addOption("l", "list", false, "List modules or versions");
        return new CommendLineProcessor(options);
    }

    private static ConfigRepository configRepository() {
        return new ConfigRepository(System.getProperty("user.home") + "/.btvm/conf.json");
    }

    private static BaseHandler baseHandler() {
        return new BaseHandler();
    }

}
