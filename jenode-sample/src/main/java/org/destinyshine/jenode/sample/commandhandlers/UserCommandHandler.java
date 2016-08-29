package org.destinyshine.jenode.sample.commandhandlers;

import org.destinyshine.jenode.commanding.context.CommandContext;
import org.destinyshine.jenode.commanding.CommandHandler;
import org.destinyshine.jenode.commanding.Subscribe;
import org.destinyshine.jenode.sample.command.ChangePasswordCommand;
import org.destinyshine.jenode.sample.command.UserPostCommand;
import org.destinyshine.jenode.sample.command.UserPutCommand;
import org.destinyshine.jenode.sample.dto.UserDTO;
import org.destinyshine.jenode.sample.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author destinyliu
 */
@CommandHandler
public class UserCommandHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Subscribe(UserPostCommand.class)
    public void handlePost(CommandContext<User> commandContext,
                           UserPostCommand postCommand) {
        logger.info("handle user post");
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname(postCommand.getNickname());
        userDTO.setEmail(postCommand.getEmail());
        userDTO.setPassword(postCommand.getPassword());
        commandContext.add(new User(userDTO));
    }

    @Subscribe(UserPutCommand.class)
    public void handlePut(CommandContext<User> commandContext,
                          User user,
                          UserPutCommand putCommand) {
        logger.info("handle user put");
        UserDTO dto = new UserDTO();
        dto.setPassword(putCommand.getPassword());
        user.update(dto);
    }

    @Subscribe(ChangePasswordCommand.class)
    public void changePassword(CommandContext<User> commandContext,
                               User user,
                               ChangePasswordCommand chPwdCmd) {
        user.changePassword(chPwdCmd.getPassword());
    }

}
