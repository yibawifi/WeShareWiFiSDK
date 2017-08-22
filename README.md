
# WeShare WiFi SDK Integration Instructions 2.3.1 (Standard)

[中文](README_CN.md)

## 1. Android Studio WiFiSDK quick  integration(mandatory)

```
   compile 'com.yiba.sdk:weshareWiFiSDk:2.3.1'
```

**Please Note**

- If the native “so” file in your project only supports certain cpu platfrom,
please exclude other “so” in build.gradle. Our aar contains all cpu platforms.

- Fow example, my app project only uses armeabi-v7a/libgif.so, then you should
include the rules below in your project：

```java
android {
    //exclude other so files.
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
- If your project uses other platfroms, please exclude accordingly refer to the example above

## 2. Add gradle compile  (mandatory)

```
compile 'com.android.support:recyclerview-v7:25.0.0'
compile 'com.android.support:support-v4:25.0.0'

```

## 3. Set Token (mandatory)

```
 <string name="yiba_wifi_token">yourAppToken</string>

```

**Notice：**

- yourAppToken means you must instead of your token .

- In our website to get the token (https://www.pegasus-mobile.com/) .


## 4. Add permissions (mandatory)

If your app’s gradle config “targetSdkVersion” larger than 23， you must
add below permission in your AndroidMainifest.xml file.

```

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

```

else if your app’s targetSdkVersion small than 23，you can noting to do with permission。

## 5. WiFiSDK init (mandatory)

Copy this code in your Application class, please add in the onCreate()，
that is the SDK start the service and prepare some data for SDK.

```
WiFiSDKManager.init( this );
```


## 6. Start SDK Activity (mandatory)

```
Intent intent = new Intent( MainActivity.this , WiFiActivity.class) ;
startActivity( intent );

```

## 7. Add notification in your project (Optional)


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
                 * json Data example： {"type":0,"count":7}
                 *
                 *        type means WiFi’s type（ 0：Shared WiFi 、1：Open WiFi  ）
                 *        count : means WiFi count.
                 *
                 */
            }
        }
    }
}

```

NotificationReceiver needs to be registered in AndroidManifest.xml


```

<receiver android:name=".receiver.NotificationReceiver">
    <intent-filter>
    <action android:name="com.yiba.action.ACTION_YIBA_WIFI_NOTIFICATION" />
    </intent-filter>
</receiver>

```


## 8. Customize UI (Optional)

### 8.1 Customize click event of back button in WiFi list page

![image](http://oquxsbjw1.bkt.clouddn.com/WiFi%20SDK%201.png)

When in default, click on the back button will execute finish() method and delete WiFi list Activity.
If developer would like to gain click event of back button, developer needs to extends WiFiActivity
and override the `initBackLayout()` method, the sample like below,

-  WiFiSDKActivity class

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
     * Override initBackLayout()
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

**Please note**

- Override initBackLayout() method cannot contain `super.initBackLayout();`

- 8.1.2 WiFiSDKActivity needs to be registered in AndroidManifest.xml file.

```
<activity
    android:name=".WiFiSDKActivity">
</activity>

```

-8.1.3   Start WiFi list page

```

Intent intent = new Intent( MainActivity.this , WiFiSDKActivity.class) ;
startActivity( intent );

```


### 8.2  Customize tab font color in WiFi page

In default, in WiFi page Tab’s chosen color is white `( #ffffff )`, color not chosen is gray `( #dddddd) `.
Please see the picture for the effect:

![](http://oquxsbjw1.bkt.clouddn.com/yiba_tab_color.png)


```
<color name="yiba_viewpager_tip_selected">#ffffff</color> <!-- tab selected color -->
<color name="yiba_viewpager_tip_unselected">#dddddd</color> <!-- tab unselected color -->

```

If developer needs to customize Tab font color, you may rewrite the values of the
two colors in res/values/colors.xml files.


### 8.3 Customize background color in WiFi list page

In default, the background color of WiFi analyze page and WiFi list page are the
same gradient color. Please see the picture below for the style:

![image](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_bg_old_600.jpg)

If developer changes the theme color to others, for example light green

-In res/values/colors.xml, add the gradient color’s starting and ending color

```

<color name="yiba_status_bar_bg_start">#5ba938</color> <!-- the start bg color with gradient-->
<color name="yiba_status_bar_bg_end">#58c129</color> <!-- the end bg color with  gradient -->

```

- 在 drawable 目录下新建 yiba_bg_color.xml 文件
- Create yiba_bg_color.xml file in your project in res/drawable/ folder
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

See below for the effect:

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_bg_new_600.jpg)


### 8.4 Customize UI style in WiFi analyzer result page

Default WiFi analyzer result page style is as shown below:

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_jianche_old.jpg)

If developer would like to customize WiFi analyzer result page style, for example to **light green**
- Add color value in /res/values/colors.xml

```
<color name="yiba_ui_bg_color">#58c129</color>

```

- Please add this code in your Application class


```
WiFiSDKManager.s_bgColor = getResources().getColor( R.color.yiba_ui_bg_color ) ;

```

See result after changing as follows:

![](http://oquxsbjw1.bkt.clouddn.com/yiba_wifi_jianche_new.jpg)

## 9. Proguard in your project pleas add below code (mandatory)

```
-keep class com.yiba.**{*;}
-keep class www.yiba.com.**{*;}

```
