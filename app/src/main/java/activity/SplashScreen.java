package activity;

/**
 * Created by vishwashrisairm on 23/2/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;


import com.example.vishwashrisairm.materialdesign.R;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;
    protected Client kinveyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        kinveyClient = ((UserLogin) getApplication()).getKinveyService();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                System.out.println("Splash Screen"+kinveyClient.user().isUserLoggedIn());
                if(kinveyClient.user().isUserLoggedIn()){
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                }



                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}