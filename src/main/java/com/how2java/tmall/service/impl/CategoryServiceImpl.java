package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    /**自动装配Autowired引入CategoryMapper**/
    @Autowired
    CategoryMapper categoryMapper;

    /**(第一节)在list方法中调用categoryMapper的list方法**/
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    /**第二节：提供一个分页查询和查询总数的方法**/
//    @Override
//    public List<Category> list(Page page) {
//        return categoryMapper.list(page);
//    }

//    @Override
//    public int total() {
//        return categoryMapper.total();
//    }

    /**(第三节)新增add方法**/
    @Override
    public void add(Category category){
        categoryMapper.insert(category);
    }

    /**(第四节)增加delete方法**/
    @Override
    public void delete(int id){
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**(第五节)增加get方法**/
    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    /**
     *（第六节）增加修改方法
     */
    @Override
    public void update(Category category){
        categoryMapper.updateByPrimaryKeySelective(category);
    }


}
