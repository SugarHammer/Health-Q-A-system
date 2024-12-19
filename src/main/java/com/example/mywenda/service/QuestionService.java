package com.example.mywenda.service;

import com.example.mywenda.dao.QuestionDAO;
import com.example.mywenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveService sensitiveService;


    public int addQuestion(Question question) {
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId(): 0;
    }


    public List<Question> getQuestion(int userId, int offset, int limit) {
        if (userId != 0){
            return questionDAO.selectLatestQuestion(userId, offset, limit);
        } else {
            return questionDAO.selectLatestQuestion2(userId, offset, limit);
        }
    }

    public Question selectQuestion(int qid) {
        return questionDAO.selectById(qid);
    }


    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }
}
