package com.example.mywenda.model;

import java.util.HashMap;

public class ViewObj {
    private HashMap<User,Question> objectHashMap;

    public void setViewObj(User user, Question question) {
        objectHashMap.put(user, question);
    }

    public Question getViewObj(User user) {
        return objectHashMap.get(user);
    }
}
