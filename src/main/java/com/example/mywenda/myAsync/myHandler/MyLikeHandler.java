package com.example.mywenda.myAsync.myHandler;

import com.example.mywenda.model.Message;
import com.example.mywenda.model.User;
import com.example.mywenda.myAsync.MyEventHandler;
import com.example.mywenda.myAsync.MyEventModel;
import com.example.mywenda.myAsync.MyEventTye;
import com.example.mywenda.service.MessageService;
import com.example.mywenda.service.UserService;
import com.example.mywenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class MyLikeHandler implements MyEventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void doHandler(MyEventModel model) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName() + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        messageService.addMessage(message);


    }

    @Override
    public List<MyEventTye> getSupportEventTypes() {
        return Arrays.asList(MyEventTye.LIKE);
    }
}
