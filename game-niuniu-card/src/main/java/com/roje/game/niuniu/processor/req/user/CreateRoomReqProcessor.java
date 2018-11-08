package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultCreateCardRoomReqProcessor;
import com.roje.game.message.action.Action;
import com.roje.game.niuniu.data.NNCardRoom;
import com.roje.game.niuniu.data.NNRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.CreateCardRoomReq)
public class CreateRoomReqProcessor extends DefaultCreateCardRoomReqProcessor<NNRole, NNCardRoom> {

    protected CreateRoomReqProcessor(MessageDispatcher dispatcher,
                                     ISessionManager<NNRole> sessionManager,
                                     CardRoomManager<NNRole, NNCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
