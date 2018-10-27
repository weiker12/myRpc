package com.weiker.myrpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * @description: RPC客户端本地服务代理
 * @author: zhengshangchao
 * @date: 2018-10-27 12:17
 **/
public class RpcImporter<T> {

    public T importer(final Class<?> serviceClass, final InetSocketAddress addr) {
        return (T)Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{
                serviceClass.getInterfaces()[0]}, (proxy, method, args) -> {
            Socket socket = new Socket();
            ObjectOutputStream output = null;
            ObjectInputStream input = null;
            try {
                socket.connect(addr);
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeUTF(serviceClass.getName());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                output.writeObject(args);
                input = new ObjectInputStream(socket.getInputStream());
                return input.readObject();
            } finally {
                if (socket != null) {
                    socket.close();
                }
                if (output != null) {
                    output.close();
                }
                if (socket != null) {
                    socket.close();
                }
            }
        });
    }
}
