package pp.myapplication;
import android.app.Application;
import com.yiba.wifi.sdk.lib.manager.WiFiSDKManager;

/**
 * Created by ${zhaoyanjun} on 2017/6/13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WiFiSDKManager.init( this );
    }
}
