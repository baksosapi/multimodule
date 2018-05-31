package org.nusabit.appsatu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.nusabit.multimodule.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  String bar()
    {
        return "bar called";
    }
}
