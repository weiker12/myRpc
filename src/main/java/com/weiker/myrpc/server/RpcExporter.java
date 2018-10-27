package com.weiker.myrpc.server;/**
 * @author zhengshangchao
 * @date 2018/10/26
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description: RPC服务端代码发布者代码
 * @author: zhengshangchao
 * @date: 2018-10-26 22:50
 **/
public class RpcExporter {

    /**
     * 手动创建线程池
     */
    public static Executor excutor = Executors.newFixedThreadPool(2);

    /**
     * 监听客户端的TCP连接，收到客户端连接就交给线程池执行
     * 将客户端发送的码流序列化成对象，反射调用服务实现者，获取执行结果
     * 将执行结果对象反序列化，通过socket发送黑客户端
     * 远程服务调用结束后要释放socket等连接资源，防止内存泄露
     *
     * @param hostName 客户端地址
     * @param port 客户端端口号
     * @throws Exception
     */
    public static void exporter(String hostName, int port) throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(hostName, port));
        try {
            while (true) {
                excutor.execute(() -> {
                    ObjectInputStream input = null;
                    ObjectOutputStream output = null;
                    try {
                        Socket client = server.accept();
                        input = new ObjectInputStream(client.getInputStream());
                        String interfaceName = input.readUTF();
                        Class<?> service = Class.forName(interfaceName);
                        String methodName = input.readUTF();
                        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                        Object[] arguments = (Object[]) input.readObject();
                        Method method = service.getMethod(methodName, parameterTypes);
                        Object result = method.invoke(service.newInstance(), arguments);
                        output = new ObjectOutputStream(client.getOutputStream());
                        output.writeObject(result);
                        client.close();
                    } catch (Exception e) {
                        e.getStackTrace();
                    } finally {
                        try {
                            if (output != null) {
                                output.close();
                            }
                            if (input != null) {
                                input.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } finally {
            server.close();
        }
    }

}
