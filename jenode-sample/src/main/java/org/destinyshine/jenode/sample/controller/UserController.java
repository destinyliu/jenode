package org.destinyshine.jenode.sample.controller;

import org.destinyshine.jenode.commanding.CommandPublishService;
import org.destinyshine.jenode.sample.command.UserPostCommand;
import org.destinyshine.jenode.sample.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by fengmian on 16/7/29.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private CommandPublishService commandPublishService;

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User post(@RequestBody User user) {
        commandPublishService.publish(new UserPostCommand());
        return user;
    }

    @RequestMapping(path = "/{user}", method = RequestMethod.GET)
    public User get(@PathVariable String user) {
        User u = new User();
        u.setNickname(user);
        u.setEmail("mylife@life.com");
        return u;
    }

}
