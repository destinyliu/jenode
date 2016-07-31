package org.destinyshine.jenode.commanding;

import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.BlockingQueue;

/**
 * Created by fengmian on 16/7/30.
 */
public class InMemoryCommandObserver extends AbstractCommandObserver implements InitializingBean{

    private BlockingQueue<Command> commandQueue;

    public void setCommandQueue(BlockingQueue<Command> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    try {
                        Command command = commandQueue.take();
                        getCommandDispatcher().dispatch(command);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
