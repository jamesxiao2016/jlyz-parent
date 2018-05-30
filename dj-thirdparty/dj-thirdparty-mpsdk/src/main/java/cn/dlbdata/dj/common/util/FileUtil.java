package cn.dlbdata.dj.common.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    /**
     * 上传文件
     * @param file 文件内容
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
