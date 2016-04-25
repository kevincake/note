package com.lingchat.lc.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lingchat.lc.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建者:yin_zeping
 * 创建时间:2016/3/4 14:49
 * 功能说明:图片处理工具类
 */
public class ImageUtils {

    public static final int MAX_IMAGE_SIZE = 300;//KB
    public static final float COMMON_IMAGE_HEIGHT = 800f;//px
    public static final float COMMON_IMAGE_WIDTH = 480f;//px
    /**
     * 创建者: eava
     * 创建时间: 2016/4/8 10:21
     * 功能说明: 根据bitmap的大小，来设置不低于最小和高于最大的图片
     */
    public static Bitmap getMedianBitMap(Context context,Bitmap bitmap){
        Bitmap newBitpMap = null;
        int width = (int) context.getResources().getDimension(R.dimen.divider_px490);
        int height = (int) context.getResources().getDimension(R.dimen.divider_px600);
        int realHeight = bitmap.getHeight();
        int realWidth = bitmap.getWidth();
        if (realWidth > width || realHeight > height) {
            newBitpMap = zoomImage(bitmap,width,height);
        } else {
            newBitpMap = zoomImage(bitmap,width,height);
//            getAcqunaintanceImageIfNeedZoom(userId, fileName, imageView, null, false, realWidth, realHeight);
        }
        return newBitpMap;
    }

    //通过传入位图,新的宽.高比进行位图的缩放操作
    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {

        // load the origial Bitmap
        Bitmap BitmapOrg = bitmap;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        // make a Drawable from Bitmap to allow to set the Bitmap
        // to the ImageView, ImageButton or what ever
        return new BitmapDrawable(resizedBitmap);

    }

    /***
     * 加载本地图片
     * @param context：主运行函数实例
     * @param bitAdress：图片地址，一般指向R下的drawable目录
     * @return
     */
    public static final Bitmap CreatImage(Context context, int bitAdress) {
        Bitmap bitmaptemp = null;
        bitmaptemp = BitmapFactory.decodeResource(context.getResources(),
                bitAdress);
        return bitmaptemp;
    }
    /***
     * 图片分割
     *
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param imgBit
     * ：图片
     * @param x
     * ：X轴起点坐标
     * @param y
     * ：Y轴起点坐标
     * @param w
     * ：单一图片的宽度
     * @param h
     * ：单一图片的高度
     * @param line
     * ：第几列
     * @param row
     * ：第几行
     */
    public final void cuteImage(Canvas g, Paint paint, Bitmap imgBit, int x,
                                int y, int w, int h, int line, int row) {
        g.clipRect(x, y, x + w, h + y);
        g.drawBitmap(imgBit, x - line * w, y - row * h, paint);
        g.restore();
    }
    /***
     * 图片的缩放方法
     *
     * @param bgimage
     * ：源图片资源
     * @param newWidth
     * ：缩放后宽度
     * @param newHeight
     * ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        int width = bgimage.getWidth();
        int height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        float scale = Math.min(scaleWidth, scaleHeight);
        matrix.postScale(scale, scale);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
                matrix, true);
        return bitmap;
    }



    /***
     * 绘制带有边框的文字
     *
     * @param strMsg
     * ：绘制内容
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param setx
     * ：：X轴起始坐标
     * @param sety
     * ：Y轴的起始坐标
     * @param fg
     * ：前景色
     * @param bg
     * ：背景色
     */
    public void drawText(String strMsg, Canvas g, Paint paint, int setx,
                         int sety, int fg, int bg) {
        paint.setColor(bg);
        g.drawText(strMsg, setx + 1, sety, paint);
        g.drawText(strMsg, setx, sety-1, paint);
        g.drawText(strMsg, setx, sety + 1, paint);
        g.drawText(strMsg, setx-1, sety, paint);
        paint.setColor(fg);
        g.drawText(strMsg, setx, sety, paint);
        g.restore();
    }

    /**
     * 添加文字到图片，类似水印文字。
     * @param gContext
     * @param gResId
     * @param gText
     * @return
     */
    public static Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61,61,61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale*5));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
