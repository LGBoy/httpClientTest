package com.distance.common.event;

import com.distance.common.listener.FileFilterImpl;
import com.distance.common.utils.FileMonitorUtil;
import com.mzlion.core.utils.PlaceholderPropertyResolver;
import com.mzlion.core.utils.PropertyResolver;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 87796
 */
public class DistanceEvent  {
    /**
     * 10seconds mo
     */
    private static final long INTERVAL = 10L;
    private static final String DEFAULT_FILEPATH = "D:\\request";

    private static final String DEFAULT_ACCEPT = ".json,.JSON";

    public static void fileListenerEvent(){
        //读取默认配置文件
        PropertyResolver propertyResolver = new PlaceholderPropertyResolver.Builder()
                .path("classpath:file-listener.properties").build();
        //文件监听间隔时间
        long interval = propertyResolver.getProperty("interval", long.class);
        if (interval <= 0) {
            interval = INTERVAL;
        }
        //设置监听文件路径
        String filePath=propertyResolver.getProperty("filePath", String.class);
        if (StringUtils.isBlank(filePath)) {
            filePath = DEFAULT_FILEPATH;
        }
        FileFilterImpl fileFilter=new FileFilterImpl();
        //设置默认过滤文件
        String accept=propertyResolver.getProperty("accept", String.class);
        if (StringUtils.isBlank(accept)) {
            accept = DEFAULT_ACCEPT;
        }
        fileFilter.setAcceptName(accept);
         FileMonitorUtil.addMonitor(filePath,interval,fileFilter);
    }
}
