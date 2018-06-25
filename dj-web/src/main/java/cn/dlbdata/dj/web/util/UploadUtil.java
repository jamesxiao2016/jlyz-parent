package cn.dlbdata.dj.web.util;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class UploadUtil {

    public static String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response,String userId) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        if (StringUtils.isEmpty(userId)) {
            userId = DigitUtil.generatorLongId() + "";
        }
        // 文件保存路径（可以考虑存放到固定目录下）
        String path = request.getSession().getServletContext().getRealPath("upload") + File.separator + userId;
        String originalFilename = file.getOriginalFilename();
        Long id = DigitUtil.generatorLongId();
        String fileName = id + originalFilename.substring(originalFilename.lastIndexOf("."));
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            return null;
        }
        return path+"\\"+fileName;
    }
}
