package com.zakiadev.sikatax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by zaki on 30/03/18.
 */

public class AboutAppsActivity extends AppCompatActivity {

    Button aboutDev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_apps_activity);

        aboutDev = (Button)findViewById(R.id.aboutDev);
        aboutDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutAppsActivity.this, AboutDevActivity.class);
                startActivity(intent);
            }
        });

    }
}
