package com.sdk.util.file;

import java.io.Closeable;
import java.io.IOException;

/**
 *     time  : 2016/10/09
 *     desc  : 关闭相关工具类
 */
public final class UtilsCloseIO {

    private UtilsCloseIO() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     *  closeables closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     *  closeables closeables
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
