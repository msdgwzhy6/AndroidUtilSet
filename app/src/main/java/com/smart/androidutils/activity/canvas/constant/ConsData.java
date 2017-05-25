package com.smart.androidutils.activity.canvas.constant;

import com.smart.androidutils.R;
import com.util.InitUtil;

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
            InitUtil.getContext().getString(R.string.canvas_round_rect)
    };
}
