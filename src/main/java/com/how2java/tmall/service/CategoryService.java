package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    /**查询**/
    //List<Category> list();

    /**提供一个支持分页的查询方法和获取总数的方法total**/
//    int total();
//
//    List<Category> list(Page page);

    List<Category> list();

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}
