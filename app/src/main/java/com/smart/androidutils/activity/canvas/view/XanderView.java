package com.smart.androidutils.activity.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static com.smart.androidutils.constant.ConstantData.TAG_CIRCLE;
import static com.smart.androidutils.constant.ConstantData.TAG_COLOR;
import static com.smart.androidutils.constant.ConstantData.TAG_LINE;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class XanderView extends View implements IDrawType{
    public XanderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XanderView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Integer tag = (Integer) getTag();
        if (tag == null) {
            tag = TAG_COLOR;
        }
        //分发 绘制
        switch (tag){
            case TAG_LINE:
                onDrawLine(canvas);
                break;
            case TAG_CIRCLE:
                onDrawCircle(canvas);
                break;
            default:
                onDrawColor(canvas);
        }
    }

    @Override
    public void onDrawLine(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawLine(200,100,100,200,paint);
    }

    @Override
    public void onDrawRect(Canvas canvas) {

    }

    @Override
    public void onDrawRoundRect(Canvas canvas) {

    }

    @Override
    public void onDrawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,100,100,paint);
    }

    @Override
    public void onDrawColor(Canvas canvas) {
        Paint paint = new Paint();
        canvas.drawColor(Color.GREEN);
    }
}
