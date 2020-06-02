package com.mohallekedukandar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends AppCompatActivity  {

    private static final String TAG = CartPage.class.getSimpleName();
    ListView lvList;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad,tvs,tv12;

    CartAdapter adapter;

    ImageButton imgbt;
    List<Products> listpdr;
    int cartVal;
    Checkout checkout;
    Button btchk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        lvList=findViewById(R.id.listProd1);
        imgbt=findViewById(R.id.imageButton1);
        checkout = new Checkout();
        btchk =findViewById(R.id.btBuy);

        btchk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartVal!=0)
                    startActivityForResult(new Intent(CartPage.this,Payment.class),2);
            }
        });

        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartPage.this.finish();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        tvs=findViewById(R.id.textViewss);
        tv12=findViewById(R.id.textView4);
        cartVal=0;
        for(int i=0;i<ApplicationClass.list.size();i++)
        {
            cartVal=cartVal+(ApplicationClass.list.get(i).getQunt()*ApplicationClass.prd1.get(i).getPrice());
        }
        if(cartVal==0)
        {
            btchk.setVisibility(View.GONE);
        }
        if(cartVal!=0)
        {
            btchk.setVisibility(View.VISIBLE);
        }
        tv12.setText("Rs "+cartVal);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(CartPage.this, Delete.class);
                intent.putExtra("index", i);
                startActivityForResult(intent, 1);

            }
        });
        //tvs.setText(" "+ApplicationClass.prd1.get(0).getProduct_Id());
        adapter = new CartAdapter(CartPage.this, ApplicationClass.prd1,ApplicationClass.list);
        lvList.setAdapter(adapter);




    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode ==RESULT_OK)
        {
            adapter.notifyDataSetChanged();
            cartVal=0;
            for(int i=0;i<ApplicationClass.list.size();i++)
            {
                cartVal=cartVal+(ApplicationClass.list.get(i).getQunt()*ApplicationClass.prd1.get(i).getPrice());
            }
            tv12.setText("Rs "+cartVal);
            if(cartVal==0)
            {
                btchk.setVisibility(View.GONE);
            }
        }
        if (requestCode == 2&&resultCode == RESULT_OK)
        {
            ApplicationClass.prd1.removeAll(ApplicationClass.prd1);
            ApplicationClass.list.removeAll(ApplicationClass.list);
            adapter.notifyDataSetChanged();
            cartVal=0;
            for(int i=0;i<ApplicationClass.list.size();i++)
            {
                cartVal=cartVal+(ApplicationClass.list.get(i).getQunt()*ApplicationClass.prd1.get(i).getPrice());
            }
            tv12.setText("Rs "+cartVal);
            if(cartVal==0)
            {
                btchk.setVisibility(View.GONE);
            }
        }

    }





}
