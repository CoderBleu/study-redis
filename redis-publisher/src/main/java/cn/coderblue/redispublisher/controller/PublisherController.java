package cn.coderblue.redispublisher.controller;

import cn.coderblue.redispublisher.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author coderblue
 */
@RestController
@RequestMapping("/publish")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    /**
     * push 消息
     * @param params
     * @return
     */
    @GetMapping("/pushMsg")
    public String pushMsg(String params){
        return publisherService.pushMsg(params);
    }
}
