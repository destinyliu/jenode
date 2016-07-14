package test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import test.CommnadPublisher;

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
    private CommnadPublisher commandPublisher;

    public void test() {

    }

}
