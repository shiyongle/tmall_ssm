package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    public void add(Order c){
        orderMapper.insert(c);
    }

    public void update(Order c){
        orderMapper.updateByPrimaryKeySelective(c);
    }

    public void delete(int id){
        orderMapper.deleteByPrimaryKey(id);
    }

    public Order get(int id){
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> reslut = orderMapper.selectByExample(example);
        setUser(reslut);
        return reslut;
    }

    public void setUser(List<Order> os){
        for (Order o:os){
            setUser(o);
        }
    }

    public void setUser(Order o){
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }
}
