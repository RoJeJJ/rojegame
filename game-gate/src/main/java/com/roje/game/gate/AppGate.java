package com.roje.game.gate;


import com.roje.game.core.util.SpringUtil;
import com.roje.game.gate.client.GateClusterTcpClient;
import com.roje.game.gate.server.GateGameTcpServer;
import com.roje.game.gate.server.GateUserTcpTcpServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppGate {
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:gate-context.xml"));
        GateUserTcpTcpServer gateUserTcpServer = SpringUtil.getBean("gateUserTcpServer");
        new Thread(gateUserTcpServer).start();
        GateGameTcpServer gateGameTcpServer = SpringUtil.getBean("gateGameTcpServer");
        new Thread(gateGameTcpServer).start();
        GateClusterTcpClient gateClusterTcpClient = SpringUtil.getBean("gateClusterTcpClient");
        new Thread(gateClusterTcpClient).start();
    }
}
