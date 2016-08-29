package org.destinyshine.jenode.sample.command;

import org.destinyshine.jenode.commanding.Command;

/**
 * @author destinyliu
 */
public class UserPutCommand implements Command {

    private Long id;
    private String email;
    private String nickname;
    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getId() {
        return null;
    }

}
