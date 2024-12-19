package com.example.mywenda.service;

import com.example.mywenda.dao.LoginTicketDAO;
import com.example.mywenda.dao.UserDAO;
import com.example.mywenda.model.LoginTicket;
import com.example.mywenda.model.User;
import com.example.mywenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User selectByName(String name) {
        return userDAO.selectUserByName(name);
    }

    public Map<String,String> register(String name, String password) {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByName(name);
        if (user != null){
            map.put("msg", "用户名已存在");
            return map;
        }
//        都成功，就注册
        user = new User();
        user.setName(name);
//        随机生成salt
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
//        随机生成头像
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(100)));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }


    public Map<String, String> login(String username, String password) {
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByName(username);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }

        if (!Objects.equals(WendaUtil.MD5(password + user.getSalt()), user.getPassword())) {
            map.put("msg", "密码不正确");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600 * 24 * 1000 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }


    public User getUser(int id) {
        return userDAO.selectUserById(id);
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket,1);
    }
}
