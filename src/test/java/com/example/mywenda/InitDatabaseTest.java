package com.example.mywenda;


import com.example.mywenda.dao.QuestionDAO;
import com.example.mywenda.dao.UserDAO;
import com.example.mywenda.model.Question;
import com.example.mywenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyWendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDao;

    @Test
    public void initDatabase() {
        Random random = new Random();
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(100)));
            user.setName(String.format("User%d",i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            user.setPassword("yy");
            userDAO.updatePassWord(user);


            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i);
            question.setCreateDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("Title %d", i));
            question.setContent(String.format("bilibili  %d",i));

            questionDao.addQuestion(question);
        }
        userDAO.deleteById(1);
        userDAO.selectUserById(1);
        System.out.println(questionDao.selectLatestQuestion(0,0,10));



    }
}
