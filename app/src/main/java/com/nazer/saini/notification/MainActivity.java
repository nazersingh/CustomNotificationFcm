package com.nazer.saini.notification;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    /**
     * {
     "to" : "YOUR_FCM_TOKEN_WILL_BE_HERE",
     "collapse_key" : "type_a",
     "notification" : {
     "body" : "First Notification",
     "title": "Collapsing A"
     },
     "data" : {
     "body" : "First Notification",
     "title": "Collapsing A",
     "key_1" : "Data for key one",
     "key_2" : "Hellowww"
     }
     }
     */
    String msg="{body=First Notification Body," +
            "image=https://i2.wp.com/beebom.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg?resize=640%2C426, " +
            "title=Notification Title, " +
            "expandContent=Expand Content is here, notificationType=2}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fcm_main_view);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        /**
         * error
         * android.os.NetworkOnMainThreadException
         at android.os.StrictMode$AndroidBlockGuardPolicy.onNetwork(StrictMode.java:1460)
         */
/**
 * solution
 */
        /**
         * This exception is thrown when application attempts to perform a networking operation in the main thread.
         * Use below code in your onViewCreated to avoid this error
         * else Call your networking operations (getting data from web server) request in thread or Asynch class.
         */

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        
        findViewById(R.id.btn_fcm_Notification).setOnClickListener(view->
        {
            HashMap<String,String> stringStringMap=new HashMap<>();
            stringStringMap.put("body","First Notification");
            stringStringMap.put("image","https://i2.wp.com/beebom.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg?resize=640%2C426");
            stringStringMap.put("title","Notification Title");
            stringStringMap.put("expandContent","Expand Content is here");
            stringStringMap.put("notificationType","2");
            NotificationUtility.showNotification(this,stringStringMap);

        });


    }
}
