package com.sdk.util.logger;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * 描    述：日志的工具类
 * ================================================
 */
public class JJLogger {

    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char VERTICAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;

    private static final char I = 'I', W = 'W', D = 'D', E = 'E', V = 'V', A = 'A', M = 'M';

    private static String LINE_SEPARATOR = System.getProperty("line.separator"); //等价于"\n\r"
    private static int JSON_INDENT = 4;

    private static boolean isDebug = true;
    private static String tag = "log";

    /*isLogEnable 是否开启调试日志
    * true 开启；
    * false 关闭；
    * */
    public static void debug(boolean isEnable) {
        isDebug = isEnable;
    }
    /**
     * 打印MAp
     */
    public static void map(String tag, Map map) {
        Set set = map.entrySet();
        if (set.size() < 1) {
            printLog(D, tag, "[]");
            return;
        }

        int i = 0;
        String[] s = new String[set.size()];
        for (Object aSet : set) {
            Map.Entry entry = (Map.Entry) aSet;
            s[i] = "key = "+entry.getKey() + " , " + "value = "+entry.getValue() + ",\n";
            i++;
        }
        printLog(V,tag , s);
    }

    /**
     * 打印JSON
     *
     * @param tag
     * @param jsonStr
     */
    public static void json(String tag, String jsonStr) {
        if (isDebug) {
            String message;
            try {
                if (jsonStr.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    message = jsonObject.toString(JSON_INDENT); //这个是核心方法
                } else if (jsonStr.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    message = jsonArray.toString(JSON_INDENT);
                } else {
                    message = jsonStr;
                }
            } catch (JSONException e) {
                message = jsonStr;
            }

            message = LINE_SEPARATOR + message;
            String[] lines = message.split(LINE_SEPARATOR);
            printLog(D, tag, lines);
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) printLog(I,tag , msg);
    }
    public static void i(String msg) {
        if (isDebug) printLog(I,tag , msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)  printLog(D, tag, msg);
    }
    public static void d(String msg) {
        if (isDebug)  printLog(D, tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)  printLog(D, tag, msg);
    }
    public static void w(String msg) {
        if (isDebug)  printLog(D, tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) printLog(D, tag, msg);
    }
    public static void e(String msg) {
        if (isDebug) printLog(D, tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) printLog(D, tag, msg);
    }
    public static void v(String msg) {
        if (isDebug) printLog(D, tag, msg);
    }


    /**
     * 意打印
     * @param type 打印类型
     * @param tag 发音筛选的tag
     * @param msg 要打印的信息
     */
    private static void printer(char type, String tag, String msg) {
        switch (type) {
            case I:
                Log.i(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case V:
                Log.v(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
        }
    }

    /**
     * 打印头部信息
     * @param type 打印类型
     * @param tag 发音筛选的tag
     */
    private static void printHead(char type, String tag) {
        printer(type,tag , TOP_BORDER);
        printer(type,tag , VERTICAL_DOUBLE_LINE + "   线程信息:");
        printer(type,tag , VERTICAL_DOUBLE_LINE + "   " + Thread.currentThread().getName());
        printer(type,tag , MIDDLE_BORDER);
    }

    /**
     * 打印Log被调用的位置
     *
     * @param type 打印类型
     * @param tag 发音筛选的tag
     * @param msg 要打印的信息
     */
    private static void printLocation(char type, String tag, String... msg) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        int i = 0;
        for (StackTraceElement e : stack) {
            String name = e.getClassName();
            if (!name.equals(JJLogger.class.getName())) {
                i++;
            } else {
                break;
            }
        }
        i += 3;
        String className = stack[i].getFileName();
        String methodName = stack[i].getMethodName();
        int lineNumber = stack[i].getLineNumber();
        String location = stack[i].toString();//输出信息能够进行点击定位的关键代码
        StringBuilder sb = new StringBuilder();
        printer(type,tag , VERTICAL_DOUBLE_LINE + "   Location:");
        sb
                .append(VERTICAL_DOUBLE_LINE)
                .append("   类名：").append(className)
                .append("\n").append(VERTICAL_DOUBLE_LINE)
                .append(location)
                .append("\n").append(VERTICAL_DOUBLE_LINE).append("   方法名：").append(methodName)
                .append("\n").append(VERTICAL_DOUBLE_LINE).append("   第 ").append(lineNumber).append(" 行");
        printer(type,tag , sb.toString());
        printer(type,tag, msg == null || msg.length == 0 ? BOTTOM_BORDER : MIDDLE_BORDER);
    }

    /**
     * 打印消息
     *  @param type 打印类型
     * @param tag 发音筛选的tag
     * @param msg 要打印的信息
     */
    private static void printMsg(char type, String tag, String... msg) {
        printer(type, tag, VERTICAL_DOUBLE_LINE + "   信息内容:");
        for (String str : msg) {
            str.length();
            printer(type, tag, VERTICAL_DOUBLE_LINE + "   " + str);
        }
        printer(type, tag, BOTTOM_BORDER);
    }

    /**
     * 打印log
     *  @param type
     * @param tag
     * @param msg
     */
    private static void printLog(char type, String tag, String... msg) {
        printHead(type,tag );
        printLocation(type,tag , msg);
        if (msg == null || msg.length == 0) {
            return;
        }
        printMsg(type,tag , msg);
    }
}
