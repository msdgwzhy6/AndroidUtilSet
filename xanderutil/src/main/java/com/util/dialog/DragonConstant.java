package com.util.dialog;

import android.os.Environment;

import java.io.File;

/**
 * author xander on  2017/3/3.
 * function
 */

final class DragonConstant {
	static final String downloadCachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
			+  "Download/";

	final static int ANIM_SLIDE_UP_DOWN = 0x01;
	final static int ANIM_FADE_IN_OUT = 0x02;
	final static int ANIM_SCALE_IN_ALPHA_OUT = 0x03;
	final static int ANIM_HYPERSPACE_IN_OUT = 0x04;
	final static int ANIM_PUSH_LEFT_IN_OUT = 0x05;
	final static int ANIM_PUSH_UP_IN_OUT = 0x06;
	final static int ANIM_SLIDE_LEFT_RIGHT = 0x07;
}
