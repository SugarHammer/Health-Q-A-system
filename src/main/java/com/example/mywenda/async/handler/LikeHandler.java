package com.example.mywenda.async.handler;

import com.example.mywenda.async.EventHandler;
import com.example.mywenda.async.EventModel;
import com.example.mywenda.async.EventType;
import com.example.mywenda.model.Message;
import com.example.mywenda.model.User;
import com.example.mywenda.service.MessageService;
import com.example.mywenda.service.UserService;
import com.example.mywenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(Integer.parseInt(model.getActorId()));
//        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
