package com.example.mywenda.controller;

import com.example.mywenda.model.User;
import com.example.mywenda.service.WendaService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

//@Controller
public class IndexController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session) {
        return "Hello World" + session.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{userID}/{GroupID}"})
    @ResponseBody
    public String profile(@PathVariable("userID") int userID,
                          @PathVariable("GroupID") int GroupID,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", defaultValue = "zz") String key) {
        return String.format("profile page of %d %d %d %s", userID, GroupID, type, key);
    }

    @RequestMapping(path = {"/vm"}, method = RequestMethod.GET)
    public String template(Model model) {
        model.addAttribute("value1","111" );
        List<String> list = Arrays.asList("red","blue","yellow");
        model.addAttribute("value2", list);
        User user = new User();
        user.setName("li");
        model.addAttribute("value3",user);
        return "home";
    }

    @RequestMapping(path = {"/request"})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                            HttpSession session) {
        return "Hello World";
    }

    @RequestMapping(path = {"/redirect/{code}"})
    public String redirect(@PathVariable("code") int i,
                           HttpSession session) {
        session.setAttribute("msg","jump from redirect");
        return "redirect:/";
    }

    @RequestMapping(path = {"/admin"},method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if (key.equals("admin")) {
            return "Hello admin";
        }else {
            throw new IllegalArgumentException("参数不对");
        }

    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "error" + e.getMessage();
    }
}
