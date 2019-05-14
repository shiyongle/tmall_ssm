package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
/**表示访问的时候无须额外的地址**/
@RequestMapping("")
public class CategoryController {
    /**把CategoryServiceImpl自动装配进了CategoryService 接口**/
    @Autowired
    CategoryService categoryService;

    /**（第一节）
     * 映射admin_category_list路径的访问
     *在list方法中，通过categoryService.list()获取所有的Category对象，然后放在"cs"中，并服务端跳转到 “admin/listCategory” 视图。
     *“admin/listCategory” 会根据后续的springMVC.xml 配置文件，跳转到 WEB-INF/jsp/admin/listCategory.jsp 文件
     * **/

    /**（第二节）
     * 为list方法增加了Page参数用于获取浏览器传递过来的分页信息
     * categoryService.list(page); 获取当前页的分类集合
     * 通过categoryService.total(); 获取分类总数
     * 通过page.setTotal(total); 为分页对象设置总数
     * 把分类集合放在"cs"中
     * 把分页对象放在 "page“ 中
     * 跳转到listCategory.jsp页面
     * **/
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
//        int total = categoryService.total();
//        page.setTotal(total);
//        model.addAttribute("cs", cs);
//        model.addAttribute("page",page);
        List<Category> cs = categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    /**(第三节)增加add方法
     *  add方法映射amdin_category_add的访问
     *      Category c接受页面提交的分类名称
     *      session用于后续获取当前应用的路径
     *      uploadedImageFile用于接收上传的图片
     * 通过categoryService保存c对象
     * 通过session获取controllerContext，再通过getRealPath定位存放分类图片的路径
     * 根据分类id创建文件名
     * 如果img/category目录不存在，则创建该目录，否则后续保存浏览器传过来图片，会提示无法保存
     * 通过uploadedImageFile把浏览器传递过来的图片保存在上述指定位置
     * 通过ImageUtil.change2jpg(file)确保图片格式一定是jpg，而不仅仅只有后缀名是jpg
     * 客户端跳转至admin_category_list
     */
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId()+".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg", file);
        return "redirect:/admin_category_list";
    }

    /**(第四节)
     *  映射路径admin_category_delete
     *  提供参数接受id注入
     *  提供session参数，用于后续定位文件位置
     *  通过categoryService删除数据
     *  通过session获取ControllerContext获取分类图片位置，接着删除图片
     *  客户端转到admin_category_list
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException{
        categoryService.delete(id);
        File imgeFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imgeFolder, id + ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    /**(第五节)
     * 映射admin_category_edit的访问
     * 参数id用来接受注入
     * 通过categoryService.get(id)获取category对象
     * 把对象放在c上
     * 返回editCategory.jsp
     */
    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model) throws IOException {
        Category c= categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    /**
     * 映射路径为admin_category_update的访问
     * @param c ：接受页面提交的分类名称
     * @param session ：用于在后续获取当前应用的路径
     * @param uploadedImageFile ： 用于接收上传的图片
     * 通过catrgoryService更新c对象
     * 首先判断是否有上传图片，如果有上传，那么通过session获取ControllerContext，再通过getRealPath定位存放分类图片的路径
     * 根据分类id创建文件名
     * 通过uploadedImageFile把浏览器传递过来的图片保存在上述指定位置
     * 通过ImageUtil.change2jpg(file)，确保图片格式一定是jpg，而不仅仅后缀名是.jpg
     * 客户端跳转到admin_category_list
     */
    @RequestMapping("admin_category_update")
    public String update(Category c,HttpSession session, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.update(c);
        MultipartFile image = uploadedImageFile.getImage();
        if (null != image && !image.isEmpty()){
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }
}
