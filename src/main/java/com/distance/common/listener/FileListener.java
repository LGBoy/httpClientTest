package com.distance.common.listener;

import com.distance.common.config.ExecutorConfig;
import com.distance.common.service.SendPost;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;

/**
 * @author 87796
 */
@Slf4j
public class FileListener extends FileAlterationListenerAdaptor {

    private static final ThreadPoolTaskExecutor TASK_EXECUTOR = ExecutorConfig.asyncServiceExecutor();
    @Override
    public void onDirectoryChange(File file) {
        log.info("onDirectoryChange");
    }

    @Override
    public void onDirectoryCreate(File file) {
        log.info("Directory create: " + file.getAbsolutePath()+"  "+file.getParent());
    }

    @Override
    public void onDirectoryDelete(File file) {
        log.info("Directory delete: " + file.getAbsolutePath()+"  "+file.getParent());
    }

    @Override
    public void onFileChange(File file) {
        log.info("File change: " + file.getAbsolutePath()+"  "+file.getParent());
    }

    @Override
    public void onFileCreate(File file) {
        SendPost sendPost=new SendPost(file);
        TASK_EXECUTOR.execute(sendPost);
        log.info("File created: " + file.getAbsolutePath()+"  "+file.getParent());
    }

    @Override
    public void onFileDelete(File file) {
        log.info("File deleted: " + file.getAbsolutePath()+"  "+file.getParent());
    }

    @Override
    public void onStart(FileAlterationObserver filealterationobserver) {
//        log.info("File onStart: " );
    }

    @Override
    public void onStop(FileAlterationObserver filealterationobserver) {
//        log.info("File onStop: " );
    }

}
