package pp.myapplication;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import com.yiba.wifi.ui.WiFiActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById( R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity( new Intent( MainActivity.this , WiFiActivity.class ));
            }
        });
    }
}
