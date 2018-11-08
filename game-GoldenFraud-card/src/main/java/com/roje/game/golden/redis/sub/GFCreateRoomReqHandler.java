package com.roje.game.golden.redis.sub;

import com.google.protobuf.Message;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.message.RedisMsgProcessor;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.redis.sub.CreateRoomReqHandler;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.golden.data.GFCardRoom;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RedisMsgProcessor(channel = "create-room-req",thread = ThreadType.sync)
public class GFCreateRoomReqHandler extends CreateRoomReqHandler<GFCardRole, GFCardRoom> {
    public GFCreateRoomReqHandler(RedisMessageDispatcher dispatcher,
                                  RedisTemplate<Object, Object> redisTemplate,
                                  UserRedisService userRedisService,
                                  ServerInfo serverInfo,
                                  RoomHelper<GFCardRoom> roomHelper) {
        super(dispatcher, redisTemplate, userRedisService, serverInfo, roomHelper);
    }

    @Override
    protected Message responseData(GFCardRoom room) {
        return null;
    }
}
