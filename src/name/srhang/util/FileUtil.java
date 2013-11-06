package name.srhang.util;

import java.io.*;

/**
 * @author Bryant
 *         文件工具类，目前仅提供删除文件或文件夹功能。
 */
public class FileUtil {
    /**
     * 删除文件，可以是单个文件或文件夹
     *
     * @param fileName 待删除的文件名
     * @return 文件删除成功返回true, 否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            //System.out.println("删除文件失败：" + fileName + "文件不存在");
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            //System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            //System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dir 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符     
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出     
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            //System.out.println("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)     
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件     
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            //删除子目录     
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            //System.out.println("删除目录失败");
            return false;
        }

        //删除当前目录     
        if (dirFile.delete()) {
            //System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            //System.out.println("删除目录" + dir + "失败！");
            return false;
        }
    }

    /**
     * 复制文件到指定路径
     */
    public static File copyFileToPath(File f, String path) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File toFile = new File(path);
        try {
            bis = new BufferedInputStream(new FileInputStream(f));
            bos = new BufferedOutputStream(new FileOutputStream(toFile));

            byte[] buf = new byte[4096];

            int len = -1;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bis)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (null != bos)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return toFile;
    }

}    
