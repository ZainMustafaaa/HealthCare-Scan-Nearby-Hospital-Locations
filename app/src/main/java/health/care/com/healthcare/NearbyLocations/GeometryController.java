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

package health.care.com.healthcare.NearbyLocations;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/** * Created by zainm on 28-Jun-17.
 */

public class GeometryController {

    /** boolean variable for check loading */
    public static boolean loading;
    /** initializing arrayList to to carry nearBy hospital details */
    public static ArrayList<NearbyHospitalsDetail> detailArrayList = new ArrayList();

    /**
     * manipulateData method to manipulate data through from JSON format
     * @param buffer
     * */
    public static void manipulateData(StringBuffer buffer){

        /** loading variable initializing with true  */
        loading = true;
        try {
            /** detailArrayList to clear previous data */
            detailArrayList.clear();

            /** JSON parser initializing
             * @param buffer.toString()
             * */
            JSONObject jsonpObject = new JSONObject(buffer.toString());
            /** getting results array from JSON format */
            JSONArray array = jsonpObject.getJSONArray("results");

            /** adding json array to detailedHospitalArray  */
            for(int i=0; i<array.length(); i++){
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    NearbyHospitalsDetail hospitalsDetail = new NearbyHospitalsDetail();

                    if(jsonObject.getString("name")!=null)  hospitalsDetail.setHospitalName(jsonObject.getString("name"));
                    else  hospitalsDetail.setHospitalName("Not Available");

                    try {
                        hospitalsDetail.setRating(String.valueOf(jsonObject.getDouble("rating")));
                    }catch (Exception e){
                        hospitalsDetail.setRating("Not Available");
                    }

                    try {
                        if (jsonObject.getJSONObject("opening_hours").getBoolean("open_now"))  hospitalsDetail.setOpeningHours("Opened");
                        else hospitalsDetail.setOpeningHours("closed");
                    } catch (Exception e) {
                        hospitalsDetail.setOpeningHours("Not Available");
                    }

                    hospitalsDetail.setAddress(jsonObject.getString("vicinity"));
                    hospitalsDetail.setGeometry(new double[]{jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat"),
                            jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng")});

                    detailArrayList.add(hospitalsDetail);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        /** initializing loading variable to false when list when data loaded to arrayList */
        loading = false;
        Log.d("Array Loaded with size ", "Size of "+detailArrayList.size());
    }
}
