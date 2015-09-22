package me.rowkey.libs.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Author: Bryant Hang
 * Date: 15/1/21
 * Time: 下午3:08
 */
public class ImageUtil {
    /**
     * 判断文件是否是图片
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static boolean isImage(File file) throws IOException {
//        ImageInputStream iis = ImageIO.createImageInputStream(file);
//
//        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
//
//        return iter.hasNext();

        BufferedImage bi = ImageIO.read(file);

        return bi != null;
    }
}
