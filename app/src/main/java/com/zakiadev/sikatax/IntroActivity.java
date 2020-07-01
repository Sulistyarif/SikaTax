package com.zakiadev.sikatax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by sulistyarif on 13/06/18.
 */

public class IntroActivity extends AppCompatActivity {

    Button btMateri, btPetunjuk, btSkip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        initButton();

        btMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, MenuUtamaMateri.class);
                startActivity(intent);
            }
        });

        btPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, PetunjukActivity.class);
                startActivity(intent);
            }
        });

        btSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, MenuUtamaActivity.class);
                startActivity(intent);
                finish();

//                Toast toast = Toast.makeText(IntroActivity.this, "Pengaturan Neraca Awal Terdapat di Pengaturan > Pengaturan Neraca Awal", Toast.LENGTH_LONG);
//                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//                if( v != null) v.setGravity(Gravity.CENTER);
//                toast.show();

            }
        });

        changeActivityBackground();

    }

    private void changeActivityBackground() {
//        View someView = findViewById(R.id.clIntro);
//        View root = someView.getRootView();
//        root.setBackgroundColor(getResources().getColor(android.R.color.white));
        Toast toast = Toast.makeText(IntroActivity.this, "Pembuat putih layar telah dihilangkan", Toast.LENGTH_LONG);
    }

    private void initButton() {
        btMateri = (Button)findViewById(R.id.btMateri);
        btPetunjuk = (Button)findViewById(R.id.btPetunjuk);
        btSkip = (Button)findViewById(R.id.btSkip);
    }
}
