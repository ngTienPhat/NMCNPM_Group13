package group13.ntphat.evernote.Model;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import group13.ntphat.evernote.R;
import group13.ntphat.evernote.ui.share.ShareFragment;


public class LocationUpdateListener implements LocationListener{
    // ------------------------------------------------
    // ATTRIBUTES
    private Context mContext;
    private LocationManager locationManager;
    private Double longitude, latitude;
    private String address;

    // ------------------------------------------------
    // METHODS
    @SuppressLint("MissingPermission")
    public LocationUpdateListener(Context context){
        mContext = context;
        locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000,
                100,
                this);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

//        Toast.makeText(mContext, "Location changed: Loc: " + longitude.toString() +
//                " Long: " + latitude.toString(), Toast.LENGTH_SHORT).show();
        // get address from coordinates above
        //Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        //update lat, long in shareFragment
        //ShareFragment.updateLocation(latitude, longitude);

        USER.getInstance().setCurrentLat(latitude);
        USER.getInstance().setCurrentLong(longitude);
        USER.getInstance().updateNoteByGPS(mContext, longitude, latitude);


        if ( USER.getInstance().getNewNoteArrive())
        {
            this.showNotiOnStatusBar();
        }
    }

    private String getAddress(){
        return address;
    }

    private Double getLongitude(){
        return longitude;
    }

    private Double getLatitude(){
        return latitude;
    }

    private void showNotiOnStatusBar(){
        int notifyId = 1;
        String channel_id = "EZNOTE_channel_01";
        CharSequence channel_name = "EZNOTE_channel";
        String channel_description = "This is channel for notifying new note arrival";

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(channel_id, channel_name, importance);
            mChannel.setDescription(channel_description);
            mChannel.enableLights(true);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(mContext, ShareFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channel_id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("New note arrive")
                .setContentText("There are some notes shared around from your position")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(notifyId, builder.build());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}