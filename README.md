
# Weshare WiFi SDK 集成说明文档 2.2.1(标准版)

## 一、Android Studio WiFiSDK 快速集成 (必须)

```
   compile 'com.yiba.sdk:weshareWiFiSDk:latest.release'
```

**注意事项**

- 如果您的项目中的so只需要支持某个cpu平台，请在build.gradle文件中
剔除其他的so，我们aar包中包含了所有的cpu编译平台

- 例如我的APP项目只用到了armeabi-v7a/libgif.so,那么你需要在你的项目
配置如下规则：

```java
android {
    //剔除aar包多余的依赖
    packagingOptions {
        exclude 'lib/armeabi/*'
        exclude 'lib/arm64-v8a/*'
        exclude 'lib/x86/*'
        exclude 'lib/x86_64/*'
        exclude 'lib/mips/*'
        exclude 'lib/mips64/*'
    }
    
}
```
- 如果你的是其他的平台，请参照上面做相应的剔除

## 二、添加引用 (必须)

```
compile 'com.android.support:recyclerview-v7:25.0.0'
compile 'com.android.support:support-v4:25.0.0'

```

## 三、设置 Token (必须)

``` 
 <string name="yiba_wifi_token">yourAppToken</string>

```

**注意事项：**

- yourAppToken 表示你的 App WiFi SDK 需要添加的 Token . 

- 在官网 (http://www.pegasus-mobile.com/) 申请 Token .


## 四、权限添加 (必须)

如果你的 app 的 targetSdkVersion 大于或者等于 23 ， 你需要添加定位定位权限

```

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

```

如果你的 app 的 targetSdkVersion 小于 23 ，你不需要添加任何权限。


## 五、启动 WIFI 界面 (必须)

```
Intent intent = new Intent( MainActivity.this , WiFiActivity.class) ;
startActivity( intent );

```

## 六、自定义通知 (可选)


```
package com.yiba.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.yiba.wifi.sdk.lib.manager.WiFiSDKManager;

/**
 * Created by ${zhaoyanjun} on 2017/6/1.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction() ;
        if ( action.equals(WiFiSDKManager.YIBA_NOTIFICATION_ACTION)) {
            if (intent.hasExtra(WiFiSDKManager.YIBA_NOTIFICATION_JSON_DATA)) {
                String json =
                intent.getStringExtra(WiFiSDKManager.YIBA_NOTIFICATION_JSON_DATA);

                /**
                 * json 数据格式： {"type":0,"count":7}
                 *
                 * json 数据字段说明：
                 *        type 代表WiFi 类型（ 0：Shared WiFi 、1：Open WiFi  ）
                 *        count : 代表对应 WiFi 的个数
                 *
                 */
            }
        }
    }
}

```

NotificationReceiver 需要在 AndroidManifest.xml 文件中注册


```

<receiver android:name=".receiver.NotificationReceiver">
    <intent-filter>
    <action android:name="com.yiba.action.ACTION_YIBA_WIFI_NOTIFICATION" />
    </intent-filter>
</receiver>

```


## 七、自定义UI (可选)

### 7.1、自定义 WiFi 列表页面返回箭头的点击事件。

![image](http://oquxsbjw1.bkt.clouddn.com/WiFi%20SDK%201.png)

默认情况下，点击返回的按钮，会执行 finish() 方法，销毁掉 WiFi 列表 Activity 。如果开发者想要获取返回按钮的点击事件，需要用自己定义的 Activity 继承 WiFiActivity 并且重写 WiFiActivity 的 initBackLayout() 方法。示例如下：

-  WiFiSDKActivity 类

```

package com.yiba.wifi;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yiba.wifi.ui.WiFiActivity;

/**
 * Created by ${zhaoyanjun} on 2017/6/1.
 */

public class WiFiSDKActivity extends WiFiActivity {

    /**
     * 重写 initBackLayout() 方法
     */
    @Override
    public void initBackLayout() {
        ImageView yiba_back_image = (ImageView) findViewById(R.id.yiba_back_image);
        yiba_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    Toast.makeText(WiFiSDKActivity.this, "back image is click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}



```

**注意事项**

- 7.1.1、重写的 initBackLayout() 方法不能带有   `super.initBackLayout();`

- 7.1.2、WiFiSDKActivity 需要在 AndroidManifest.xml 文件中注册

```
<activity 
    android:name=".WiFiSDKActivity">
</activity>

```

- 7.1.3、打开 WiFi 列表界面

```

Intent intent = new Intent( MainActivity.this , WiFiSDKActivity.class) ;
startActivity( intent );

```


### 7.2、 自定义 WiFi 页面 Tab 字体颜色

默认情况下，WiFi 页面 Tab 选中的颜色是白色 `( #ffffff )` , 未选中的颜色是灰色 `( #dddddd) ` ， 效果如图所示。

![](http://oquxsbjw1.bkt.clouddn.com/yiba_tab_color.png)

 
```
<color name="yiba_viewpager_tip_selected">#ffffff</color> <!-- tab 被选中字体颜色 -->
<color name="yiba_viewpager_tip_unselected">#dddddd</color> <!-- tab 未被选中字体颜色 -->

```

如果开发者需要自定义 Tab 字体颜色，你可以在 colors.xml 文件中重写这两个颜色值。


### 7.3、自定义 WiFi 列表页面背景色

默认情况下，WiFi 检测页 和 WiFi 列表页的背景色是一个渐变色。样式如下图所示：

![image](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_bg_old_600.jpg)

如果开发者改变主题色，比如改成 浅绿色 

- 在 colors.xml 定义渐变色的 开始颜色、结束颜色

```

<color name="yiba_status_bar_bg_start">#5ba938</color> <!-- 背景渐变色的开始颜色 -->
<color name="yiba_status_bar_bg_end">#58c129</color> <!-- 背景渐变色的结束颜色  -->

```

- 在 drawable 目录下新建 yiba_bg_color.xml 文件 

```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">

    <gradient
        android:gradientRadius="100%p"
        android:startColor="@color/yiba_status_bar_bg_start"
        android:endColor="@color/yiba_status_bar_bg_end"
        android:type="radial"/>

</shape>

```

效果如下：

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_bg_new_600.jpg)


### 7.4、自定义 WiFi 检测结果页 UI 样式

默认的检测结果页的样式如图所示：

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_jianche_old.jpg)

如果开发者要自定义检测结果页的样式，比如改成 **浅绿色**

- 在 colors.xml 定义颜色值


```
<color name="yiba_ui_bg_color">#58c129</color>

```

- 在 WiFi 列表页面启动前调用

```
WiFiSDKManager.s_bgColor = getResources().getColor( R.color.yiba_ui_bg_color ) ;

```

修改后效果如果所示：

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_jianche_new.jpg)

## 八、混淆说明 (必须)

```
-keep class com.yiba.**{*;}
-keep class www.yiba.com.**{*;}

```

