package org.destinyshine.jenode.sample.controller;

import org.destinyshine.jenode.sample.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fengmian on 16/7/29.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping(path = "", method = RequestMethod.POST)
    public User post(User user) {
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
