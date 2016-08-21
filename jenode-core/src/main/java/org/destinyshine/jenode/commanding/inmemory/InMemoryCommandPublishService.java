package org.destinyshine.jenode.commanding.inmemory;

import org.destinyshine.jenode.commanding.Command;
import org.destinyshine.jenode.commanding.CommandPublishService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by fengmian on 16/7/29.
 */
public class InMemoryCommandPublishService implements CommandPublishService {

    private BlockingQueue<Command> commandQueue;

    @Override
    public void publish(Command command) {
        try {
            commandQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCommandQueue(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }
}
