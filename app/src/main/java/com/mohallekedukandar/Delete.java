package com.mohallekedukandar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class Delete extends AppCompatActivity {

    ImageButton btnadd;
    TextView tvPname,tvSamp,tvPQun,tvTot;
    int tot,qnt;
    ImageView iv1;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        btnadd=findViewById(R.id.btnde);
        tvPname=findViewById(R.id.tvDname);
        tvSamp=findViewById((R.id.tDvsamp));
        tvPQun=findViewById(R.id.tvDQn);
        tvTot=findViewById(R.id.tvDTot);
        iv1=findViewById(R.id.ivD);

        final int index = getIntent().getIntExtra("index",1);

        tvPname.setText(ApplicationClass.prd1.get(index).getName());
        tvPQun.setText("Single Pack Qunatity : "+ApplicationClass.prd1.get(index).getPackage_Quantity());
        tvSamp.setText("Toatl Qunatity : "+ApplicationClass.list.get(index).getQunt());
        int total = ApplicationClass.prd1.get(index).getPrice()*ApplicationClass.list.get(index).getQunt();
        tvTot.setText("Total Amount : RS "+total);
        String uri = "@drawable/p"+ApplicationClass.prd1.get(index).getProduct_Id();
        int imageResource = getResources().getIdentifier(uri,null, "com.mohallekedukandar");

        iv1.setImageResource(imageResource);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataQueryBuilder deldat = DataQueryBuilder.create();
                deldat.setGroupBy("created");
                deldat.setWhereClause("Product_Id = "+ApplicationClass.prd1.get(index).getProduct_Id());
                showProgress(true);
                tvLoad.setText("Deleting from Cart.....");
                Backendless.Persistence.of(Qunatity.class).find(deldat, new AsyncCallback<List<Qunatity>>() {
                    @Override
                    public void handleResponse(List<Qunatity> response) {
                        Qunatity qtd=response.get(0);
                        qtd.setQunatity_P(qtd.getQunatity_P()+ApplicationClass.list.get(index).getQunt());
                        Backendless.Persistence.save(qtd, new AsyncCallback<Qunatity>() {
                            @Override
                            public void handleResponse(Qunatity response) {
                                ApplicationClass.prd1.remove(index);
                                ApplicationClass.list.remove(index);
                                Toast.makeText(Delete.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                showProgress(false);
                                Delete.this.finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Delete.this,"Didnt Delete",Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(Delete.this,"Didnt Load",Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                });

            }
        });
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
