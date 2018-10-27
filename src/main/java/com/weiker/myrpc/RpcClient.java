package com.weiker.myrpc;

import com.weiker.myrpc.client.RpcImporter;
import com.weiker.myrpc.server.EchoService;
import com.weiker.myrpc.server.EchoServiceImpl;

import java.net.InetSocketAddress;

/**
 * @description: 客户端测试类
 *
 * @author: zhengshangchao
 * @date: 2018-10-27 13:59
 **/
public class RpcClient {

    public static void main(String[] args) {
        // 客户端服务
        RpcImporter<EchoService> importer = new RpcImporter<>();
        EchoService echoService = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8088));
        String result = echoService.echo("Hello RPC!");
        System.out.println(result);
    }
}
