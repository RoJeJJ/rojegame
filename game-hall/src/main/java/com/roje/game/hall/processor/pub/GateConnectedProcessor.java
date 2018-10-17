package com.roje.game.hall.processor.pub;

import com.roje.game.core.manager.ConnGateTcpMultiManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.broadcast.GateConnected;
import com.roje.game.message.conn_info.ConnInfo;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.PubGateConnected)
public class GateConnectedProcessor extends MessageProcessor {

    private final ConnGateTcpMultiManager gateTcpMultiManager;

    @Autowired
    public GateConnectedProcessor(ConnGateTcpMultiManager gateTcpMultiManager) {
        this.gateTcpMultiManager = gateTcpMultiManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        GateConnected connected = frame.getData().unpack(GateConnected.class);
        ConnInfo connInfo = connected.getConnInfo();
        if (connInfo != null)
            gateTcpMultiManager.connect(connInfo);
    }
}