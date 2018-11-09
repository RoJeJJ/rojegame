package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultEnterCardRoomReqProcessor;
import com.roje.game.message.action.Action;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNCardRoom;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.EntryRoomReq)
public class EnterGameRoomReqProcessor extends DefaultEnterCardRoomReqProcessor<NNRole, NNCardRoom> {


    protected EnterGameRoomReqProcessor(MessageDispatcher dispatcher,
                                        ISessionManager<NNRole> sessionManager,
                                        CardRoomManager<NNRole, NNCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
