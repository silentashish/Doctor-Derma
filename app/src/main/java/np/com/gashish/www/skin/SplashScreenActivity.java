package np.com.gashish.www.skin;
/**
    * This is Created By Ashish Gautam
    * mail:ashish.gm74@gmail.com
    * This is for splash Screen or welcoming Screen of Android
    * drawable/splash_background_app is used background and splash_screen_background.xml is used in style/SplashScreenTheme
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }

}
