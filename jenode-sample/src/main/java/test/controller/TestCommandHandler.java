package test.controller;

import org.destinyshine.Command;
import org.destinyshine.CommandHandler;

/**
 * @author destinyliu
 */
public class TestCommandHandler {

    @CommandHandler(TestCommand.class)
    public void handleTest(TestCommand testCommand) {

    }

}
