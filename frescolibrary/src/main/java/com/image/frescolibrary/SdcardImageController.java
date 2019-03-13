package com.image.frescolibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * @author : zhonghang zhonghang@feinno.com
 * Date: 2017-04-25
 * Time: 10:09
 * 展示Sdcard中的图片的控制器
 */
public class SdcardImageController {
    //图片的本地路径
    private String path;
    //图片展示的占位图
    private Drawable placeDrawable;
    //图片展示失败图
    private Drawable failDrawable;
    //是否以GIF动画的形式展示
    private boolean playAnimation;
    //加载图片时的缓存的大小，不一定原图加载
    private int width;
    private int height;
    private Context mContext;

    /**
     * 私有构造器，保证只能使用builder方式创建
     */
    private SdcardImageController(Context context) {
        mContext = context;
    }

    /**
     * 构造加载本地图片的控制器的builder
     */
    public static class SdcardControllerBuilder {
        private SdcardImageController sdcardParams;
        private Context context;

        public SdcardControllerBuilder(Context context, @NonNull String path) {
            sdcardParams = new SdcardImageController(context);
            sdcardParams.path = path;
            this.context = context;
        }

        /**
         * 设置本地图片的路径
         *
         * @param path 图片的本地路径，不能为空
         * @return
         */
        public SdcardControllerBuilder setPath(@NonNull String path) {
            sdcardParams.path = path;
            return this;
        }

        /**
         * 设置本地图片加载时不加载全图加载缩略图，尤其是在相册界面能够加载更多的图片
         *
         * @param width  缩略图的宽
         * @param height 缩略图的高
         * @return
         */
        public SdcardControllerBuilder setResizeOptions(int width, int height) {
            sdcardParams.width = width;
            sdcardParams.height = height;
            return this;
        }

        /**
         * 设置图片的占位图
         *
         * @param placeDrawable 占位图
         * @return
         */
        public SdcardControllerBuilder setPlaceDrawable(Drawable placeDrawable) {
            sdcardParams.placeDrawable = placeDrawable;
            return this;
        }

        /**
         * 设置图片的占位图
         *
         * @param placeId 占位图的资源id
         * @return
         */
        public SdcardControllerBuilder setPlaceDrawable(int placeId) {
            sdcardParams.placeDrawable = context.getResources().getDrawable(placeId);
            return this;
        }

        /**
         * 设置图片的站位图
         *
         * @param placePath 占位图的本地sdcard路径
         * @return
         */
        public SdcardControllerBuilder setPlaceDrawable(String placePath) {
            sdcardParams.placeDrawable = Drawable.createFromPath(placePath);
            return this;
        }

        /**
         * 设置图片的失败图
         *
         * @param failDrawable 失败图
         * @return
         */
        public SdcardControllerBuilder setFailDrawable(Drawable failDrawable) {
            sdcardParams.failDrawable = failDrawable;
            return this;
        }

        /**
         * 设置图片是否以gif形式展示
         *
         * @param playAnimation 是否播放动画，默认为false
         * @return
         */
        public SdcardControllerBuilder setPlayAnimation(boolean playAnimation) {
            sdcardParams.playAnimation = playAnimation;
            return this;
        }

        /**
         * 设置图片的失败图
         *
         * @param failId 失败图的资源id
         * @return
         */
        public SdcardControllerBuilder setFailDrawable(int failId) {
            sdcardParams.failDrawable = context.getResources().getDrawable(failId);
            return this;
        }

        /**
         * 设置图片的失败图
         *
         * @param failPath 失败图在sdcard的路径
         * @return
         */
        public SdcardControllerBuilder setFailDrawable(String failPath) {
            sdcardParams.failDrawable = Drawable.createFromPath(failPath);
            return this;
        }

        /**
         * 创建本地图片控制器
         *
         * @return 本地图片控制器
         */
        public SdcardImageController build() {
            return sdcardParams;
        }

        /**
         * 直接使用控制器展示图片
         *
         * @param draweeView 展示图片的控件
         */
        public void showSdcardImage(DraweeView<GenericDraweeHierarchy> draweeView) {
            sdcardParams.showSdcardImage(draweeView);
        }
    }

    /**
     * 展示sdcard的本地图片
     *
     * @param draweeView 展示图片的控件
     */
    public void showSdcardImage(DraweeView<GenericDraweeHierarchy> draweeView) {
        //图片路径不允许为空
        if (TextUtils.isEmpty(this.path)) {
//            throw new NullPointerException("展示本地图片时sdcard路径不允许为空");
            return;
        }
        //fresco的图片控制器
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder();
        //设置本地图片路径的Uri
        ImageRequestBuilder requestBuidler = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse("file://" + this.path));
        if (width != 0 && height != 0) {
            requestBuidler.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = requestBuidler.setAutoRotateEnabled(true).build();
        //设置图片的展示效果
        GenericDraweeHierarchy genericDraweeHierarchy = draweeView.getHierarchy();
        //设置占位图
        if (this.placeDrawable != null) {
            genericDraweeHierarchy.setPlaceholderImage(this.placeDrawable);
        }
        //设置失败图
        if (this.failDrawable != null) {
            genericDraweeHierarchy.setFailureImage(this.failDrawable);
        }
        builder.setImageRequest(request);
        //设置是否播放动画
        builder.setAutoPlayAnimations(this.playAnimation);
        DraweeController controller = builder.build();
        controller.setHierarchy(genericDraweeHierarchy);
        draweeView.setController(controller);
    }
}