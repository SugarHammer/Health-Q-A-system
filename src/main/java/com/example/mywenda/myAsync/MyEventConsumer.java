package com.example.mywenda.myAsync;

import com.alibaba.fastjson.JSON;
import com.example.mywenda.async.EventHandler;
import com.example.mywenda.async.EventModel;
import com.example.mywenda.service.LikeService;
import com.example.mywenda.util.JedisAdapter;
import com.example.mywenda.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyEventConsumer implements InitializingBean, ApplicationContextAware {

    private Map<MyEventTye, List<MyEventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;
    private final Logger logger = LoggerFactory.getLogger(MyEventConsumer.class);

    @Autowired
    JedisAdapter jedisAdapter;


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, MyEventHandler> beans = applicationContext.getBeansOfType(MyEventHandler.class);
        //   写一个新的handler时，自动注册，即对于某个类型，关注他的handler有哪些
        if (beans != null) {
            for (Map.Entry<String, MyEventHandler> entry : beans.entrySet()) {
                List<MyEventTye> eventTypes = entry.getValue().getSupportEventTypes();

                for (MyEventTye type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(entry.getValue());

                }
            }
        }


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> events = jedisAdapter.brpop(0, key);

                    for (String message : events) {
                        //                    过滤第一个key
                        if (message.equals(key)) {
                            continue;
                        }

                        MyEventModel eventModel = JSON.parseObject(message, MyEventModel.class);
                        if (!config.containsKey(eventModel.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }

//                        与该事件相关的每一个handler都要处理该事件
                        for (MyEventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandler(eventModel);
                        }

                    }
                }
            }
        });
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
