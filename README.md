# AndroidUtilSDK
## 各种测试demo，持续开发中....
![](https://github.com/Xander1024/AndroidUtilSet/blob/master/gif/3.gif)

## 初始化

### 方式一：首先在 Application 或者 MainActicity 中初始化SDK###

    public class YourApp extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            InitSDK.init(this);
        }
    }
### 方式二：继承 SDK 内部的 AppHelper

    public class YourApp extends AppHelper {
        @Override
        public void onCreate() {
            super.onCreate();
            InitSDK.init(this);
            //下面开始你的表演
        }
    }
### 以上两种初始化方式并没实质性区别

## 开始使用

### 一、通用 viewholder的 CommonAdapter 使用
##### 1、准备数据：编写 JavaBean（其他形式的json数据使用，请下载demo查看），必须实现 Serializable 接口即可，以获取手机内app安装信息为例。

    /**
     * @author xander on  16/3/3.
     * @function
     */
    public class MyAppInfoBean implements java.io.Serializable {
        private Drawable AppIcon;//App图标
        private String appName;//App名字

        public MyAppInfoBean(Drawable image, String appName) {
            this.AppIcon = image;
            this.appName = appName;
        }
        public MyAppInfoBean() {

        }

        public Drawable getAppIcon() {
            return AppIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.AppIcon = appIcon;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }
#### 2、自定义viewholder，这一步跟你用传统的方式是一样的，里面封装了 item 控件的引用；但是，要实现 BaseViewHolder 接口。
    /**
     * @author xander on  2017/5/25.
     * @function
     */

    public class AppViewHolder implements BaseItemViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }


