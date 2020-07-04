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

public class SubMenuPajakUMKM extends AppCompatActivity {

    ListView lvMenuPajak;
    String[] menu = {"Pajak 1%", "Pajak 0.5%"};
    TextView tvJudul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengaturan_activity);

        tvJudul = (TextView)findViewById(R.id.tvTitlePengaturan);
        tvJudul.setText("Pajak UMKM");
        tvJudul.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.simple_small_listview, R.id.label, menu);

        lvMenuPajak = (ListView)findViewById(R.id.lvPengaturan);
        lvMenuPajak.setAdapter(arrayAdapter);

        lvMenuPajak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(SubMenuPajakUMKM.this, LaporanPajakUMKM.class);
                    intent.putExtra("persenPajak",i);
                    startActivity(intent);

            }
        });

    }
}
