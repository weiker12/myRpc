package com.weiker.myrpc.server;/**
 * @author zhengshangchao
 * @date 2018/10/26
 */

/**
 * @description: EchoService的接口实现类
 *
 * @author: zhengshangchao
 * @date: 2018-10-26 22:30
 **/
public class EchoServiceImpl implements EchoService {

    /**
     * 服务端实现
     *
     * @param ping
     * @return
     */
    @Override
    public String echo(String ping) {
        return ping != null ? ping + " -> success" : "failure";
    }
}
