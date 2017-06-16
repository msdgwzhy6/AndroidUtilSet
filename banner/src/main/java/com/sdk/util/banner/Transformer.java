package com.sdk.util.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.sdk.util.banner.transformer.AccordionTransformer;
import com.sdk.util.banner.transformer.BackgroundToForegroundTransformer;
import com.sdk.util.banner.transformer.CubeInTransformer;
import com.sdk.util.banner.transformer.CubeOutTransformer;
import com.sdk.util.banner.transformer.DefaultTransformer;
import com.sdk.util.banner.transformer.DepthPageTransformer;
import com.sdk.util.banner.transformer.FlipHorizontalTransformer;
import com.sdk.util.banner.transformer.FlipVerticalTransformer;
import com.sdk.util.banner.transformer.ForegroundToBackgroundTransformer;
import com.sdk.util.banner.transformer.RotateDownTransformer;
import com.sdk.util.banner.transformer.RotateUpTransformer;
import com.sdk.util.banner.transformer.ScaleInOutTransformer;
import com.sdk.util.banner.transformer.StackTransformer;
import com.sdk.util.banner.transformer.TabletTransformer;
import com.sdk.util.banner.transformer.ZoomInTransformer;
import com.sdk.util.banner.transformer.ZoomOutSlideTransformer;
import com.sdk.util.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
