package com.distance.common.utils;

import com.distance.common.listener.FileListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.FileFilter;

/**
 * @author 87796
 * 监听器 监听事件
 */
@Slf4j
public class FileMonitorUtil  {
	/**
	 *  添加监听动作
	 * @param path  监听路径
	 * @param millsTime 监听间隔
	 * @param filter 文件过滤器
	 * @return
	 */
	public static FileAlterationMonitor addMonitor(String path, long millsTime, FileFilter filter){
		//监控器
		FileAlterationMonitor monitor = new FileAlterationMonitor(millsTime);
		//对某个路径的观察者
        FileAlterationObserver observer = new FileAlterationObserver(path,filter);
		//添加监听事件响应
        observer.addListener(new FileListener());
		//将观察者添加到监控器
        monitor.addObserver(observer);
        try {
			//启动
			monitor.start();
		} catch (Exception e) {
			log.error("监听器启动失败",e);
		}
		//便于停止
        return monitor;
	}
}
