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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import health.care.com.healthcare.Adapter.CustomPlacesAdapter;
import health.care.com.healthcare.MainActivity;
import health.care.com.healthcare.NearbyLocations.GeometryController;
import health.care.com.healthcare.R;

public class ListHealthCenters extends AppCompatActivity {

    /** initializing string buffer to append large string data */
    public static java.lang.StringBuffer stringBuffer = new StringBuffer();
    /** centersListView variable to fill list of nearby locations */
    ListView centersListView;
    /** latitude longitude variable*/
    double latitude, longitude;
    /** scan button to scan nearby location, and view button is used to show maps */
    Button scanButton, viewMapButton;
    /** location manager variable  */
    LocationManager locationManager;
    /** location */
    Location location;

    /**
     * onCreate override method calls first when activity start
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_centers_list);

        /** initializing centers list view to show results of nearby hospitals on list */
        centersListView = (ListView) findViewById(R.id.centersListView);

        /** setting centersListView setOnItemClickListener to show details about location
         * @param setOnClickListener
         * */
        centersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Selected=> ", i + "");
                /** calling list selection method to show selected item details  */
                listSelection(i);
            }
        });

        /** initializing viewMapButton */
        viewMapButton = (Button) findViewById(R.id.viewMapButton);
        /** settingUp viewMapButton setOnClickListener to open maps
         * @param setOnClickListener
         * */
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** calling view map button method */
                viewMapButton();
            }
        });

        /** initializing */
        scanButton = (Button) findViewById(R.id.scanButton);

        /**
         * setting up setOnClickListener method
         * @param setOnClickListener
         * */
        scanButton.setOnClickListener(new View.OnClickListener() {
            /*** override onCLick method
             * @param view
             * */
            @Override
            public void onClick(View view) {
                try {
                    /** calling updateLoc method */
                    updateLoc();
                    /** set GeometryController loading true to show loading */
                    GeometryController.loading = true;
                    /** calling loadLocation method to get current location */
                    loadLocation();
                    /** while loading show message to developer or console loading */
                    while (GeometryController.loading) {
                        Log.d("Message=>>>>", "Waiting");
                    }
                    /** fillList method to fill list from location results */
                    fillList();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(ListHealthCenters.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * runOnUiThread
         * @param Runnable
         * */
        this.runOnUiThread(new Runnable() {
            /**
             * override method
             * */
            @Override
            public void run() {
                try {
                    /** update current location */
                    updateLoc();
                    /** setting up loading true */
                    GeometryController.loading = true;
                    /** load nearby locations */
                    loadLocation();
                    /** loading */
                    while (GeometryController.loading) {
                        Log.d("Message=>>>>", "Waiting");
                    }
                    /** calling fillList method */
                    fillList();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(ListHealthCenters.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    MainActivity.progressDialog.cancel();
                    e.printStackTrace();
                    finish();
                }
            }
        });
    }

    /**
     * listSelection method
     * @param i
     * */
    void listSelection(int i) {
        /** initializing Alert dialog */
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle(GeometryController.detailArrayList.get(i).getHospitalName());
        dialog.setMessage(GeometryController.detailArrayList.get(i).getAddress());
        dialog.setIcon(R.drawable.marker);
        dialog.show();
    }

    /**
     * viewMapButton method
     * calls when viewMap button clicked
     * */
    void viewMapButton() {
        /** initializing intent to start another activity
         * @param ListHealthCenters
         * @param MapsActivity
         * */
        Intent intent = new Intent(ListHealthCenters.this, MapsActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    /**
     * updateLoc method calls
     * when user wants to update its current location
     * */
    public void updateLoc() {

        /** initializing location manager to get last Known location from location services */
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            throw new IllegalArgumentException("No GPS");
        } else if (!isGooglePlayServicesAvailable(this)) {
            throw new IllegalArgumentException("No Google Play Services Available");
        } else getLocation();

    }

    /**
     * isGooglePlayServicesAvailable
     * @param context
     * @return true if services available and
     * @return false if not
     * */
    public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    /** method calls
     *  to get current location of user
     * */
    void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("Permission", "Not Granted mpoving back");
            return;
        }

        /** initializing location variable to for last known location from network */
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            Log.d("Achieved latitude=>", location.getLatitude() + ", longitide=> " + location.getLongitude());
        }

        if (location == null) {
            Log.d("GPS PRovider", "Enabled");
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location == null) throw new IllegalArgumentException("Cann't trace location");

        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    /**
     * fillList method to fill nearby location list
     * on listView
     * */
    protected void fillList() {

        ArrayList<String> placeName = new ArrayList();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
            placeName.add(GeometryController.detailArrayList.get(i).getHospitalName());
        }

        ArrayList<String> ratingText = new ArrayList();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
            ratingText.add(GeometryController.detailArrayList.get(i).getRating());
        }

        ArrayList<String> openNow = new ArrayList<>();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
            openNow.add(GeometryController.detailArrayList.get(i).getOpeningHours());
        }

        CustomPlacesAdapter customPlacesAdapter = new CustomPlacesAdapter(this, placeName, ratingText, openNow);
        centersListView.setAdapter(customPlacesAdapter);
        MainActivity.progressDialog.cancel();
    }

    /**
     * loadLocation method call to manipulate data
     * from Json java object
     * */
    void loadLocation() {
        try {
            new RetrieveFeedTask().execute();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (stringBuffer.length() == 0) Log.d("Messege", "buffer reading");
                    GeometryController.manipulateData(ListHealthCenters.stringBuffer);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * RetrieveFeedTask
     * @extends AsyncTask
     * */
    class RetrieveFeedTask extends AsyncTask<StringBuffer, StringBuffer, StringBuffer > {
        /**
         * override doInBackground
         * @param stringBuffers
         * @return stringBuffer
         * */
        @Override
        protected StringBuffer doInBackground(StringBuffer... stringBuffers) {
            try {
                /** initializing StringBuilder  */
                StringBuilder stringBuilder = new StringBuilder()
                        .append("https://maps.googleapis.com/maps/api/place/search/json?rankby=distance&keyword=hospital&location=")
                        .append(latitude)
                        .append(",")
                        .append(longitude)
                        .append("&key=AIzaSyC6-gwhsbRMAbtSNhR56y2EBV9S16bZhHE&sensor=false&libraries=places");

                /** searching for url */
                URL url = new URL(stringBuilder.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();

                String n = "";
                while((n=bufferedReader.readLine())!=null){
                    buffer.append(n);
                }

                Log.d("loaded with size of  => ", "Size is " + buffer.length());

                ListHealthCenters.stringBuffer = buffer;
                return buffer;

            } catch (Exception e) {
                return null;
            }
        }
    }
}
