package com.distance.common.service;

import com.distance.common.utils.RequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
/**
 * @author 87796
 */
@Slf4j
public class SendPost implements Runnable{
    private File requestFile;
    public SendPost (File requestFile){
        this.requestFile=requestFile;
    }
    @Override
    public void run() {
        try {
            RequestHelper.sendHttpAndSaveFile(requestFile);
        } catch (Exception e) {
            log.error("执行失败",e);
            RequestHelper.responseExceptionSaveAsFile(requestFile);
        }
    }

}
