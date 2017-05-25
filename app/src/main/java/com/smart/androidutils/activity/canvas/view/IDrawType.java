package com.smart.androidutils.activity.canvas.view;

import android.graphics.Canvas;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public interface IDrawType {
    void onDrawLine(Canvas canvas);
    void onDrawRect(Canvas canvas);
    void onDrawRoundRect(Canvas canvas);
    void onDrawCircle(Canvas canvas);
    void onDrawColor(Canvas canvas);
    void onDrawBitmap(Canvas canvas);
    void onDrawText(Canvas canvas);
}
