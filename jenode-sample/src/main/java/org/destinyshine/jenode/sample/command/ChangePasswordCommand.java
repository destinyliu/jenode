package org.destinyshine.jenode.sample.command;

import org.destinyshine.jenode.commanding.Command;

/**
 * Created by fengmian on 16/8/27.
 */
public class ChangePasswordCommand implements Command{

    private Long id;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getAggregateRootId() {
        return null;
    }
}
