package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * 访问http://127.0.0.1:8080/tmall_ssm/admin_order_list可以看到订单查询页面
     * admin_order_list导致OrderController.list()方法被调用
     * 1、获取分页对象
     * 2、查询订单集合
     * 3、获取订单总数并设置在分页对象上
     * 4、借助orderItemService.fill(os)方法为这些订单填充上orderItems信息
     * 5、把订单集合和分页对象设置在model上
     * 6、服务端跳转至admin/listOrder.jsp页面
     * 7、在listOrder.jsp借助c：forEach把订单集合遍历出来
     * 8、遍历订单的时候，再把当前订单的orderItem订单项集合遍历出来
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os = orderService.list();

        int total = (int) new PageInfo<>(os).getTotal();
        page.setTotal(total);

        orderItemService.fill(os);

        model.addAttribute("os", os);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }

    /**
     * 当发货订单状态是waitDelivery的时候，就会出现发货按钮
     * 1、发货按钮链接跳转到admin_order_delivery
     * 2、OrderController.delivery方法被调用
     *      注入订单对象
     *      修改发货时间
     *      更新到数据库
     *      客户端跳转到admin_order_list页面
     * @param o
     * @return
     * @throws IOException
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order o) throws IOException{
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}
