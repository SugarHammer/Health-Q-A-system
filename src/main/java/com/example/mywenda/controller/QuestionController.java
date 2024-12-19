package com.example.mywenda.controller;

import com.example.mywenda.dao.QuestionDAO;
import com.example.mywenda.model.*;
import com.example.mywenda.service.CommentService;
import com.example.mywenda.service.LikeService;
import com.example.mywenda.service.QuestionService;
import com.example.mywenda.service.UserService;
import com.example.mywenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @ResponseBody
    @RequestMapping(path = {"/question/add"},method = {RequestMethod.POST})
    public String addQuestion(@RequestParam("title") String title,
                            @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreateDate(new Date());
            question.setCommentCount(0);
            if (hostHolder.getUser() == null) {
//                question.setUserId(WendaUtil.ANONYMOUS_USERID);
                return WendaUtil.getJSONString(999);

            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJSONString(0);
            }


        }catch (Exception e) {
            logger.error("新增问题失败" + e.getMessage());
        }
        return WendaUtil.getJSONString(1, "失败");
    }


    @RequestMapping(value = "/question/{qid}")
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.selectQuestion(qid);
        model.addAttribute("question",question);
        model.addAttribute("user",userService.getUser(question.getUserId()));

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));

//            加入点赞数
            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            if (hostHolder.getUser() == null) {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,comment.getId()));
            }
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        return "detail";
    }
}
