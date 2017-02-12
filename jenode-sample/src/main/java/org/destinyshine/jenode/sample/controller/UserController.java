package org.destinyshine.jenode.sample.controller;

import org.destinyshine.jenode.commanding.CommandPublishService;
import org.destinyshine.jenode.sample.command.UserPostCommand;
import org.destinyshine.jenode.sample.dto.UserDTO;
import org.destinyshine.jenode.sample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by fengmian on 16/7/29.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private CommandPublishService commandPublishService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO post(@RequestBody UserDTO user) {
        commandPublishService.publish(new UserPostCommand());
        return user;
    }

    @RequestMapping(path = "/{user}", method = RequestMethod.GET)
    public User get(@PathVariable String user) {

        User u = new User("mylife@life.com", "mylile", "123");
        return u;
    }

}