//      int x = (bitmap.getWidth() - bounds.width()) / 2;
//      int y = (bitmap.getHeight() + bounds.height()) / 2;
        //draw  text  to the bottom
        int x = (bitmap.getWidth() - bounds.width())/10*9 ;
        int y = (bitmap.getHeight() + bounds.height())/10*9;
        canvas.drawText(gText, x , y, paint);

        return bitmap;
    }

    //根据一个Uri给ImageView设置图片
    public static ImageView setImageViewByUri(Context context,ImageView iv,Uri uri){
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
        iv.setImageBitmap(BitmapFactory.decodeStream(input));
        return iv;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @param min
     * @return
     */
    public Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 根据原图和变长绘制圆形图片
     *
     * @param source
     * @return
     */
    public static Bitmap getCircleImage(Bitmap source)
    {
        int min = source.getHeight()<=source.getWidth()?source.getHeight():source.getWidth();
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN，参考上面的说明
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static Bitmap zoomImageByWindowManager(Bitmap bitmap, Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        float scaleWidth = (float)width/bitmapWidth;
        float scaleHight = (float)height/bitmapHeight;
        float scale = Math.min(scaleWidth, scaleHight);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
        return resizeBmp;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/25 17:32
     * 功能说明: 获取图片并压缩
     */
    public static Bitmap getImageFromUri(Context context, Uri uri) {
        String srcPath = FileUtils.getRealPathFromURI(context, uri);
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = COMMON_IMAGE_HEIGHT;//这里设置高度为800f
        float ww = COMMON_IMAGE_WIDTH;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return bitmap;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/28 17:05
     * 功能说明: 获取图片的大小
     */
    public static int getBitmapSize(Bitmap bitmap){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        // Do not compress
        newOpts.inSampleSize = 1;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param imgPath image path
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param image
     * @param outPath
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage( bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

//======================================================================================================================

    /**
     * get the orientation of the bitmap {@link android.media.ExifInterface}
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * caculate the bitmap sampleSize
     * @return
     */
    public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height/ (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * rotate the bitmap
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 压缩指定路径的图片，并得到图片对象
     * @param path bitmap source path
     * @return Bitmap {@link android.graphics.Bitmap}
     */
    public final static Bitmap compressBitmapByMeasure(String path, int rqsW, int rqsH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
     * @param inputPath
     * @param outputPath
     * @param isDelSrc
     * @return
     */
    public final static Bitmap compressBitmap(String inputPath, String outputPath, boolean isDelSrc) {
       return compressBitmap(inputPath, outputPath, (int) COMMON_IMAGE_WIDTH, (int) COMMON_IMAGE_HEIGHT, isDelSrc);
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
     * @param context
     * @param inputPath
     * @param outputPath
     * @return
     */
    public final static Bitmap compressBitmap(Context context, Uri inputPath, Uri outputPath) {
        return compressBitmap(context, inputPath, outputPath, false);
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
     * @param context
     * @param inputPath
     * @param outputPath
     * @param isDelSrc
     * @return
     */
    public final static Bitmap compressBitmap(Context context, Uri inputPath, Uri outputPath, boolean isDelSrc) {
        String srcPath = FileUtils.getRealPathFromURI(context, inputPath);
        String destPath = FileUtils.getRealPathFromURI(context, outputPath);
        return compressBitmap(srcPath, destPath, (int) COMMON_IMAGE_WIDTH, (int) COMMON_IMAGE_HEIGHT, isDelSrc);
    }

    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
     * @param inputPath
     * @param outputPath
     * @param rqsW
     * @param rqsH
     * @param isDelSrc
     * @return
     */
    public final static Bitmap compressBitmap(String inputPath, String outputPath, int rqsW, int rqsH, boolean isDelSrc) {
        Bitmap bitmap = compressBitmapByMeasure(inputPath, rqsW, rqsH);
        if(bitmap!=null){
            File srcFile = new File(inputPath);
            int degree = getDegress(inputPath);
            try {
                if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
                File file = new File(outputPath);
                file.deleteOnExit();
                FileOutputStream  fos = new FileOutputStream(file);
                //文件大于MAX_IMAGE_SIZE，进行压缩
                if(getBitmapSize(bitmap)/1024>MAX_IMAGE_SIZE){
                    bitmap.compress(Bitmap.CompressFormat.PNG, 60, fos);
                }else{
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }
                fos.close();
                if (isDelSrc) srcFile.deleteOnExit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
