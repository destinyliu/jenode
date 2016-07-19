package commnadhandler;

import org.destinyshine.CommandHandler;

/**
 * @author destinyliu
 */
public class TestCommandHandler {

    @CommandHandler(TestCommand.class)
    public void handleTest(TestCommand testCommand) {
        System.out.println("do testCommand handler");
    }

}
