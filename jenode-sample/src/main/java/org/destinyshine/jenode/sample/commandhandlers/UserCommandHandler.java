package org.destinyshine.jenode.sample.commandhandlers;

import org.destinyshine.commanding.CommandHandler;
import org.destinyshine.Subscribe;
import org.destinyshine.jenode.sample.command.UserPostCommand;

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
