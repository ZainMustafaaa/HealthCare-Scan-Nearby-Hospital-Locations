/*
 * %W% %E% Zain-Ul-Abedin
 *
 * Copyright (c) 2017-2018. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of ZainMustafaaa.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * for learning purposes.
 *
 */
package health.care.com.healthcare.NearbyLocations.GMap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.view.View;
import android.widget.CheckBox;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import health.care.com.healthcare.NearbyLocations.GeometryController;
import health.care.com.healthcare.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /** googleMape variable  */
    private GoogleMap mMap;
    /** variables for longitude and latitude */
    double longitude, latitude;

    /**
     * override onCreate method calls when activity start
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        /** Obtain the SupportMapFragment and get notified when the map is ready to be used. */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        /** setOnClickListener for zooming in  */
        findViewById(R.id.zoomInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        /** setOnClickListener for zooming out  */
        findViewById(R.id.zoomOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });

        /** initializing latitude and longitude */
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);

        /** refreshButton for camera point to main point */
        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
            }
        });

        /** initializing checkBox for switching satelliteView */
        final CheckBox checkBox = (CheckBox) findViewById(R.id.satelliteViewCheckBox);

        /**
         * setting Up setOnClickListener
         * @param OnClickListener
         * */
        checkBox.setOnClickListener(new View.OnClickListener() {
            /**
             * override onClick method
             * @param view
             * */
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setUpCameraAndMarkers();
            }
        });
    }


    /**
     * setUpCameraAndMarkers method
     * to mark location results on map
     * */
    void setUpCameraAndMarkers(){

        LatLng latLng;
        for(int i=0; i<GeometryController.detailArrayList.size(); i++) {
            latLng = new LatLng(GeometryController.detailArrayList.get(i).getGeometry()[0], GeometryController.detailArrayList.get(i).getGeometry()[1]);
            mMap.addMarker(new MarkerOptions().position(latLng).title(GeometryController.detailArrayList.get(i).getHospitalName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
