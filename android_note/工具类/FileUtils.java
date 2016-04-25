package com.lingchat.lc.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.lingchat.lc.Constants;
import com.lingchat.lc.data_manager.AppFileManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 创建者:Zeping.Yin
 * 创建时间:2016/3/10 18:02
 * 功能说明:
 */
public class FileUtils {

    /**
     * 用当前时间给取得的图片命名
     *
     */
    public static String getPhotoFileName() {
        return "tmp_pic_" + System.currentTimeMillis() + ".jpg";
    }

    public static String getLingchatPhotoFileName() {
        return "lingchat." + System.currentTimeMillis() + ".jpg";
    }

    /**
     * 用当前时间给取得的视频命名
     *
     */
    public static String getVideoFileName() {
        return "tmp_pic_" + System.currentTimeMillis() + ".mp4";
    }
    public static String getLingchatideoFileName() {
        return "lingchat." + System.currentTimeMillis() + ".mp4";
    }

    /**
     * 用当前时间给取得的语音命名
     *
     */
    public static String getAudioFileName() {
        return "tmp_pic_" + System.currentTimeMillis() + ".amr";
    }

    /**
     * 用当前时间给取得的文件命名
     *
     */
    public static String getFileName(String fileType) {
        return "tmp_pic_" + System.currentTimeMillis() + fileType;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/18 15:55
     * 功能说明: 通过图片文件名生成视频文件名.mp4
     */
    public static String getVideoPathFromImagePath(String imagePath) {
        return getFileNameNoSuffix(imagePath)+".mp4";
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/18 15:55
     * 功能说明: 通过视频文件名生成图片文件名.jpg
     */
    public static String getImagePathFromVideoPath(String imagePath) {
        return getFileNameNoSuffix(imagePath)+".jpg";
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/18 15:55
     * 功能说明: 通过视频文件名生成图片文件名.jpg
     */
    public static String getVoicePath(String imagePath) {
        return getFileNameNoSuffix(imagePath)+".amr";
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/18 15:53
     * 功能说明: 获取文件名的后缀
     */
    public static String getFileSuffix(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            LogUtil.error(Constants.TAG, "get register file path failed");
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return suffix;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/18 15:53
     * 功能说明: 获取文件名不包含点和后缀
     */
    public static String getFileNameNoSuffix(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            LogUtil.error(Constants.TAG, "get register file path failed");
        }
        String suffix = fileName.substring(0, fileName.lastIndexOf("."));
        return suffix;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/21 15:27
     * 功能说明: 保存输入流到文件
     */
    public static void inputStream2File(InputStream inputStream, File file) {
        OutputStream outputStream = null;
        try {

            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/21 15:27
     * 功能说明: 保存输入流到文件
     */
    public static boolean saveInputStreamToFilePath(InputStream inputStream, String filePath) {
        if(TextUtils.isEmpty(filePath)){
            return false;
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(filePath));
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/22 11:05
     * 功能说明: 递归删除文件夹下的所有文件
     */
    public static boolean deleteAllFiles(String root) {
        File file = new File(root);
        if(file.exists()){
            deleteAllFiles(file);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/22 11:05
     * 功能说明: 递归删除文件夹下的所有文件
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/4 17:23
     * 功能说明: 保存图片
     */
    public static void saveImage(Bitmap photo, String spath) {
        try {
            if(new File(spath).exists()){
                new File(spath).delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            return true;
        }catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return false;
        }

    }
    /**
     * 重命名
     */
    public static String  renameFile(String cacheDir,long userId,String localPath,String generateFileName){
        String newFilePath = "";
            //重命名图片文件
            File imageFile = new File(localPath);
            if (imageFile.exists()) {
                newFilePath  = cacheDir + userId + "_" + generateFileName;
                imageFile.renameTo(new File(newFilePath));
        }
        return newFilePath;
    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else{
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }
}
