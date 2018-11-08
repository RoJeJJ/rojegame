package com.roje.game.core.redis.sub;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.redis.message.RedisMessage;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.message.RedisMessageHandler;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public abstract class CreateRoomReqHandler<M extends CardRoom> extends RedisMessageHandler {

    protected final RedisTemplate<Object,Object> redisTemplate;

    protected final UserRedisService userRedisService;

    protected final ServerInfo serverInfo;

    private final RoomHelper<M> roomHelper;

    public CreateRoomReqHandler(RedisMessageDispatcher dispatcher,
                                RedisTemplate<Object, Object> redisTemplate,
                                UserRedisService userRedisService,
                                ServerInfo serverInfo,
                                RoomHelper<M> roomHelper) {
        super(dispatcher);
        this.redisTemplate = redisTemplate;
        this.userRedisService = userRedisService;
        this.serverInfo = serverInfo;
        this.roomHelper = roomHelper;
    }

    @Override
    public void handle(byte[] data) {
        CreateCardRoomRequest request = null;
        try {
            request = CreateCardRoomRequest.parseFrom(data);
            M room = roomHelper.createRoom(request);
            CreateCardRoomResponse.Builder builder = CreateCardRoomResponse.newBuilder();
            builder.setCode(0);
            builder.setType(request.getType());
            builder.setAccount(request.getAccount());
            Any any = Any.pack(responseData(room));
            builder.setResponseData(any);

            RedisMessage redisMessage = new RedisMessage();
            redisMessage.setFromServerId(serverInfo.getId());
            redisMessage.setToServerId(getSenderId());
            redisMessage.setData(builder.build().toByteArray());
            redisTemplate.convertAndSend("create-room-res",redisMessage);
        }catch (InvalidProtocolBufferException e) {
            log.warn("解析protobuf消息异常",e);
        } catch (RJException e) {
            catchRJException(e,request.getAccount());
        }
    }

    protected abstract Message responseData(M room);

    private void catchRJException(RJException e,String account){
        CreateCardRoomResponse.Builder builder = CreateCardRoomResponse.newBuilder();
        builder.setCode(e.getErrorData().getCode());
        builder.setMsg(e.getErrorData().getMsg());
        builder.setAccount(account);

        RedisMessage redisMessage = new RedisMessage();
        redisMessage.setFromServerId(serverInfo.getId());
        redisMessage.setToServerId(getSenderId());
        redisMessage.setData(builder.build().toByteArray());
        redisTemplate.convertAndSend("create-room-res",redisMessage);
    }
}
