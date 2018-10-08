package com.roje.game.gate;


import com.roje.game.core.util.SpringUtil;
import com.roje.game.gate.client.GateToClusterTcpClient;
import com.roje.game.gate.server.GateToGameTcpServer;
import com.roje.game.gate.server.GateToUserTcpTcpServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppGate {
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:gate-context.xml"));
        GateToUserTcpTcpServer gateUserTcpServer = SpringUtil.getBean("gateUserTcpServer");
        new Thread(gateUserTcpServer).start();
        GateToGameTcpServer gateToGameTcpServer = SpringUtil.getBean("gateToGameTcpServer");
        new Thread(gateToGameTcpServer).start();
        GateToClusterTcpClient gateToClusterTcpClient = SpringUtil.getBean("gateToClusterTcpClient");
        new Thread(gateToClusterTcpClient).start();
    }
}
