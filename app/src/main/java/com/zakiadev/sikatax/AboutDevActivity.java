package com.zakiadev.sikatax;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by sulistyarif on 20/04/18.
 */

public class AboutDevActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton ibFb, ibInsta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_developer);

        ibFb = (ImageButton)findViewById(R.id.ibFb);
        ibInsta = (ImageButton)findViewById(R.id.ibInsta);

        ibFb.setOnClickListener(this);
        ibInsta.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibFb:{
                Intent intent = openFacebook(AboutDevActivity.this);
                startActivity(intent);
                break;
            }
            case R.id.ibInsta:{
                Intent intent = openInsta(AboutDevActivity.this);
                startActivity(intent);
                break;
            }
        }
    }

    private Intent openInsta(Context context) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/weslelakon/"));
    }

    private Intent openFacebook(Context context) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/gendox.heso/"));
    }
}
