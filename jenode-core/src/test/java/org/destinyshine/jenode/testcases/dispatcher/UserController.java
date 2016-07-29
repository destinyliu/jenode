package org.destinyshine.jenode.testcases.dispatcher;

import org.destinyshine.CommandPublishService;

import javax.annotation.Resource;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/11.
 *
 * @author jianyu.liu@hnlark.com
 */
//@Controller
//@RequestMapping
public class UserController {

    //@Resource
    private CommandPublishService commandPublishService;

    public void test() {
        commandPublishService.publish(null);
    }

}
