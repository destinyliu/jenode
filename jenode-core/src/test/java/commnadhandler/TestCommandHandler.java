package commnadhandler;

import org.destinyshine.CommandHandler;
import org.destinyshine.Subscribe;
import org.springframework.stereotype.Component;

/**
 * @author destinyliu
 */
@CommandHandler
public class TestCommandHandler {

    @Subscribe(TestCommand.class)
    public void handleTest(TestCommand testCommand) {
        System.out.println("do testCommand handler");
    }

}
