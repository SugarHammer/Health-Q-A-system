package com.example.mywenda.myAsync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mywenda.util.JedisAdapter;
import com.example.mywenda.util.RedisKeyUtil;
import com.example.mywenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyEventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(MyEventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
