package com.tao.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断上传文件是普通的表单还是带文件的表单
        if (!ServletFileUpload.isMultipartContent(req)) {
            return ;//终止方法运行，说明这是一个普通表单
        }
        //创建上传文件保存路径，建议在WEB-INF路径下，安全，用户无法直接访问上传的文件
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");//文件在tomcat目录下
        File uploadFile = new File(uploadPath);
        if(!uploadFile.exists()) {
            uploadFile.mkdir();//创建这个目录
        }
        //缓存，存放临时文件
        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File file = new File(tmpPath);
        if (!file.exists()) {
            file.mkdir();//创建临时目录
        }

        //处理上传文件一般通过流来处理，可以使用request.getInputStream()，原生态的文件上传流获取，比较麻烦
        //建议使用apache的文件上传组件来实现，common-fileupload，它需要依赖common-io组件

        //1.创建DiskFileItemFactory对象，用来处理文件上传路径和大小限制
        DiskFileItemFactory factory = getDiskFileItemFactory(file);//参数临时文件夹的File对象
        //2.获取ServletFileUpload
        ServletFileUpload upload = getServletFileUpload(factory);
        //3.处理上传的文件
        String msg = uploadParseRequest(upload, req, uploadPath);
        //servlet请求转发
        req.setAttribute("msg",msg);
        req.getRequestDispatcher("info.jsp").forward(req,resp);
        

    }
    public DiskFileItemFactory getDiskFileItemFactory(File file){
        //1.创建DiskFileItemFactory对象，用来处理文件上传路径和大小限制
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过工厂设置缓冲区，当上传的文件大于缓冲区的时候，将他放到临时文件中
        factory.setSizeThreshold(1024);//1k//使用指定目录进行缓存，最终还是存到指定的存储位置
        factory.setRepository(file);//大文件缓存目录
        return factory;
    }

    public ServletFileUpload getServletFileUpload(DiskFileItemFactory factory){
        //2.获取ServletFileUpload
        //ServletFileUpload负责处理上传的文件数据，并将表单中的每个输入项封装成一个FileItem对象
        //在使用ServletFileUpload对象解析请求时需要DiskFileItemFactory对象

        ServletFileUpload upload = new ServletFileUpload(factory);

        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("总大小"+pContentLength+"已上传"+pBytesRead);
            }
        });
        //处理乱码的问题
        upload.setHeaderEncoding("utf-8");
        //设置单个文件的最大值
        upload.setFileSizeMax(1024*1024*10);//10M
        //设置总共能上传文件的大小
        upload.setSizeMax(1024*1024*10);//10M

        return upload;
    }

    public String uploadParseRequest(ServletFileUpload upload,HttpServletRequest req,String uploadPath){
        //3.处理上传的文件
        //把前端请求解析，封装成一个FileItem对象，需要从ServletFileUpload对象中获取
        String msg="";//返回的信息
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            //fileItem是每个表单对象
            for (FileItem fileItem : fileItems) {//普通表单
                //判断上传文件是普通的表单还是带文件的表单
                if(fileItem.isFormField()){
                    //getFieldName指的是前端表单控件的name
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    System.out.println(name+":"+value);
                }else{//文件表单
                    //==============================处理文件=====================================
                    String uploadFileName = fileItem.getName();
                    //可能存在文件名不合法的情况
                    if(uploadFileName==null||uploadFileName.trim().equals("")){
                        msg ="文件名不合法";
                        return msg;

                    }
                    //获得文件上传名
                    String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/")+1);
                    //获得文件的后缀名
                    String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
                    /*
                     * 如果后缀名fileExtName不是我们所需要的
                     * 直接return，不处理，告诉用户文件类型不对
                     * */
                    //可以使用UUID(Universally Unique Identifier)通用唯一识别码，保证文件名唯一，太长了，重复概率看成0
                    //UUID.randomUUID()随机生成一个通用唯一识别码

                    //==============================存放地址========================================
                    //存放到upload下的生成的文件夹下，一个文件一个文件夹，文件夹名字用uuid随机生成
                    String uuidPath = UUID.randomUUID().toString();
                    String realPath = uploadPath+"/"+uuidPath;
                    File realPathFile = new File(realPath);
                    if(!realPathFile.exists()){
                        realPathFile.mkdir();
                    }
                    //===============================文件传输=======================================
                    //获得文件上传的流
                    InputStream inputStream = fileItem.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream fos = new FileOutputStream(realPath+"/"+fileName);
                    //创建一个缓冲区
                    byte[] buffer = new byte[1024*1024];
                    //判断是否读取完毕
                    int len = 0;
                    //如果大于0说明还存在数据
                    while ((len = inputStream.read(buffer)) > 0) {
                        fos.write(buffer,0,len);
                    }
                    //关闭流
                    fos.close();
                    inputStream.close();

                    msg = "文件上传成功";
                    fileItem.delete();
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
            return msg;
    }

}
