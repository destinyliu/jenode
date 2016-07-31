package org.destinyshine.jenode.jenode.sample.commandhandlers;

import org.destinyshine.jenode.commanding.CommandHandler;
import org.destinyshine.jenode.commanding.Subscribe;
import org.destinyshine.jenode.jenode.sample.command.UserPostCommand;

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
