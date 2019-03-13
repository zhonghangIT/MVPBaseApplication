package com.image.frescolibrary;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/**
 * @author : zhonghang
 * Date: 2017-04-25
 * Time: 10:09
 * 用于展示网络图片的控制器
 */
public class WebImageController {
    /**
     * 私有构造器，确保控制器只能有builder构建
     */
    private WebImageController(Context context) {
        this.mContext = context;
    }

    /**
     * 图片的网络地址
     */
    private String url;
    /**
     * 图片的占位图
     */

    private Drawable placeDrawable;
    /**
     * 图片加载失败图
     */
    private Drawable failDrawable;
    /**
     * 图片是否以GIF形式展示
     */
    private boolean playAnimation;
    /**
     * 图片下载成功的回调
     */
    private ImageDownloadSuccess onImageDownloadSuccessListener;
    /**
     * 图片下载失败的回调
     */
    private ImageDownloadFail onImageDownloadFailListener;
    /**
     * 图片下载进度的回调
     */
    private ImageDownloadProgress onImageDownloadProgressListener;
    private Context mContext;

    /**
     * 图片下载成功的接口
     */
    public interface ImageDownloadSuccess {
        /**
         * 图片下载成功的回调方法
         */
        void onDownloadSuccess();
    }

    /**
     * 图片下载失败的接口
     */
    public interface ImageDownloadFail {
        /**
         * 图片下载失败的回调方法
         */
        void onDwonloadFail();
    }

    /**
     * 图片下载进度的接口
     */
    public interface ImageDownloadProgress {
        /**
         * 图片下载进度的回调方法
         *
         * @param progress 进度范围0-10000
         */
        void progress(int progress);
    }

    /**
     * 网络图片控制器的builder
     */
    public static class WebImageControllerBuilder {
        private WebImageController webImageParams;
        private Context context;

        public WebImageControllerBuilder(Context context, String url) {
            webImageParams = new WebImageController(context);
            webImageParams.url = url;
            this.context = context;
        }

        /**
         * 是指网络图片的url
         *
         * @param url 图片的网络地址
         * @return
         */
        public WebImageControllerBuilder setUrl(String url) {
            webImageParams.url = url;
            return this;
        }

        /**
         * 设置图片的占位图
         *
         * @param placeDrawable 占位图
         * @return
         */
        public WebImageControllerBuilder setPlaceDrawable(Drawable placeDrawable) {
            webImageParams.placeDrawable = placeDrawable;
            return this;
        }

        /**
         * 设置图片失败图
         *
         * @param failDrawable 失败图
         * @return
         */
        public WebImageControllerBuilder setFailDrawable(Drawable failDrawable) {
            webImageParams.failDrawable = failDrawable;
            return this;
        }

        /**
         * 设置图片是否播放GIF
         *
         * @param playAnimation 是否播放gif，默认为false
         * @return
         */
        public WebImageControllerBuilder setPlayAnimation(boolean playAnimation) {
            webImageParams.playAnimation = playAnimation;
            return this;
        }

        /**
         * 设置图片下载成功的监听器
         *
         * @param onImageDownloadSuccessListener
         * @return
         */
        public WebImageControllerBuilder setOnImageDownloadSuccessListener(ImageDownloadSuccess onImageDownloadSuccessListener) {
            webImageParams.onImageDownloadSuccessListener = onImageDownloadSuccessListener;
            return this;
        }

        /**
         * 设置图片下载失败的监听器
         *
         * @param onImageDownloadFailListener
         * @return
         */
        public WebImageControllerBuilder setOnImageDownloadFailListener(ImageDownloadFail onImageDownloadFailListener) {
            webImageParams.onImageDownloadFailListener = onImageDownloadFailListener;
            return this;
        }

        /**
         * 设置图片加载进度监听器
         *
         * @param onImageDownloadProgressListener
         * @return
         */
        public WebImageControllerBuilder setOnImageDownloadProgressListener(ImageDownloadProgress onImageDownloadProgressListener) {
            webImageParams.onImageDownloadProgressListener = onImageDownloadProgressListener;
            return this;
        }

        /**
         * 设置失败图
         *
         * @param failId 失败图的资源id
         * @return
         */
        public WebImageControllerBuilder setFailDrawable(int failId) {
            webImageParams.failDrawable = context.getResources().getDrawable(failId);
            return this;
        }

        /**
         * 设置失败图
         *
         * @param failPath 失败图在sdcard中的路径
         * @return
         */
        public WebImageControllerBuilder setFailDrawable(String failPath) {
            webImageParams.failDrawable = Drawable.createFromPath(failPath);
            return this;
        }

        /**
         * 设置占位图
         *
         * @param placeId 占位图的资源id
         * @return
         */
        public WebImageControllerBuilder setPlaceDrawable(int placeId) {
            webImageParams.placeDrawable = context.getResources().getDrawable(placeId);
            return this;
        }

        /**
         * 设置占位图
         *
         * @param placePath 占位图在sdcard中的路径
         * @return
         */
        public WebImageControllerBuilder setPlaceDrawable(String placePath) {
            webImageParams.placeDrawable = Drawable.createFromPath(placePath);
            return this;
        }

        /**
         * 创建网络图片控制器
         *
         * @return 网络图片控制器
         */
        public WebImageController build() {
            return webImageParams;
        }

        /**
         * 展示图片
         *
         * @param draweeView 展示图片的控件
         */
        public void showWebImage(DraweeView<GenericDraweeHierarchy> draweeView) {
            webImageParams.showWebImage(draweeView);
        }
    }

    /**
     * 展示网络图片
     *
     * @param draweeView 展示控件
     */
    public void showWebImage(DraweeView<GenericDraweeHierarchy> draweeView) {
        //网络路径不允许为空
        if (url == null) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setUri(url)
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        //图片名称
                        if (onImageDownloadFailListener != null) {
                            onImageDownloadFailListener.onDwonloadFail();
                        }
                    }

                    @Override
                    public void onFinalImageSet(String id, @NonNull ImageInfo imageInfo, @NonNull Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        //图片名称
                        if (onImageDownloadSuccessListener != null) {
                            onImageDownloadSuccessListener.onDownloadSuccess();
                        }
                    }
                }).setAutoPlayAnimations(playAnimation).build();
        GenericDraweeHierarchy genericDraweeHierarchy = draweeView.getHierarchy();
        if (placeDrawable != null) {
            genericDraweeHierarchy.setPlaceholderImage(placeDrawable);
        }
        if (failDrawable != null) {
            genericDraweeHierarchy.setFailureImage(failDrawable);
        }
        genericDraweeHierarchy.setProgressBarImage(new ProgressDrawable() {
            @Override
            protected boolean onLevelChange(int level) {
                //进度值为0-10000
                if (onImageDownloadProgressListener != null) {
                    onImageDownloadProgressListener.progress(level);
                }
                return super.onLevelChange(level);
            }
        });
        controller.setHierarchy(genericDraweeHierarchy);
        draweeView.setController(controller);
    }
}
