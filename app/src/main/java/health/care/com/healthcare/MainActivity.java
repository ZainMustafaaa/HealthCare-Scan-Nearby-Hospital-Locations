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

package health.care.com.healthcare;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.Toast;
import health.care.com.healthcare.Medical.MedicalProblems;
import health.care.com.healthcare.NearbyLocations.GMap.ListHealthCenters;

public class MainActivity extends AppCompatActivity {

    /** progressDialog variable*/
    public static ProgressDialog progressDialog;

    /**
     * onCreate method calls when application opened
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocationPermission();

        /** initializing onClickListener method for medical button
         * @param onClickListener
         * */
        findViewById(R.id.medicalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalProblems();
            }
        });

        /** initializing onClickListener method for hospital button
         * @param onClickListener
         * */
        findViewById(R.id.hospitalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hospitalLocations();
            }
        });

    }

    /**
     * medicalProblems method to show problems list
     * */
    void medicalProblems() {
        loading("Loading...");
        Intent intent = new Intent(MainActivity.this, MedicalProblems.class);
        startActivity(intent);
    }

    /**
     * hospitalLocations method to start activity which shows nearby hospital locations
     * */
    void hospitalLocations() {
        if(isNetworkAvailable()) {
            loading("Scanning Location...");
            Intent intent = new Intent(MainActivity.this, ListHealthCenters.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    /**
     * isNetworkAvailable
     * @return true if internet connection is available and @return false if not available
     * */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * show progress dialog while loading maps or problems
     * */
    void loading(String message){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    
    private void requestLocationPermission(){
        
        // creating request dialog
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                
            }else {
                Toast.makeText(this, "Without the location permission we will be unable to show the hospital list ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
