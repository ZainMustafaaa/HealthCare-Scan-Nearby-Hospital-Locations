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

import health.care.com.healthcare.R;

/**
 * Created by zainm on 15-Jun-17.
 */

public class MedicinesController {

    /** declaring byte index variables */
    public static byte index, index1 = -1;

    /** medicinesNames 2D array */
    static int[][] medicinesNames = {
            {R.string.feverNuberol, R.string.feverAspirin, R.string.feverActironCF, R.string.feverAcetaminophen, R.string.feverPanadol},
            {R.string.headacheProthiaden, R.string.headacheDisprin, R.string.headacheParacetamol, R.string.headacheZolmitriptan, R.string.headacheNaproxen},
            {R.string.coldAndCoughLeflox, R.string.coldAndCoughCodeine, R.string.coldAndCoughArinac, R.string.coldAndCoughDextromethorphan, R.string.coldAndCoughNoscapine},
            {R.string.vomitingDronabinol, R.string.vomitingAprepitant, R.string.vomitingDexamethasone, R.string.vomitingNabilone, R.string.vomitingDroperidol},
            {R.string.constipationBuscopan, R.string.constipationLactulose, R.string.constipationGlucomannan, R.string.constipationBisacodyl, R.string.constipationLomotil},
            {R.string.bloodPressureLisinopril, R.string.bloodPressureAtenolol, R.string.bloodPressureActrapidPenfill, R.string.bloodPressureAmaryl, R.string.bloodPressureDiamicronMR},
            {R.string.dailyHealthCareGlucobay, R.string.dailyHealthCareGNCBurn60, R.string.dailyHealthCareCalciumPluswithMagnesium, R.string.dailyHealthCareBetaCarotene, R.string.dailyHealthCareADVANTEC, R.string.dailyHealthCareAscard,
            R.string.dailyHealthCareLoprin, R.string.dailyHealthCareCLENILNEBULISER, R.string.dailyHealthCareMontelukast}
    };

    /** medicine descriptions 2D array */
    static int[][] descriptions = {
            {R.string.feverNuberolDescription, R.string.feverAspirinDescription, R.string.feverActironCFDescription, R.string.feverAcetaminophenDescription, R.string.feverPanadolDescription},
            {R.string.headacheProthiadenDescription, R.string.headacheDisprinDescription, R.string.headacheParacetamolDescription, R.string.headacheZolmitriptanDescription, R.string.headacheNaproxenDescription},
            {R.string.coldAndCoughLefloxDescription, R.string.coldAndCoughCodeineDescription, R.string.coldAndCoughArinacDescription, R.string.coldAndCoughDextromethorphanDescription, R.string.coldAndCoughNoscapineDescription},
            {R.string.vomitingDronabinolDescription, R.string.vomitingAprepitantDescription, R.string.vomitingDexamethasoneDescription, R.string.vomitingNabiloneDescription, R.string.vomitingDroperidolDescription},
            {R.string.constipationBuscopanDescription, R.string.constipationLactuloseDescription, R.string.constipationGlucomannanDescription, R.string.constipationBisacodylDescription, R.string.constipationLomotilDescription},
            {R.string.bloodPressureLisinoprilDescription, R.string.bloodPressureAtenololDescription, R.string.bloodPressureActrapidPenfillDescription, R.string.bloodPressureAmarylDescription, R.string.bloodPressureDiamicronMRDescription},
            {R.string.dailyHealthCareGlucobayDescription, R.string.dailyHealthCareGNCBurn60Description, R.string.dailyHealthCareCalciumPluswithMagnesiumDescription, R.string.dailyHealthCareBetaCaroteneDescription, R.string.dailyHealthCareADVANTECDescription, R.string.dailyHealthCareAscardDescription,
                    R.string.dailyHealthCareLoprinDescription, R.string.dailyHealthCareCLENILNEBULISERDescription, R.string.dailyHealthCareMontelukastDescription}
    };

