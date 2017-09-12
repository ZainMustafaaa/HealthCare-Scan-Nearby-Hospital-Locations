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

package health.care.com.healthcare.Medical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import health.care.com.healthcare.R;

public class RequiredMedicines extends AppCompatActivity {

    /** gestureDetectorCompat variable */
    private GestureDetectorCompat gestureDetectorCompat;
    /** medicineNames, descriptions, images array */
    private int[] medicineNames, descriptions, images;
    /** medicineName medicineDescription medicineIndes textView variables */
    private TextView medicineName, medicineDescription, medicineIndex;
    /** imageView variable for medicines image view */
    private ImageView medicineImage;
    /** index for array details and total medicines */
    byte index = 0, TOTAL_MEDICINES;

    /**
     * override onCreate method
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(getResources().getStringArray(R.array.medicalProblems)[MedicinesController.index]);

        /** initializing medicines descriptions and images variables */
        medicineNames = MedicinesController.getMedicines();
        descriptions = MedicinesController.getDescriptions();
        images = MedicinesController.getImagesId();

        /** totalMedicines byte variable */
        TOTAL_MEDICINES = (byte) medicineNames.length;

        /** settingUp layout content  */
        setContentView(R.layout.activity_required_medicines);
        /** initializing gestures */
        gestureDetectorCompat = new GestureDetectorCompat(this, new Gesture());

        /** settingUp setOnClickMethod for buyOnline method
         * @param OnClickListener
         * */
        findViewById(R.id.onlineBuyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyOnline(view);
            }
        });

        /** initializing medicineName medicineDescription medicineImage and medicineIndex variables */
        medicineName = (TextView) findViewById(R.id.medicineName);
        medicineDescription = (TextView) findViewById(R.id.medicineDescription);
        medicineImage = (ImageView) findViewById(R.id.medicineImage);
        medicineIndex = (TextView) findViewById(R.id.indexView);

        /** calling method fill data */
        fillData(index);

    }

    /** buyOnline method
     * @param view
     * */
    void buyOnline(View view){
        MedicinesController.index1 = index;
        /** initializing intent
         * @param RequiredMedicines
         * @param WebsiteView
         * */
        Intent intent = new Intent(RequiredMedicines.this, health.care.com.healthcare.Web.WebsiteView.class);
        startActivity(intent);
    }

    /**
     * onTouchEvent
     * @param motionEvent
     * @return condition if is possible to apply gesture
     * */
    public boolean onTouchEvent(MotionEvent motionEvent){
        this.gestureDetectorCompat.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    /**
     * fillData method
     * @param i
     * */
    void fillData(byte i){
        medicineName.setText(medicineNames[i]);
        medicineDescription.setText(descriptions[i]);
        medicineImage.setImageResource(images[i]);

        medicineIndex.setText((i+1)+"/"+TOTAL_MEDICINES);
    }

    /** gestures
     * @extends SimpleOnGestureListener
     * */
    class Gesture extends GestureDetector.SimpleOnGestureListener{

        /**
         * override method onFling
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return true
         * */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            
            if(e2.getX()<e1.getX()){
                if(index<TOTAL_MEDICINES-1){
                    index++;
                    fillData(index);
                }
            }
            else if(e2.getX()>e1.getX()) {
                if (index>0){
                    index--;
                    fillData(index);
                }
            }

            return true;
        }
    }
}
