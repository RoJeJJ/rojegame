package com.roje.game.hall.redis.sub;

import com.google.protobuf.InvalidProtocolBufferException;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.message.RedisMessageHandler;
import com.roje.game.core.redis.message.RedisMsgProcessor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RedisMsgProcessor(channel = "create-room-res")
public class createRoomResHandler extends RedisMessageHandler {
    private final ISessionManager sessionManager;
    public createRoomResHandler(RedisMessageDispatcher dispatcher,
                                ISessionManager sessionManager) {
        super(dispatcher);
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(byte[] data) {
        try {
            CreateCardRoomResponse response = CreateCardRoomResponse.parseFrom(data);
            Role role = sessionManager.getRole(response.getAccount());
            if (role != null)
                MessageUtil.send(role.getChannel(), Action.CreateCardRoomRes,response);
        } catch (InvalidProtocolBufferException e) {
            log.warn("解析redis消息异常",e);
        }
    }
}
