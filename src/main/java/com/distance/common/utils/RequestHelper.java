package com.distance.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.mzlion.easyokhttp.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author 87796
 */
@Slf4j
public class RequestHelper {
    private static final String FILE_PATH="D:\\response";
    /**
     * 发送请求  并将请求结果保存在文件中
     * @param requestFile 请求文件
     */
    public static void  sendHttpAndSaveFile(File requestFile) throws Exception{
        JSONObject requestJson= RequestHelper.requestReader(requestFile);
        String url=String.valueOf(requestJson.get("url"))  ;
        String requestType=(String) requestJson.get("requestType");
        String paramsStr= String.valueOf(requestJson.get("params")) ;
        String resStr="";
        if (StringUtils.isBlank(url)){
            throw new RuntimeException("url不能为空");
        }
        //1.params为map
        if (StringUtils.isNotBlank(paramsStr)){
            if ("form".equals(requestType)||StringUtils.isBlank(requestType)){
                Map params=JSONObject.parseObject(paramsStr,Map.class);
                //将相应结果输出至文件中
                if (params!=null) {
                    if (params.get("entity") == null) {
                        resStr=  HttpClient.post(url).param(params).asString();
                    } else {
                        resStr = HttpClient.textBody(url).json(String.valueOf(params.get("entity"))).asString();
                    }
                }else{
                    resStr =  HttpClient.post(url).asString();
                }
            }else if ("json".equals(requestType)){
                //2.params为json字符串
                resStr = HttpClient.textBody(url).json(paramsStr).asString();
            }else if ("get".equals(requestType)){
                resStr = HttpClient.get(url).asString();
            }
        }else {
            resStr = HttpClient.get(url).asString();
        }
        FileUtils.writeStringToFile(new File(FILE_PATH,requestFile.getName()),resStr,"UTF-8");
    }
    /**
     *  将请求文件解析为JSON
     */
    private static JSONObject requestReader(File file){
        String requestStr="";
        try {
            long start = System.currentTimeMillis();
            while (StringUtils.isBlank(requestStr)) {
                if (System.currentTimeMillis()-start<1000*10){
                    requestStr = FileUtils.readFileToString(file, "UTF-8");
                }else {
                    break;
                }
            }
            if (StringUtils.isBlank(requestStr)){
                throw new RuntimeException("文件解析出错");
            }
            return JSONObject.parseObject(requestStr);
        } catch (IOException e) {
            log.error("文件解析出错",e);
            throw new RuntimeException("文件解析出错");
        }

    }

    /**
     * 线程错误则 保存错误信息
     * 解析异常，将异常信息保存至响应文件
     * @param requestFile
     * @throws IOException
     */
    public static void responseExceptionSaveAsFile(File requestFile){
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("code",500);
            jsonObject.put("msg","服务连接异常");
            FileUtils.writeStringToFile(new File(FILE_PATH,requestFile.getName()),jsonObject.toJSONString(),"UTF-8");
        } catch (IOException e) {
            log.error("失败",e);
        }
    }

}
