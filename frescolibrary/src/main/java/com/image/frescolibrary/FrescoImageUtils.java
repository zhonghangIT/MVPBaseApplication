package com.image.frescolibrary;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * @author : zhonghang
 * 用于加载图片的统一入口，目前图片分为三种类型:本地图片，网络图片，云存储图片。
 * 所有的加载图片的方法使用这个共有的入口。
 */
public class FrescoImageUtils {

    private static FrescoImageUtils ourInstance;

    public static FrescoImageUtils getInstance() {
        if (ourInstance == null) {
            ourInstance = new FrescoImageUtils();
        }
        return ourInstance;
    }

    private FrescoImageUtils() {

    }

    public static void init(Context context) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
//                .setBitmapMemoryCacheParamsSupplier()
//                .setCacheKeyFactory(cacheKeyFactory)
                .setDownsampleEnabled(true)
//                .setWebpSupportEnabled(true)
//                .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
//                .setExecutorSupplier(executorSupplier)
//                .setImageCacheStatsTracker(imageCacheStatsTracker)
//                .setMainDiskCacheConfig(mainDiskCacheConfig)
//                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                .setNetworkFetcher(new OkHttpNetworkFetcher(client))
//                .setPoolFactory(poolFactory)
//                .setProgressiveJpegConfig(progressiveJpegConfig)
//                .setRequestListeners(requestListeners)
//                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
        Fresco.initialize(context, config);
    }

    /**
     * 创建加载本地图片的Builder
     *
     * @param path 加载本地图片必须传递进来path
     * @return SdcardControllerBuilder
     */
    public SdcardImageController.SdcardControllerBuilder createSdcardBuilder(Context context, @NonNull String path) {
        SdcardImageController.SdcardControllerBuilder builder = new SdcardImageController.SdcardControllerBuilder(context, path);
        return builder;
    }

    /**
     * 创建加载网络图片的builder
     *
     * @param url 加载
     * @return WebImageControllerBuilder
     */
    public WebImageController.WebImageControllerBuilder createWebImageParamsBuilder(Context context, String url) {
        WebImageController.WebImageControllerBuilder builder = new WebImageController.WebImageControllerBuilder(context, url);
        return builder;
    }


    /**
     * 判断该文件是否是图片
     *
     * @param imagePath 文件路径
     * @return true 是图片 false 图片文件损坏
     */
    public static boolean isImage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return false;
        }
        File file = new File(imagePath);
        if (!file.exists()) {
            return false;
        }
        //只读取图片的宽高，不将该文件读取到内存中，目的是下方设置图片的宽高时会使用，加载图片更改成了fresco
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        if (options.mCancel || options.outWidth == -1
                || options.outHeight == -1 || options.outWidth == 0 || options.outHeight == 0) {
            //表示图片已损毁
            return false;
        }
        return true;
    }


    public static void clearCache() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }


    public static void clearCache(String path) {

        Uri uri = Uri.parse("file://" + path);

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);

        // combines above two lines
        imagePipeline.evictFromCache(uri);
    }

}
