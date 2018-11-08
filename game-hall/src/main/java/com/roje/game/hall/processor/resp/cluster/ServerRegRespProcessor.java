package com.roje.game.hall.processor.resp.cluster;

import com.roje.game.core.processor.AbsMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServRegResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.ServRegRes,thread = ThreadType.def)
public class ServerRegRespProcessor extends AbsMessageProcessor {

    public ServerRegRespProcessor(MessageDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServRegResponse response = frame.getData().unpack(ServRegResponse.class);
        if (response.getSuccess()){
            log.info("注册成功,id:{}",response.getId());
        }else
            log.info("注册失败:{}",response.getMsg());
    }
}
