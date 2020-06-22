package com.sandscape.getcurrentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        int progress = getIntent().getIntExtra("brightness", 0);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = progress/255.0f;
        getWindow().setAttributes(layoutParams);


        Toast.makeText(this, "Came in Dummy Activity", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    }
