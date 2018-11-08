package com.roje.game.hall.processor.req.user;

import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.hall.entity.HRole;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.CreateCardRoomReq)
public class CreateRoomReqProcessor extends RoleMessageProcessor<HRole> {

    private final RoomHelper<Void> roomHelper;

    public CreateRoomReqProcessor(MessageDispatcher dispatcher,
                                  ISessionManager<HRole> sessionManager,
                                  RoomHelper<Void> roomHelper) {
        super(dispatcher, sessionManager);
        this.roomHelper = roomHelper;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        CreateCardRoomRequest request = frame.getData().unpack(CreateCardRoomRequest.class);
        try {
            Role role = sessionManager.getRole(channel);
            if (role != null)
                roomHelper.createRoom(request);
        }catch (RJException e){
            MessageUtil.sendErrorData(channel, e.getErrorData());
        }
    }
}
