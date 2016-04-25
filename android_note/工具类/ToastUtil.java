package com.lingchat.lc.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lingchat.lc.R;

/**
 * 创建者:Zeping.Yin
 * 创建时间:2016/3/2 15:19
 * 功能说明:Toast提示工具
 */
public class ToastUtil {
    private static Toast toast = null;
    /**
     * 普通文本消息提示
     * @param context
     * @param text
     * @param duration
     */
    public static void TextToast(Context context,CharSequence text,int duration){
        //创建一个Toast提示消息
        toast = Toast.makeText(context, text, duration);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //显示消息
        toast.show();
    }

    /**
     * 普通文本消息提示
     * @param context
     * @param text
     */
    public static void TextToast(Context context,CharSequence text){
        TextToast(context, text, Toast.LENGTH_LONG);
    }

    /**
     * 带图片消息提示
     * @param context
     * @param ImageResourceId
     * @param text
     * @param duration
     */
    public static void ImageToast(Context context,int ImageResourceId,CharSequence text,int duration){
        //创建一个Toast提示消息
        toast = Toast.makeText(context, text, duration);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取Toast提示消息里原有的View
        View toastView = toast.getView();
        //创建一个ImageView
        ImageView img = new ImageView(context);
        img.setImageResource(ImageResourceId);
        if(toastView instanceof ViewGroup){
            ((ViewGroup) toastView).addView(img, 0);
            toast.setView(toastView);
        }else{
            //创建一个LineLayout容器
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.toast_round_shape);
            toastView.setBackgroundResource(R.color.toast);
            //向LinearLayout中添加ImageView和Toast原有的View
            ll.addView(img);
            ll.addView(toastView);
            //将LineLayout容器设置为toast的View
            toast.setView(ll);
        }
        //显示消息
        toast.show();
    }

    /**
     * 带图片消息提示
     * @param context
     * @param ImageResourceId
     * @param text
     */
    public static void ImageToast(Context context,int ImageResourceId,CharSequence text){
        ImageToast(context, ImageResourceId, text, Toast.LENGTH_LONG);
    }

    /**
     * 带图片消息提示
     * @param context
     * @param ImageResourceId
     */
    public static void ImageToast(Context context, int ImageResourceId){
        ImageToast(context, ImageResourceId, Toast.LENGTH_LONG);
    }

    /**
     * 带图片消息提示
     * @param context
     * @param ImageResourceId
     */
    public static void ImageToast(Context context,int ImageResourceId, int duration){
        //创建一个Toast提示消息
        toast = Toast.makeText(context, "", duration);
        //设置Toast提示消息在屏幕上的位置
        toast.setGravity(Gravity.CENTER, 0, 0);
        //创建一个LineLayout容器
        //创建一个ImageView
        ImageView img = new ImageView(context);
        img.setImageResource(ImageResourceId);
        //将ImageView容器设置为toast的View
        toast.setView(img);
        //显示消息
        toast.show();
    }
}
