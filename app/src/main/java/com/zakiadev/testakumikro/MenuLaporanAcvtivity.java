package com.zakiadev.testakumikro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zakiadev.testakumikro.LaporanJurnalUmum;

import org.w3c.dom.Text;

/**
 * Created by Sulistyarif on 31/01/2018.
 */

public class MenuLaporanAcvtivity extends AppCompatActivity {

    ListView lvMenuLaporan;
    String[] menu = {"Jurnal Umum", "Neraca Saldo", "Laba Rugi", "Perubahan Ekuitas", "Laporan Posisi Keuangan / Neraca", "Arus Kas"};
    TextView tvjudul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengaturan_activity);

        tvjudul = (TextView)findViewById(R.id.tvTitlePengaturan);
        tvjudul.setText("LAPORAN");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_listview, R.id.label, menu);

        lvMenuLaporan = (ListView)findViewById(R.id.lvPengaturan);
        lvMenuLaporan.setAdapter(arrayAdapter);

        lvMenuLaporan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:{
//                        setting pengaturan perusahaan
//                        Intent i = new Intent(MenuLaporanAcvtivity.this, JurnalActivity.class);
//                        startActivity(i);
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanJurnalUmum.class);
                        startActivity(i);
                        break;
                    }
                    case 1:{
//                        neraca saldo
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanNeracaSaldoActivity.class);
                        startActivity(i);
                        break;
                    }
                    case 2:{
//                        Laba Rugi
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanLabaRugi.class);
                        startActivity(i);
                        break;
                    }
                    case 3:{
//                        Perubahan Ekuitas
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanPerubahanEkuitas.class);
                        startActivity(i);
                        break;
                    }
                    case 4:{
//                        Neraca
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanNeracaActivity.class);
                        startActivity(i);
                        break;
                    }
                    case 5:{
//                        Arus Kas
                        Intent i = new Intent(MenuLaporanAcvtivity.this, LaporanArusKas2.class);
                        startActivity(i);
                        break;
                    }
                }
            }
        });


    }
}
