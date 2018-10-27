package com.weiker.myrpc.rpc;

import com.weiker.myrpc.server.RpcExporter;

/**
 * @description: 测试类
 *
 * @author: zhengshangchao
 * @date: 2018-10-27 13:14
 **/
public class RpcServer {

    public static void main(String[] args) {
        // 打开服务端线程监听socket
        new Thread(() -> {
            try{
                RpcExporter.exporter("localhost", 8088);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
