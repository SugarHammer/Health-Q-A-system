package com.example.mywenda.myAsync;

import java.util.HashMap;
import java.util.Map;

public class MyEventModel {
    private MyEventTye type;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;

    private Map<String, String> ext = new HashMap<>();

    public MyEventModel() {
    }

    public MyEventTye getType() {
        return type;
    }

    public  MyEventModel setType(MyEventTye type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public  MyEventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public  MyEventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public  MyEventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public  MyEventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public String getExt(String key) {
        return ext.get(key);
    }

    public MyEventModel setExt(String key, String value) {
        ext.put(key, value);
        return this;
    }
}
