package com.smart.androidutils.activity.canvas.constant;

import com.smart.androidutils.R;
import com.util.core.InitSDK;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class ConsData {
    public static final String[] ITEMS_CANVAS = new String[]{
            InitSDK.getContext().getString(R.string.canvas_circle),
            InitSDK.getContext().getString(R.string.canvas_line),
            InitSDK.getContext().getString(R.string.canvas_rect),
            InitSDK.getContext().getString(R.string.canvas_bitmap),
            InitSDK.getContext().getString(R.string.canvas_text),
            InitSDK.getContext().getString(R.string.canvas_round_rect)
    };
    public static final int TAG_COLOR = 0x00;
    public static final int TAG_LINE = 0x01;
    public static final int TAG_CIRCLE = 0x02;
    public static final int TAG_RECT = 0x03;
    public static final int TAG_ROUND_RECT = 0x04;
    public static final int TAG_BITMAP = 0x05;
    public static final int TAG_TEXT = 0x06;
}
