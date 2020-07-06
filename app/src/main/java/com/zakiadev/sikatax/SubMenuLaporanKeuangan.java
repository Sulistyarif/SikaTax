package com.zakiadev.sikatax;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SubMenuLaporanKeuangan extends AppCompatActivity {

    ListView lvMenuLaporanKeuangan;
    String[] menu = {"Rasio Profitabilitas", "Rasio Likuiditas"};
    TextView tvJudul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengaturan_activity);

        tvJudul = (TextView)findViewById(R.id.tvTitlePengaturan);
        tvJudul.setText("Analisis Laporan Keuangan");
        tvJudul.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.simple_small_listview, R.id.label, menu);

        lvMenuLaporanKeuangan = (ListView)findViewById(R.id.lvPengaturan);
        lvMenuLaporanKeuangan.setAdapter(arrayAdapter);

        lvMenuLaporanKeuangan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(SubMenuLaporanKeuangan.this, MateriWebView1.class);
//                intent.putExtra("pilihanMenu",i);
//                startActivity(intent);
                if (i == 0){
                    Intent intent = new Intent(SubMenuLaporanKeuangan.this, LaporanRasioProfitabilitas.class);
                    startActivity(intent);
                }else {
//                    rasio likuiditas
                }
            }
        });

    }
}
