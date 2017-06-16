# AndroidUtilSDK
##各种测试demo，持续开发中....
![](https://github.com/Xander1024/AndroidUtilSet/blob/master/gif/3.gif)


##初始化

###方式一：首先在 Application 或者 MainActicity 中初始化SDK

    public class YourApp extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            InitSDK.init(this);
        }
    }
###方式二：继承 SDK 内部的 AppHelper

    public class YourApp extends AppHelper {
        @Override
        public void onCreate() {
            super.onCreate();
            InitSDK.init(this);
        }
    }
###以上两种初始化方式并没实质性区别

##开始使用

### 通用 viewholder的 CommonAdapter 使用
####关于数据
#####一、编写 JavaBean（其他形式的json数据使用，请下载demo查看），必须实现 Serializable 接口即可，以获取手机内app安装信息为例。


