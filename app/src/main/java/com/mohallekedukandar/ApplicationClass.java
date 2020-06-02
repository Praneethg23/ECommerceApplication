package com.mohallekedukandar;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.razorpay.Checkout;

import java.util.ArrayList;
import java.util.List;

public class ApplicationClass extends Application{

    public static List<Products>  prd1,listpd;

    public  static int[] imgids;
    public static List<Qunatity> qty;
    public static List<CartObj> list;


    public static final String APPLICATION_ID = "B564E73C-1EAF-5E48-FFF8-E3C97CBEF700";
    public static final String API_KEY = "F71CBFAC-93CC-423C-B590-F7C5DB31F3CB";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        imgids = new int[50];
        list = new ArrayList<CartObj>();
        prd1=new ArrayList<Products>();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

    }



}
