package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.EntryRoomRequest;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.EntryRoomReq)
public class EnterRoomReqProcessor extends MessageProcessor {

    private final

    public EnterRoomReqProcessor(RoomManager<? extends Role, ? extends Room> roomManager) {
        this.roomManager = roomManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        EntryRoomRequest request = frame.getData().unpack(EntryRoomRequest.class);
        long roomId = request.getRoomId();
        Room room = roomManager.getRoomById(roomId);


    }
}
