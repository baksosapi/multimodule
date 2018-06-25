package org.nusabit.bidan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    public  String fooBar()
    {
        return "foo bar called";
    }
}
