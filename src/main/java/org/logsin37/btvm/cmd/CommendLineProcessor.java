package org.logsin37.btvm.cmd;

import org.apache.commons.cli.*;
import org.logsin37.btvm.di.ApplicationContext;

public class CommendLineProcessor {

    private final Options options;

    public CommendLineProcessor(Options options) {
        this.options = options;
    }

    public CommandLine parse(String[] args) {
        final CommandLineParser parser = new DefaultParser();
        final CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Parsing command failed. Reason: " + e.getMessage());
            ApplicationContext.stop();
            return null;
        }

        if (cmd.hasOption("help")) {
            printHelpText();
            ApplicationContext.stop();
            return null;
        }

        return cmd;
    }

    /**
     * 打印帮助文档
     */
    public void printHelpText() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("btvm", options);
    }

}
