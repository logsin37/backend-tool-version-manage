package org.logsin37.btvm;

import org.apache.commons.cli.CommandLine;
import org.logsin37.btvm.cmd.CommendLineProcessor;
import org.logsin37.btvm.di.ApplicationContext;
import org.logsin37.btvm.entity.Config;
import org.logsin37.btvm.handler.BaseHandler;
import org.logsin37.btvm.repository.ConfigRepository;

public class Main {


    public static void main(String[] args) {

        ApplicationContext.init();

        final CommendLineProcessor commendLineProcessor = ApplicationContext.getBean(CommendLineProcessor.class);
        final CommandLine cmd = commendLineProcessor.parse(args);

        final ConfigRepository configRepository = ApplicationContext.getBean(ConfigRepository.class);
        final Config config = configRepository.loadConfig();

        final BaseHandler baseHandler = ApplicationContext.getBean(BaseHandler.class);
        baseHandler.handle(cmd, config);
    }
}