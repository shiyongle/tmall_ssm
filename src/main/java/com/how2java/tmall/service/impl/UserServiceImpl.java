package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    public void add(User u){
        userMapper.insert(u);
    }

    public void delete(int id){
        userMapper.deleteByPrimaryKey(id);
    }

    public void update(User u){
        userMapper.updateByPrimaryKeySelective(u);
    }

    public User get(int id){
        return  userMapper.selectByPrimaryKey(id);
    }

    public List<User> list(){
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }
}
