package com.example.mywenda.service;


import org.springframework.stereotype.Service;

@Service
public class WendaService {
    public String getMessage(int user) {
        return  "Hello message" + String.valueOf(user);
    }
}
