package org.destinyshine.jenode.sample.model;

import org.destinyshine.jenode.sample.dto.UserDTO;

/**
 * Created by fengmian on 16/7/29.
 */
public class User {

    private String email;
    private String nickname;
    private String password;

    public User() {

    }

    public User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.nickname = userDTO.getNickname();
        this.password = userDTO.getPassword();
    }

    public void update(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.nickname = userDTO.getNickname();
    }

    public void changePassword(String password) {
        this.password = encrptPassword(password);
    }

    private String encrptPassword(String password) {
        return password;
    }

}
