package com.how2java.tmall.util;

import org.springframework.web.multipart.MultipartFile;

public class UploadedImageFile {
    /**MultipartFile类型属性，用于接受上传文件的注入
     * 该属性名称image必须和页面中的增加分类部分中的type="file"的name值保持一致
     * **/
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image){
        this.image = image;
    }
}
