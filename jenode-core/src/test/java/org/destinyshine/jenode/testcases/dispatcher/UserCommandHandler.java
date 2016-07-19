package org.destinyshine.jenode.testcases.dispatcher;

import org.destinyshine.CommandHandler;
import org.destinyshine.Subscribe;

/**
 * @author destinyliu
 */
@CommandHandler
public class UserCommandHandler {

    @Subscribe(UserPostCommand.class)
    public void handleTest(UserPostCommand testCommand) {
        System.out.println("handle user post");
    }

}
