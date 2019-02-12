package com.zcx.redsoft.socketserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.net.Socket;

@SpringBootApplication
public class SocketServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SocketServerApplication.class, args);
//        SocketServer socketServer=new SocketServer();
//        socketServer.create();
//        for (int i=0;i<1000;i++){
//            socketServer.send(String.valueOf(i));
//            Thread.sleep(1000);
//        }


        MQSender sender=new MQSender();
        sender.sendByStep("词",1500000,700);
        sender.sendByStep("word",700000,1500);

    }
}
