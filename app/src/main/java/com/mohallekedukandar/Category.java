package com.mohallekedukandar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

public class Category extends AppCompatActivity {

    CardView fv,bv,bu,sn,hh;
    ImageView imageview;
    TextView tv2;
    int[] res1;
    ImageButton imgbt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        res1 = new int[50];
        fv = findViewById(R.id.fv);
        bv = findViewById(R.id.bv);
        bu = findViewById(R.id.bu);
        sn = findViewById(R.id.sn);
        hh = findViewById(R.id.hh);
        imgbt=findViewById(R.id.imageButton1Cat);

        imgbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Category.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(Category.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        for(int i=0;i<50;i++)
        {

            if(i<10)
                res1[i]=getResources().getIdentifier("@drawable/p10"+i,null,getPackageName());
            else
                res1[i]=getResources().getIdentifier("@drawable/p1"+i,null,getPackageName());
        }
        ApplicationClass.imgids=res1;

        tv2=findViewById(R.id.textView2);
        imageview = findViewById(R.id.imageView2);

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,CartPage.class);
                startActivity(intent);

            }
        });

        fv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,Buying.class);
                intent.putExtra("Categ","Fruits and Vegetables");
                //startActivityForResult(intent,1);
                startActivity(intent);
            }
        });
        bv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,Buying.class);
                intent.putExtra("Categ","Beverages");
                startActivityForResult(intent,2);
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,Buying.class);
                intent.putExtra("Categ","Beauty and Hygiene");
                startActivityForResult(intent,3);
            }
        });
        sn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,Buying.class);
                intent.putExtra("Categ","Snacks");
                startActivityForResult(intent,4);
            }
        });
        hh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Category.this,Buying.class);
                intent.putExtra("Categ","Household");
                startActivityForResult(intent,5);
            }
        });



    }
}
