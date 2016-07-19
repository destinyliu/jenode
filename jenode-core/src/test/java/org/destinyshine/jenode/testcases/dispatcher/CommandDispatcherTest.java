package org.destinyshine.jenode.testcases.dispatcher;

import org.destinyshine.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author destinyliu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/CommandDispatcher.xml")
public class CommandDispatcherTest {

    @Resource
    CommandDispatcher commandDispatcher;

    @Test
    public void test() throws Exception {
        commandDispatcher.dispatch(new UserPostCommand());
    }

}
