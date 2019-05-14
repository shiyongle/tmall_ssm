package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    /**单个图片**/
    String type_single = "type_single";

    /**详情图片**/
    String type_detail = "type_detail";

    void add(ProductImage pi);

    void  delete(int id);

    void update(ProductImage pi);

    ProductImage get(int id);

    /**根据产品id，产品类型进行查找的方法**/
    List list(int pid, String type);
}
