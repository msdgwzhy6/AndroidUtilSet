package com.smart.androidutils.activity.canvas.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.util.InitUtil;
import com.util.UtilEncript;
import com.util.file.UtilSP;

import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_BITMAP;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_CIRCLE;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_COLOR;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_LINE;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_RECT;
import static com.smart.androidutils.activity.canvas.constant.ConsData.TAG_TEXT;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class XanderView extends View implements IDrawType{
    Paint mPaint;
    public XanderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
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
        mPaint.reset();
        canvas.save();
        //分发 绘制
        switch (tag){
            case TAG_LINE:
                onDrawLine(canvas);
                break;
            case TAG_CIRCLE:
                onDrawCircle(canvas);
                break;
            case TAG_BITMAP:
                onDrawBitmap(canvas);
                break;
            case TAG_RECT:
                onDrawRect(canvas);
                break;
            case TAG_TEXT:
                onDrawText(canvas);
                break;
            default:
                onDrawColor(canvas);
        }
        canvas.restore();
    }

    @Override
    public void onDrawLine(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(-10,170,200,170,mPaint);//这四个坐标全是相对于父容器的，如果小于0，则为0
        canvas.drawLine(0,200,200,200,mPaint);
        canvas.drawLine(10,220,210,220,mPaint);
        canvas.drawLine(20,240,220,240,mPaint);
        canvas.drawLine(30,260,230,260,mPaint);
    }

    @Override
    public void onDrawRect(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        Log.i("xxx", "onDrawRect" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100,300,300,400,20,20,mPaint);
        }
    }

    @Override
    public void onDrawRoundRect(Canvas canvas) {

    }

    @Override
    public void onDrawCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200,100,100,mPaint);
    }

    @Override
    public void onDrawColor(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
    }

    @Override
    public void onDrawBitmap(Canvas canvas) {
        Bitmap bitmap = UtilSP.getInstance(InitUtil.getContext())
                .initSP("canvas")
                .getBitmap(UtilEncript.getMD5("canvas"));
        assert bitmap != null;
        canvas.drawBitmap(bitmap, 200, 200, null);
    }

    @Override
    public void onDrawText(Canvas canvas) {
        RectF targetRect = new RectF(50, 50, 500, 200);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(3);
        paint.setTextSize(20);
        String testString = "第三个是啊大大大高档货大开大个的改善读过ijkJQKA:1234";
        paint.setColor(Color.CYAN);
        canvas.drawRoundRect(targetRect,50,50, paint);
//        canvas.drawRect(targetRect, paint);
        paint.setColor(Color.RED);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        int baseline = (int) ((targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, targetRect.centerX(), baseline, paint);
    }


}