    /** medicines imagesId 2D array  */
    static int[][] imagesId = {
            {R.drawable.fever_nuberol, R.drawable.fever_aspirin, R.drawable.fever_actiron, R.drawable.fever_acetaminophen, R.drawable.fever_panadol},
            {R.drawable.headache_prothiaden, R.drawable.headache_disprin, R.drawable.headache_paracetamol, R.drawable.headache_zolmitriptan, R.drawable.headache_naproxen},
            {R.drawable.cough_and_cold_leflox, R.drawable.cough_and_cold_codeine, R.drawable.cough_and_cold_arinac, R.drawable.cough_and_cold_dextromethrophan, R.drawable.cough_and_cold_noscapine},
            {R.drawable.vomiting_dronabinol, R.drawable.vomiting_aprepitant, R.drawable.vomiting_dexamethasone, R.drawable.vomiting_nabilone, R.drawable.vomiting_droperidol},
            {R.drawable.constipation_buscopan, R.drawable.constipation_lactulose, R.drawable.constipation_glucomannan, R.drawable.constipation_bisacodyl, R.drawable.constipation_lomotil},
            {R.drawable.lisinopril, R.drawable.atenolol, R.drawable.actrapid_penfill, R.drawable.amaryl_2mg, R.drawable.diamicron_mp},
            {R.drawable.glucobay, R.drawable.gnc_burn, R.drawable.calcium_plus_with_magnesium, R.drawable.beta_carotene, R.drawable.advantec, R.drawable.ascard,
                    R.drawable.loprin, R.drawable.clenil_nebuliser, R.drawable.montelukast}

    };

    /** websites 2D array */
    static int[][] websites  = {
            {R.string.feverNuberolWebsite, R.string.feverAspirinWebsite, R.string.feverActironCFWebsite, R.string.feverAcetaminophenWebsite, R.string.feverPanadolWebsite},
            {R.string.headacheProthiadenWebsite, R.string.headacheDisprinWebsite, R.string.headacheParacetamolWebsite, R.string.headacheZolmitriptanWebsite, R.string.headacheNaproxenWebsite},
            {R.string.coldAndCoughLefloxWebsite, R.string.coldAndCoughCodeineWebsite, R.string.coldAndCoughArinacWebsite, R.string.coldAndCoughDextromethorphanWebsite, R.string.coldAndCoughNoscapineWebsite},
            {R.string.vomitingDronabinolWebsite, R.string.vomitingAprepitantWebsite, R.string.vomitingDexamethasoneWebsite, R.string.vomitingNabiloneWebsite, R.string.vomitingDroperidolWebsite},
            {R.string.constipationBuscopanWebsite, R.string.constipationLactuloseWebsite, R.string.constipationGlucomannanWebsite, R.string.constipationBisacodylWebsite, R.string.constipationLomotilWebsite},
            {R.string.bloodPressureLisinoprilWebsite, R.string.bloodPressureAtenololWebsite, R.string.bloodPressureActrapidPenfillWebsite, R.string.bloodPressureAmarylWebsite, R.string.bloodPressureDiamicronMRWebsite},
            {R.string.dailyHealthCareGlucobayWebsite, R.string.dailyHealthCareGNCBurn60Website, R.string.dailyHealthCareCalciumPluswithMagnesiumWebsite, R.string.dailyHealthCareBetaCaroteneWebsite, R.string.dailyHealthCareADVANTECWebsite, R.string.dailyHealthCareAscardWebsite,
                    R.string.dailyHealthCareLoprinWebsite, R.string.dailyHealthCareCLENILNEBULISERWebsite, R.string.dailyHealthCareMontelukastWebsite}
    };

    /**
     *  getMedicine name method
     *  @return medicinesNames
     * */
    public static int[] getMedicines(){
        return medicinesNames[index];
    }

    /**
     * getDescriptions method
     * @return descriptions
     * */
    public static int[] getDescriptions(){
        return descriptions[index];
    }

    /**
     * getImagesId method
     * @return imagesId
     * */
    public static int[] getImagesId(){
        return imagesId[index];
    }

    /**
     * getWebUrl method
     * @return websites
     * */
    public static int getWebUrl(){
        return websites[index][index1];
    }
}
