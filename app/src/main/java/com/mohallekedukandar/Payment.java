package com.mohallekedukandar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Payment extends AppCompatActivity {

    ImageButton iv1,iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        iv1=findViewById(R.id.imageButton2);
        iv2=findViewById(R.id.imageButton24);

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Toast.makeText(Payment.this,"Order Placed Successful",Toast.LENGTH_SHORT).show();
                Payment.this.finish();

            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Payment.this,CardPayment.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            setResult(RESULT_OK);
            Toast.makeText(Payment.this,"Order Placed Successful",Toast.LENGTH_SHORT).show();
            Payment.this.finish();;
        }
    }
}
