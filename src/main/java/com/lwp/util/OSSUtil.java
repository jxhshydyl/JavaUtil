package com.lwp.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class OSSUtil {

    private static String endpoint = "oss-cn-hongkong.aliyuncs.com";

    private static String accessKeyId = "LTAIh8LS6nZC2zKH";

    private static String accessKeySecret = "lrvln1FqpTAJwNQW5vNT8MbkhIsPs8";

    private static String bucketName = "xianshangyunshang";

    private OSSClient ossClientStatic;
    private ClientConfiguration conf;
    public static Logger logger = LoggerFactory.getLogger(OSSUtil.class);
    {
        conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000);// socket超时，默认15秒
        conf.setMaxErrorRetry(2);// 失败后最大重试次数
        ossClientStatic = new OSSClient(endpoint, accessKeyId, accessKeySecret,conf);
    }

    /**
     * 创建存储空间
     * @param bucketName 存储空间
     * @return
     */
    public  String createBucketName(String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClientStatic.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClientStatic.createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param bucketName  存储空间
     */
    public  void deleteBucket(String bucketName){
        ossClientStatic.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"folder/"
     * @return  文件夹名
     */
    public  String createFolder(String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClientStatic.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClientStatic.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = ossClientStatic.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param folder  模拟文件夹名 如"folder/"
     * @param key Bucket下的文件的路径名+文件名 如："folder/1.jpg"
     */
    public void deleteFile(String folder, String key){
        ossClientStatic.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 上传图片至OSS
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"test-kaka/headImage//"
     * @param afterFileName  上传后的文件名
     * @return String 返回的唯一MD5数字签名（在获取图片链接时会追加在链接后面）
     * */
    public  String uploadObject2OSS(BufferedImage bufImg, String bucketName, String folder, String beforeFileName, String afterFileName) {
        String resultStr = null;
        try {
            // 压缩代码
            bufImg = Thumbnails.of(bufImg).width(1024).keepAspectRatio(true).outputQuality(0.9).asBufferedImage();
            // 存储图片文件byte数组
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 图片写入到 ImageOutputStream
            ImageIO.write(bufImg, "jpg", bos);
            //以输入流的形式上传文件
            InputStream is = new ByteArrayInputStream(bos.toByteArray());
            //文件名
            String fileName = beforeFileName;
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
//            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClientStatic.putObject(bucketName, folder + afterFileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
            logger.error("上传阿里云OSS成功 {}", resultStr);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        //后缀名转小写
        String fileType = fileExtension.toLowerCase();
        //返回类型
        String contentType = "";
        switch (fileType) {
            case ".bmp":
                contentType = "image/bmp";
                break;
            case ".gif":
                contentType = "image/gif";
                break;
            case ".png":
            case ".jpeg":
            case ".jpg":
                contentType = "image/jpeg";
                break;
            case ".html":
                contentType = "text/html";
                break;
            case ".txt":
                contentType = "text/plain";
                break;
            case ".vsd":
                contentType = "application/vnd.visio";
                break;
            case ".ppt":
            case ".pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case ".doc":
            case ".docx":
                contentType = "application/msword";
                break;
            case ".xml":
                contentType = "text/xml";
                break;
            case ".mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }



    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     * 
     * @param fileName 文件名称 包括后缀名
     * @param instream 文件流
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String upload(String fileName, InputStream instream) {
        String resultStr = "";
        try {

            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(instream.available());
//            objectMetadata.setCacheControl("no-cache");
//            objectMetadata.setHeader("Pragma", "no-cache");
//            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
//            objectMetadata.setContentDisposition("inline;filename=" + fileName);

            // 上传文件 (上传文件流的形式)
            PutObjectResult putResult = ossClientStatic.putObject(bucketName, fileName, instream, objectMetadata);
            // 解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultStr;

    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadUrl(String fileName, String url) {

        try {
            InputStream instream = new URL(url).openStream();
            upload(fileName, instream);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key, String option) {
        if (StringUtils.isBlank(key))
            return "";
        return "http://" + bucketName + "." + endpoint + "/" + key + option;
    }

    /**
     * 获得url链接
     * @param key 上传图片的路径+名称（如：test-kaka/headImage/1546404670068899.jpg）
     * @return 图片链接：http://xxxxx.oss-cn-beijing.aliyuncs.com/test/headImage/1546404670068899.jpg?Expires=1861774699&OSSAccessKeyId=****=p%2BuzEEp%2F3JzcHzm%2FtAYA9U5JM4I%3D
     * （Expires=1861774699&OSSAccessKeyId=LTAISWCu15mkrjRw&Signature=p%2BuzEEp%2F3JzcHzm%2FtAYA9U5JM4I%3D 分别为：有前期、keyID、签名）
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        OSSClient ossClient = ossClientStatic;
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);

        return url.toString().substring(0, url.toString().lastIndexOf("?"));
    }

    /**
     * 获得指定文件的byte数组
     * @param filePath 文件绝对路径
     * @return
     */
    public byte[] imageToByteArray(String filePath){
        byte imgs[] = null;
        try {
            OSSClient ossClient = ossClientStatic;
            OSSObject in = ossClient.getObject(bucketName, filePath.replace("", ""));
            InputStream input = in.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte tmp [] = new byte[1024];
            int i ;
            while((i=input.read(tmp, 0, 1024))>0){
                baos.write(tmp, 0, i);
            }
            imgs = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgs;
    }

    public String getBaseUrl() {
        return "http://" + bucketName + "." + endpoint + "/";
    }

    public void shutdown() {
        ossClientStatic.shutdown();
    }


    public static void main(String[] args){
        File file = new File("");
        try {
            //压缩图片
            InputStream inputStream = ImageCompressUtil.compressPhoto(new FileInputStream(file), 700, 0.9d);
            OSSUtil ossUtil=new OSSUtil();
            ossUtil.upload("文件位置",inputStream);
            String path = ossUtil.getUrl("文件位置", "");
            ossUtil.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}