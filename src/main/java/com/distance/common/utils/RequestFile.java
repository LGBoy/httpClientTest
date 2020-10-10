package com.distance.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.distance.common.exception.RRException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 87796
 */
public class RequestFile {
    public static void saveRequestFile(String uuid,Object params,String url) {
        String filePath="D:\\request";
        //获取request
        Map<String,Object> requestMap=new HashMap<>();
        if(StringUtils.isBlank(url)){
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            url=request.getRequestURI();
            if(url.contains("/netExam")){
                url =  url.replace("/netExam","");
            }
            url="http://10.61.132.62:8699/examineefacecomparison"+url;
        }
        File fileDir = new File(filePath);
        try {
            requestMap.put("url",url);
            requestMap.put("params",params);
            String requestString= JSONArray.toJSONString(requestMap);
            FileUtils.forceMkdir(fileDir);
            FileUtils.write(new File(filePath+File.separator+uuid+".json"),requestString,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RRException(e.getMessage());
        }
    }
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    /**
     *  将请求文件解析为JSON
     */
    public static R responseReader(String uuid){
        String filePath="D:\\response\\"+uuid+".json";
        boolean exit=false;
        long start=System.currentTimeMillis();
        while (!exit){
            if (System.currentTimeMillis()-start< 1000*60){
                exit=isFileExists(filePath);
            }else {
                break;
            }
        }
        if (!exit){
            return  R.error("网络异常，请稍后重试");
        }
        String responseStr="";
        try {
            long startRes = System.currentTimeMillis();
            while (StringUtils.isBlank(responseStr)){
                if (System.currentTimeMillis()- startRes < 1000*10){
                    responseStr= FileUtils.readFileToString(new File(filePath),"UTF-8");
                }else {
                    break;
                }
            }
            return JSON.parseObject(responseStr,R.class);
        } catch (Exception e) {
            return R.ok(responseStr);
        }
    }
    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    private static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }
    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    private static File getFileByPath(String filePath) {
        return StringUtils.isWhitespace(filePath) ? null : new File(filePath);
    }
    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    private static boolean isFileExists(File file) {
        return file != null && file.exists();
    }
}
