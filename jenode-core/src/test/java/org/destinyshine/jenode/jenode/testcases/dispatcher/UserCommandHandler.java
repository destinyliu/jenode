package org.destinyshine.jenode.jenode.testcases.dispatcher;

import org.destinyshine.jenode.commanding.CommandHandler;
import org.destinyshine.jenode.commanding.Subscribe;

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
