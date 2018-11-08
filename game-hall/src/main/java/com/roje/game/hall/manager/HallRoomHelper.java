package com.roje.game.hall.manager;

import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.redis.message.RedisMessage;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class HallRoomHelper implements RoomHelper<Void> {

    private final UserRedisService userRedisService;

    private final ServerInfo serverInfo;

    private final RedisTemplate<Object,Object> redisTemplate;

    public HallRoomHelper(UserRedisService userRedisService,
                          ServerInfo serverInfo,
                          RedisTemplate<Object, Object> redisTemplate) {
        this.userRedisService = userRedisService;
        this.serverInfo = serverInfo;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Void createRoom(CreateCardRoomRequest request) throws RJException {
        ServerInfo serverInfo = userRedisService.getAllocateServer(request.getAccount(),request.getType());
        if (serverInfo != null) {
            RedisMessage redisMessage = new RedisMessage();
            redisMessage.setFromServerId(this.serverInfo.getId());
            redisMessage.setToServerId(serverInfo.getId());
            redisMessage.setData(request.toByteArray());

            redisTemplate.convertAndSend("create-room-req",redisMessage);
        }else
            throw new RJException(ErrorData.CREATE_ROOM_GAME_SERVER_NOT_FOUND);
        return null;
    }
}
