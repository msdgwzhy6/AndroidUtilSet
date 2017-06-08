package com.zxing.encoding;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Random;

/**
 * @author Ryan Tang
 *
 */
public final class EncodingHandler {
	private static final int BLACK = 0xff000000;
	
	public static Bitmap createQRCode(String str,int widthAndHeight) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
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
	}
	
	/*
	public static Bitmap Create2DCode(String str) throws WriterException {       
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败       
        BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, 400, 400);       
        int width = matrix.getWidth();       
        int height = matrix.getHeight();       
        //二维矩阵转为一维像素数组,也就是一直横着排了       
        int[] pixels = new int[width * height];       
        for (int y = 0; y < height; y++) {       
            for (int x = 0; x < width; x++) {       
                if(matrix.get(x, y)){       
                    pixels[y * width + x] = 0xff000000;       
                }       
                       
            }       
        }       
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);       
        //通过像素数组生成bitmap,具体参考api       
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);       
        return bitmap;       
    }   
    */
    /**
     * 根据指定内容生成自定义宽高的二维码图片
     *
     * param logoBm
     * logo图标
     * param content
     * 需要生成二维码的内容
     * param width
     * 二维码宽度
     * param height
     * 二维码高度
     * throws WriterException
     * 生成二维码异常
     */
    public static Bitmap createQRCode(Bitmap logoBmp, String content,
                                      int QR_WIDTH, int QR_HEIGHT) throws WriterException {
        try {
// 图像数据转换，使用了矩阵转换
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错率
            hints.put(EncodeHintType.MARGIN, 2); // default is 4
            hints.put(EncodeHintType.MAX_SIZE, 600);
            hints.put(EncodeHintType.MIN_SIZE, 320);
            BitMatrix bitMatrix = new QRCodeWriter().encode(content,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
// 下面这里按照二维码的算法，逐个生成二维码的图片，//两个for循环是图片横列扫描的结果
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y))
                        if (x < QR_WIDTH / 2 && y < QR_HEIGHT / 2) {
                            pixels[y * QR_WIDTH + x] = 0xFF000000;// 蓝色 0xFF0094FF
                            Integer.toHexString(new Random().nextInt());
                        } else if (x < QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
                            pixels[y * QR_WIDTH + x] = 0xFF000000;// 黄色  0xFFFED545
                        } else if (x > QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
                            pixels[y * QR_WIDTH + x] = 0xFF000000;// 绿色  0xFF5ACF00
                        } else {
                            pixels[y * QR_WIDTH + x] = 0xFF000000;// 黑色  0xFF000000
                        }
                    else
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                }
            }
// ------------------添加图片部分------------------//
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
// 设置像素点
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
// 获取图片宽高
            int logoWidth = logoBmp.getWidth();
            int logoHeight = logoBmp.getHeight();
            if (QR_WIDTH == 0 || QR_HEIGHT == 0) {
                return null;
            }
            if (logoWidth == 0 || logoHeight == 0) {
//                Log.i("xxx", "createQRCode==============================================: "+logoWidth);
                return bitmap;
            }
// 图片绘制在二维码中央，合成二维码图片
// logo大小为二维码整体大小的1/5
            float scaleFactor = QR_WIDTH * 1.0f / 5 / logoWidth;
            try {
                Canvas canvas = new Canvas(bitmap);//绘制一个bitmap
                canvas.drawBitmap(bitmap, 0, 0, null);
                canvas.scale(scaleFactor, scaleFactor, QR_WIDTH / 2,
                        QR_HEIGHT / 2);
                canvas.drawBitmap(logoBmp, (QR_WIDTH - logoWidth) / 2,
                        (QR_HEIGHT - logoHeight) / 2, null);
                canvas.save(Canvas.ALL_SAVE_FLAG);
                canvas.restore();
                return bitmap;
            } catch (Exception e) {
                bitmap = null;
                e.getStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}
