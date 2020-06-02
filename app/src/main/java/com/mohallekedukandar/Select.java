package com.mohallekedukandar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Select extends AppCompatActivity {

    SeekBar skbr;
    TextView tv;
    int max=10,min=1,current=1;

    ImageButton btnadd;
    TextView tvPname,tvPprice,tvPQun,tvTot;
    int tot,qnt;
    ImageView iv1;
    ImageButton imgbt;

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    int index;
    String id1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        btnadd=findViewById(R.id.btnadd);
        tvPname=findViewById(R.id.tvPname);
        tvPprice=findViewById((R.id.tvPprice));
        tvPQun=findViewById(R.id.tvQn);
        tvTot=findViewById(R.id.tvTot);
        iv1=findViewById(R.id.imageView3);
        imgbt=findViewById(R.id.imagback);

        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select.this.finish();
            }
        });





        qnt=1;
        skbr= findViewById(R.id.seekBar);
        tv=findViewById(R.id.tvsamp);

        index = getIntent().getIntExtra("index",1);

        tvPname.setText(ApplicationClass.listpd.get(index).getName());
        tvPQun.setText("Single Pack Qunatity : "+ApplicationClass.listpd.get(index).getPackage_Quantity());
        tvPprice.setText("Price of Single Pack : Rs "+ ApplicationClass.listpd.get(index).getPrice());

        String uri = "@drawable/p"+ApplicationClass.listpd.get(index).getProduct_Id();
        int imageResource = getResources().getIdentifier(uri,null, "com.mohallekedukandar");

        iv1.setImageResource(imageResource);

        tvTot.setText("Total : Rs "+ApplicationClass.listpd.get(index).getPrice());
        id1 = ApplicationClass.listpd.get(index).getProduct_Id();
        tot = ApplicationClass.listpd.get(index).getPrice();
        DataQueryBuilder qb = DataQueryBuilder.create();
        qb.setGroupBy("created");
        qb.setWhereClause("Product_Id = "+id1);

        showProgress(true);

        Backendless.Persistence.of(Qunatity.class).find(qb, new AsyncCallback<List<Qunatity>>() {
            @Override
            public void handleResponse(List<Qunatity> response) {

                ApplicationClass.qty = response;
                max=ApplicationClass.qty.get(0).getQunatity_P();
                if(ApplicationClass.qty.get(0).getQunatity_P()==0){
                    skbr.setVisibility(View.GONE);
                    showProgress(false);
                    current=0;
                    tv.setText("Currently Unavialble");
                    btnadd.setVisibility(View.GONE);}
                else{
                    max=(max/10)+1;

                    skbr.setVisibility(View.VISIBLE);
                    min=1;
                    current=1;
                    skbr.setMax(max-min);
                    skbr.setProgress(current-min);
                    skbr.setMin(0);
                    tv.setText(Integer.toString(current));
                    showProgress(false);
                    qnt=current;
                    btnadd.setVisibility(View.VISIBLE);}
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                Toast.makeText(Select.this, "Error: " + fault.getMessage(),
                        Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        });
        skbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current= progress+min;
                tv.setText(""+current);
                tvTot.setText("Total : Rs "+(tot*current));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartObj crt = new CartObj();
                crt.setPid(ApplicationClass.listpd.get(index).getProduct_Id());
                crt.setQunt(current);
                ApplicationClass.list.add(crt);
                ApplicationClass.prd1.add(ApplicationClass.listpd.get(index));
                showProgress(true);
                DataQueryBuilder dqb = DataQueryBuilder.create();
                dqb.setGroupBy("created");
                dqb.setWhereClause("Product_Id = "+id1);
                tvLoad.setText("Product is being added to Cart");

                Backendless.Persistence.of(Qunatity.class).find(dqb, new AsyncCallback<List<Qunatity>>() {
                    @Override
                    public void handleResponse(List<Qunatity> response) {

                        ApplicationClass.qty = response;
                        ApplicationClass.qty.get(0).setQunatity_P(ApplicationClass.qty.get(0).getQunatity_P()-current);
                        Backendless.Persistence.save(ApplicationClass.qty.get(0), new AsyncCallback<Qunatity>() {
                            @Override
                            public void handleResponse(Qunatity response) {
                                Toast.makeText(Select.this,
                                        "Product Added to Cart!", Toast.LENGTH_SHORT).show();
                                showProgress(false);
                                Select.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(Select.this, "Error: " + fault.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(Select.this, "Error: " + fault.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                });


            }
        });

        /*skbr.setMax(max);
        skbr.setMin(0);*/

        //tv.setText(ApplicationClass.qty.get(0).getProduct_Id());



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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
