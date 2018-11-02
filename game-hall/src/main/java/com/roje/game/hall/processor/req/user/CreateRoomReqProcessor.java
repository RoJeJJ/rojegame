package com.roje.game.hall.processor.req.user;

import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.CreateCardRoomReq)
public class CreateRoomReqProcessor extends MessageProcessor {
    private final ISessionManager sessionManager;

    public CreateRoomReqProcessor(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        CreateCardRoomRequest request = frame.getData().unpack(CreateCardRoomRequest.class);
        CreateCardRoomResponse.Builder builder = CreateCardRoomResponse.newBuilder();
        try {
            Role role = sessionManager.getRole(channel);
            if (role != null)
                sessionManager.createRoom(role.getAccount(),request.getGameId(),request);
        }catch (RJException e){
            builder.setCode(e.getErrorData().getCode());
            builder.setMsg(e.getErrorData().getMsg());
            MessageUtil.send(channel,Action.CreateCardRoomRes,builder.build());
        }
    }
}
