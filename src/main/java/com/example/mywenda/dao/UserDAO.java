package com.example.mywenda.dao;

import com.example.mywenda.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDAO {
    final String TABLE_NAME = "user";
    final String INSERT_FIELD = "name, password, salt, headUrl";
    final String SELECT_FIELD = "id," + INSERT_FIELD;

    @Insert({"insert into",TABLE_NAME ,"(",INSERT_FIELD,
            ") values(#{name},#{password},#{salt},#{headUrl})"})
    public int addUser(User user);

    @Select({"select ", SELECT_FIELD, "from", TABLE_NAME, "where id = #{id}"})
    public User selectUserById(int id);

    @Select({"select ", SELECT_FIELD, "from", TABLE_NAME, "where name = #{name}"})
    public User selectUserByName(String name);

    @Insert({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    public int updatePassWord(User user);

    @Delete({"delete from",TABLE_NAME,"where id = #{id}"})
    public int deleteById(int id);
}
