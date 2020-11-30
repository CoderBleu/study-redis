package cn.coderblue.redispublisher.service.impl;

import cn.coderblue.redispublisher.consts.Const;
import cn.coderblue.redispublisher.service.PublisherService;
import cn.coderblue.redispublisher.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author coderblue
 */
@Slf4j
@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    private RedisService redisService;

    @Override
    public String pushMsg(String params) {
        log.info(" 又开始发布消息 .......... ");
        // 直接使用convertAndSend方法即可向指定的通道发布消息
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                redisService.sendChannelMess(Const.CHANNEL, System.currentTimeMillis() + "：" + "redis消息队列-线程一");
            }
        }, "线程一测试").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                redisService.sendChannelMess(Const.CHANNEL, System.currentTimeMillis() + "：" + "redis消息队列-线程二");
            }
        }, "线程二测试").start();
        return "success";
    }
}

