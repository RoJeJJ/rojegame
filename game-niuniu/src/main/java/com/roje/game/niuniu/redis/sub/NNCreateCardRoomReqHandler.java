package com.roje.game.niuniu.redis.sub;

import com.google.protobuf.Message;
import com.roje.game.core.entity.Role;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.message.RedisMsgProcessor;
import com.roje.game.core.redis.sub.CreateRoomReqHandler;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.niuniu.data.NNRoom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RedisMsgProcessor(channel = "create-room-req",thread = ThreadType.sync)
public class NNCreateCardRoomReqHandler extends CreateRoomReqHandler<NNRoom> {

    public NNCreateCardRoomReqHandler(RedisMessageDispatcher dispatcher,
                                      ISessionManager<? extends Role, NNRoom> sessionManager,
                                      RedisTemplate<Object, Object> redisTemplate,
                                      ServerInfo serverInfo) {
        super(dispatcher, sessionManager, redisTemplate, serverInfo);
    }

    @Override
    protected Message responseData(NNRoom room) {
        return null;
    }
}
