package com.lingchat.lc.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * 创建者:Zeping.Yin
 * 创建时间:2016/3/7 17:35
 * 功能说明:
 */
public class QrCodeGeneraterUtil {
    private int QRCODE_SIZE = 300;
    private int PORTRAIT_SIZE = 100;
    private int BLACK = 0xff000000;

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/8 10:33
     * 功能说明: 构建二维码图片
     * logo为null中间则无logo
     * width小于100时，宽高取默认值300
     */
    public Bitmap createQRCode(String text, Bitmap logo, int width) {
        if(width>=100)
            this.QRCODE_SIZE=width;
        //生成二维码图片
        if(text==null||text.equals(""))
            return null;
        Bitmap qrcode=createQRCode(text);
        //生成带logo的二维码图片
        if(logo!=null)
            createQRCodeWithPortrait(qrcode, logo);
        return qrcode;
    }

    /**
     * 创建者: Zeping.Yin
     * 创建时间: 2016/3/8 10:33
     * 功能说明: 根据数据生成二维码
     */
    private Bitmap createQRCode(String str){
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix matrix;
            matrix = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = BLACK;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 构造中间带logo的二维码
     *
     * @param qr
     * @param logo
     */
    private void createQRCodeWithPortrait(Bitmap qr, Bitmap logo) {
        int portrait_W = PORTRAIT_SIZE;
        int portrait_H = PORTRAIT_SIZE;
        int left = (QRCODE_SIZE - portrait_W) / 2;
        int top = (QRCODE_SIZE - portrait_H) / 2;
        int right = left + portrait_W;
        int bottom = top + portrait_H;
        Rect rect1 = new Rect(left, top, right, bottom);
        Canvas canvas = new Canvas(qr);
        Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
        canvas.drawBitmap(logo, rect2, rect1, null);
    }
}
