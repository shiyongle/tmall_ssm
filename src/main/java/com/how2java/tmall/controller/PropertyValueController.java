package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 不提供删除和增加功能
 * 仅对属性进行维护
 */
@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductService productService;

    /**
     * 1、根据pid获取product对象，因为面包屑导航里需要显示产品的名称和分类的链接
     * 2、初始化属性值：propertyValueService.init(p);因为在第一次访问的时候，这些属性值是不存在的，需要进行初始化
     * 3、根据产品获取其对应的属性值集合
     *  4 . 服务端跳转到editPropertyValue.jsp 上
     *  5. 在editPropertyValue.jsp上，用c:forEach遍历出这些属性值
     * @param model
     * @param pid
     * @return
     */
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model,int pid){
        Product p = productService.get(pid);
        propertyValueService.init(p);
        List<PropertyValue> pvs = propertyValueService.list(p.getId());

        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "admin/editPropertyValue";
    }

    /**
     *
     * @param pv
     * @return
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv){
        propertyValueService.update(pv);
        return "success";
    }
}
