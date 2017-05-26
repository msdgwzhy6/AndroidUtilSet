package com.smart.androidutils.activity.canvas.constant;

import com.smart.androidutils.R;
import com.util.core.InitUtil;

/**
 * @author xander on  2017/5/25.
 * @function
 */

public class ConsData {
    public static final String[] ITEMS_CANVAS = new String[]{
            InitUtil.getContext().getString(R.string.canvas_circle),
            InitUtil.getContext().getString(R.string.canvas_line),
            InitUtil.getContext().getString(R.string.canvas_rect),
            InitUtil.getContext().getString(R.string.canvas_bitmap),
            InitUtil.getContext().getString(R.string.canvas_text),
            InitUtil.getContext().getString(R.string.canvas_round_rect)
    };
    public static final int TAG_COLOR = 0x00;
    public static final int TAG_LINE = 0x01;
    public static final int TAG_CIRCLE = 0x02;
    public static final int TAG_RECT = 0x03;
    public static final int TAG_ROUND_RECT = 0x04;
    public static final int TAG_BITMAP = 0x05;
    public static final int TAG_TEXT = 0x06;
}
