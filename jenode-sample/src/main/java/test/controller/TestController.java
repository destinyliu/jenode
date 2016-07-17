package test.controller;

import org.destinyshine.CommandPublishService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by jianyu.liu@hnlark.com on 2016/7/11.
 *
 * @author jianyu.liu@hnlark.com
 */
@Controller
@RequestMapping
public class TestController {

    @Resource
    private CommandPublishService commandPublishService;

    public void test() {
        commandPublishService.publish(null);
    }

}
