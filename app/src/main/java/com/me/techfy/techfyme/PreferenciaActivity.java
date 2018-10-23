package com.me.techfy.techfyme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreferenciaActivity extends Activity {

    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia);


    }



    public void home(View view) {
        Intent intent = new Intent(view.getContext(), HomeActivity.class);


        startActivity(intent);

    }
}
