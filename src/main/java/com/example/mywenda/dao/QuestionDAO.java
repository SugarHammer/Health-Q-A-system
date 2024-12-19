package com.example.mywenda.dao;

import com.example.mywenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDAO {
    final String TABLE_NAME = "question";
    final String INSERT_FIELD = "title, content, createDate, userId, commentCount";
    final String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into",TABLE_NAME ,"(",INSERT_FIELD,
            ") values(#{title},#{content},#{createDate},#{userId},#{commentCount})"})
    public int addQuestion(Question question);


    public List<Question> selectLatestQuestions(@Param("userId") int userId,
                                               @Param("offset") int offset,
                                               @Param("limit") int limit);

    @Select({"<script>" +
            "select * from", TABLE_NAME +
            "<if test = 'userId != 0'> "+
                "where userId = #{userId}" +
            "</if>" +
            "<if test = 'userId == 0'>" +
            "order by createDate ASC" +
            "limit #{offset},#{limit}" +
            "</if>" +
            "</script>"})
    public List<Question> selectLatestQuestion(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Select({"<script>" +
            "select * from", TABLE_NAME +
            "<if test = 'userId != 0'> "+
            "where userId = #{userId}" +
            "</if>" +
            "<if test = 'userId = 0'>" +
            "order by createDate ASC" +
            "limit #{offset},#{limit}" +
            "</if>" +
            "</script>"})
    public List<Question> selectLatestQuestion2(@Param("userId") int userId,
                                               @Param("offset") int offset,
                                               @Param("limit") int limit);

//    @Insert({"update",TABLE_NAME,"set commentCount = #{commentCount} where id = #{id}"})
//    public int updateCommentCount(Question question);

    @Delete({"delete from",TABLE_NAME,"where id = #{id}"})
    public int deleteById(int id);

    @Select({"select",SELECT_FIELD,"from",TABLE_NAME,"where id = #{qid}"})
    public Question selectById(int qid);

    @Update({"update", TABLE_NAME, "set commentCount = #{commentCount} where id=#{id}"})
    public int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
