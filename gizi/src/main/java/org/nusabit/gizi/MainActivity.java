package org.nusabit.gizi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.nusabit.vaksin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  String foo()
    {
        return "foo called";
    }
    public  String bar()
    {
        return "bar called";
    }
}
