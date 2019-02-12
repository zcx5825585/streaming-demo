package com.zcx.redsoft.socketserver;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private ServerSocket serverSocket;
    private Socket socket;

    public void create() {
        try {
            serverSocket = new ServerSocket(9999);//1024-65535的某个端口
            System.out.println("start");
            //2、调用accept()方法开始监听，等待客户端的连接
            socket = serverSocket.accept();
            System.out.println("连接成功");
        } catch (Exception e) {

        }
    }

    public void send(String msg) {
        try {
            //4、获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(msg+"\n");
            pw.flush();
        } catch (Exception e) {
        }
    }
}
