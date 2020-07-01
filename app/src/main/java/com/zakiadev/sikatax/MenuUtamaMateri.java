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

/**
 * Created by sulistyarif on 08/03/18.
 */

public class MenuUtamaMateri extends AppCompatActivity {

    ListView lvMenuMateri;
    String[] menu = {"Pengenalan Akutansi", "Elemen dan Akun-Akun dalam Laporan Keuangan"
            , "Kode Akun", "Pencatatan Transaksi Kedalam Jurnal", "Penyesuaian", "Macam-Macam dan Manfaat Laporan Keuangan"
            , "Jurnal Penutup dan Pembalik" };
    TextView tvJudul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pengaturan_activity);

        tvJudul = (TextView)findViewById(R.id.tvTitlePengaturan);
        tvJudul.setText("MATERI");
        tvJudul.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.simple_small_listview, R.id.label, menu);

        lvMenuMateri = (ListView)findViewById(R.id.lvPengaturan);
        lvMenuMateri.setAdapter(arrayAdapter);

        lvMenuMateri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MenuUtamaMateri.this, MateriWebView1.class);
                intent.putExtra("pilihanMenu",i);
                startActivity(intent);
            }
        });
    }
}
