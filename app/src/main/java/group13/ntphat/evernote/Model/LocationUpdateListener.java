package group13.ntphat.evernote.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Toast.makeText(mContext, "Location changed: Loc: " + longitude.toString() +
                " Long: " + latitude.toString(), Toast.LENGTH_SHORT).show();
        // get address from coordinates above
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0){
                address = addressList.get(0).getAddressLine(0);
                ShareFragment.updateAddressTextview(address);
            }
        } catch (IOException e) {
            e.printStackTrace();
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