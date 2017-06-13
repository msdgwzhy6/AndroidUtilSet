package com.sdk.util.app;

import android.content.Intent;
import android.net.Uri;

import com.sdk.util.file.UtilFile;

import java.io.File;

/**
 * @author xander on  2017/5/25.
 */

public final class UtilShare {
    /**
     * 获取分享文本的意图
     *
     *  content 分享文本
     *  intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     *  content   文本
     *  imagePath 图片文件路径
     *  intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, UtilFile.getFileByPath(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     *  content 文本
     *  image   图片文件
     *  intent
     */
    public static Intent getShareImageIntent(String content, File image) {
        if (!UtilFile.isFileExists(image)) return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     *  content 分享文本
     *  uri     图片uri
     *  intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
