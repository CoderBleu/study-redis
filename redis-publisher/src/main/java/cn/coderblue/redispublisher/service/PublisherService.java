package cn.coderblue.redispublisher.service;

/**
 * @author coderblue
 */
public interface PublisherService {
    /**
     * 发布消息
     * @param params
     * @return
     */
    String pushMsg(String params);
}
