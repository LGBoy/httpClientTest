package com.distance.common.listener;

import java.io.File;
import java.io.FileFilter;

/**
 * @author 87796
 */
public class FileFilterImpl implements FileFilter {
    private String acceptName;
    /** 
     * return true:返回所有目录下所有文件详细(包含所有子目录);
     * return false:返回主目录下所有文件详细(不包含所有子目录) 
  */
    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return true;
        }
        return acceptName(pathname.getName());
    }

    private boolean acceptName(String fileName){
        String[] names=acceptName.split(",");
        boolean isAccept=false;
        for (String endName:names ) {
            if (fileName.endsWith(endName)){
                isAccept=true;
            }
        }
        return isAccept;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

}
