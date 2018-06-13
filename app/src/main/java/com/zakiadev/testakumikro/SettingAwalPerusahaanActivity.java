package com.zakiadev.testakumikro;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zakiadev.testakumikro.data.DataPerusahaan;
import com.zakiadev.testakumikro.db.DBAdapterMix;

/**
 * Created by sulistyarif on 04/03/18.
 */

public class SettingAwalPerusahaanActivity extends AppCompatActivity {

    EditText etNamaPerusahaan, etPemilik, etAlamat, etTelp, etEmail;
    Button btSimpan;
    TextView tvEmail;
    DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_data_perusahaan_activity);

        btSimpan = (Button)findViewById(R.id.btSimpanDataPers);
        etNamaPerusahaan = (EditText)findViewById(R.id.etNamaUsaha);
        etPemilik = (EditText)findViewById(R.id.etPemilik);
        etAlamat = (EditText)findViewById(R.id.etAlamat);
        etTelp = (EditText)findViewById(R.id.etTelepon);
        etEmail = (EditText)findViewById(R.id.etEmail);

        tvEmail = (TextView)findViewById(R.id.tvEmailAwal);

        etEmail.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNamaPerusahaan.getText().toString().equals("") || etPemilik.getText().toString().equals("")){
                    Toast.makeText(SettingAwalPerusahaanActivity.this, "Nama Usaha dan Nama Pemilik Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
                }else {
                    String namaPers = etNamaPerusahaan.getText().toString();
                    String pemilik = etPemilik.getText().toString();
                    String alamat = etAlamat.getText().toString();
                    String telp = etAlamat.getText().toString();
//                    String email = etEmail.getText().toString();
                    String email = "";

                    DataPerusahaan dataPerusahaan = new DataPerusahaan();
                    dataPerusahaan.setNamaPers(namaPers);
                    dataPerusahaan.setNamaPemilik(pemilik);
                    dataPerusahaan.setAlamat(alamat);
                    dataPerusahaan.setTelp(telp);
                    dataPerusahaan.setEmail(email);

                    new DBAdapterMix(SettingAwalPerusahaanActivity.this).insertDataPerusahaan(dataPerusahaan);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingAwalPerusahaanActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("DataPerusahaan", true);
                    editor.commit();

                    Intent intent = new Intent(SettingAwalPerusahaanActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        });

    }
}
