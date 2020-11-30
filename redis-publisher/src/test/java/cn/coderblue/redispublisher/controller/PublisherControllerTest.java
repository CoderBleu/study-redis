package cn.coderblue.redispublisher.controller;

import cn.coderblue.redispublisher.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublisherControllerTest {

    @Resource
    private PublisherService publisherService;

    @Test
    void pushMsg() {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                publisherService.pushMsg("redis消息队列");
            }
        }, "线程一测试").start();
    }
}