package com.nazer.saini.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtility {


    public static void sendNotificationOnlyText(Context context,Map<String, String> notification) {
        String NOTIFICATION_CHANNEL_ID = "10001";
        NotificationCompat.Builder builder;
               NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("body"))
                    .setContentText(notification.get("title"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            builder = new NotificationCompat.Builder(context)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("title"))
                    .setContentText(notification.get("body"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0));
        }
        notificationManager.notify(0, builder.build());
    }

    public static void sendNotificationwithImage(Context context, Map<String, String> notification) {
        String NOTIFICATION_CHANNEL_ID = "10001";
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        Bitmap bitmap=getBitmapfromUrl(notification.get("image"));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("title"))
                    .setContentText(notification.get("body"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                    // setting the custom collapsed and expanded views
                    .setLargeIcon(bitmap)/*Notification icon image*/
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap).setSummaryText(notification.get("expandContent")));/*Notification with Image*/
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            builder = new NotificationCompat.Builder(context)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("title"))
                    .setContentText(notification.get("body"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                    // setting the custom collapsed and expanded views
                    .setLargeIcon(bitmap)/*Notification icon image*/
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap).setSummaryText(notification.get("expandContent")));/*Notification with Image*/

            // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
//                .setStyle(new android.support.v7.app.NotificationCompat.DecoratedCustomViewStyle());

            // retrieves android.app.NotificationManager

        }

        notificationManager.notify(0, builder.build());
    }
    /*
     *To get a Bitmap image from the URL received
     * */
    public static Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void showNotification(Context context, Map<String, String> notification) {

        if (notification.get("notificationType").equalsIgnoreCase("0"))
            sendNotificationOnlyText(context, notification);
        else if (notification.get("notificationType").equalsIgnoreCase("1"))
            sendNotificationwithImage(context, notification);
        else if (notification.get("notificationType").equalsIgnoreCase("2"))
            sendNotification(context, notification);
    }


    public static void sendNotification(Context context, Map<String, String> notification) {
        Bitmap bitmap=getBitmapfromUrl(notification.get("image"));
        String NOTIFICATION_CHANNEL_ID = "10001";
        /**
         * expand view
         */
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_expand);
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        expandedView.setTextViewText(R.id.content_title, notification.get("title"));
        expandedView.setTextViewText(R.id.content_text, notification.get("body"));
        expandedView.setTextViewText(R.id.notification_message,  notification.get("expandContent"));
        expandedView.setImageViewBitmap(R.id.notification_img, bitmap);
        expandedView.setImageViewBitmap(R.id.big_icon, bitmap);
        // adding action to left button
        Intent leftIntent = new Intent(context, NotificationIntentService.class);
        leftIntent.setAction("left");
        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(context, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        // adding action to right button
        Intent rightIntent = new Intent(context, NotificationIntentService.class);
        rightIntent.setAction("right");
        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(context, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        /**
         * Collapse view
         */
        RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.notification_custom_collapsed);
        collapsedView.setTextViewText(R.id.timestamp, "" + DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));
        collapsedView.setTextViewText(R.id.content_title, notification.get("title"));
        collapsedView.setTextViewText(R.id.content_text, notification.get("body"));
        collapsedView.setImageViewBitmap(R.id.big_icon, bitmap);
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("title"))
                    .setContentText(notification.get("body"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                    // setting the custom collapsed and expanded views
                    .setCustomContentView(collapsedView)
                    .setCustomBigContentView(expandedView);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            builder = new NotificationCompat.Builder(context)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification.get("title"))
                    .setContentText(notification.get("body"))
                    // notification will be dismissed when tapped
                    .setAutoCancel(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                    // setting the custom collapsed and expanded views
                    .setCustomContentView(collapsedView)
                    .setCustomBigContentView(expandedView);

            // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
//                .setStyle(new android.support.v7.app.NotificationCompat.DecoratedCustomViewStyle());

            // retrieves android.app.NotificationManager

        }

        notificationManager.notify(0, builder.build());
    }
}
