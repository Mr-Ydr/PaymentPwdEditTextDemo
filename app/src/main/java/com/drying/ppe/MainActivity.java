package com.drying.ppe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private PaymentPwdEditText ppe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ppe = findViewById(R.id.ppe);
        ppe.setInputEndClick(new PaymentPwdEditText.InputEndClick() {
            @Override
            public void onInputEndClick(String text) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
