package com.example.mywenda.myAsync;

import java.util.List;

public interface MyEventHandler {
    public void doHandler(MyEventModel eventModel);

    public List<MyEventTye> getSupportEventTypes();
}
