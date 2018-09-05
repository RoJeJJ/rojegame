package com.roje.game.cluster;

import com.roje.game.cluster.server.ClusterHttpTcpServer;
import com.roje.game.cluster.server.ClusterTcpTcpServer;
import com.roje.game.core.util.SpringUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppCluster {
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(new ClassPathXmlApplicationContext("classpath:cluster-context.xml"));
        ClusterTcpTcpServer clusterTcpServer = SpringUtil.getBean(ClusterTcpTcpServer.class);
        new Thread(clusterTcpServer).start();
        ClusterHttpTcpServer clusterHttpTcpServer = SpringUtil.getBean(ClusterHttpTcpServer.class);
        new Thread(clusterHttpTcpServer).start();
    }
}
