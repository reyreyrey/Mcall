package tgio.benchmark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.WindowManager;


import tgio.rncryptor.RNCryptorNative;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RNCryptorNative rncryptor = new RNCryptorNative();
        String s = new String(rncryptor.encrypt("14940", "i6vn%aLPZYr9JStu"));
Log.e("=====>", s);
//
    }



}
