package com.zakiadev.sikatax;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class LaporanRasioProfitabilitas extends AppCompatActivity {

    Button btDetailAnalisis, btTutupDialog;
    TextView tvNilaiGpm, tvNilaiNpm, tvNilaiRoa, tvNilaiRoe;
    TextView tvdaGpm, tvdaIsiGpm, tvdaNpm, tvdaIsiNpm, tvdaRoa, tvdaIsiRoa, tvdaRoe, tvdaIsiRoe;
    Spinner spBulan,spTahun;
    private String[] listBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] listTahun = new String[50];
    int bulanDipilih, tahunDipilih;
    String strBulan, strTahun;
    int nilaiGpm, nilaiNpm, nilaiRoa, nilaiRoe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_rasio_profitabilitas_activity);

        settingDateSpinner();
        tampilkanRasioProfitabilitas();

    }

    private void settingDateSpinner() {

//        setting date spinner
        spBulan = (Spinner)findViewById(R.id.spNeracaSaldobulan);
        spTahun = (Spinner)findViewById(R.id.spNeracaSaldoTahun);

//        ambil waktu sekarang
        Date currentDate = Calendar.getInstance().getTime();

//        setting spinner bulan
        final ArrayAdapter<String> adapterSpinnerMonth = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listBulan);
        spBulan.setAdapter(adapterSpinnerMonth);
        spBulan.setSelection(currentDate.getMonth());
        bulanDipilih = currentDate.getMonth();

//        ketika spinner bulan diganti
        spBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulanDipilih = position+1;
                strBulan = parent.getItemAtPosition(position).toString();
                Log.i("Bulan yang dipilih : ", String.valueOf(position));
                tampilkanRasioProfitabilitas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        setting spinner tahun
        int c = 1990;
        for (int i=0; i<listTahun.length; i++){
            listTahun[i] = String.valueOf(c);
            c++;
        }

        final ArrayAdapter<String> adapterSpinnerYear = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listTahun);
        spTahun.setAdapter(adapterSpinnerYear);
        spTahun.setSelection(currentDate.getYear()-90);
        tahunDipilih = currentDate.getYear()-90;
//        ketika spinner tahun diganti
        spTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahunDipilih = position+1990;
                strTahun = parent.getItemAtPosition(position).toString();
                Log.i("Tahun yang dipilih : ", String.valueOf(position));
                tampilkanRasioProfitabilitas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tampilkanRasioProfitabilitas() {

        tvNilaiGpm = findViewById(R.id.tvNilaiGpm);
        tvNilaiNpm = findViewById(R.id.tvNilaiNpm);
        tvNilaiRoa = findViewById(R.id.tvNilaiRoa);
        tvNilaiRoe = findViewById(R.id.tvNilaiRoe);
        btDetailAnalisis = findViewById(R.id.btDetailProfitabilitas);

        nilaiGpm = getNilaiGpm();
        nilaiNpm = getNilaiNpm();
        nilaiRoa = getNilaiRoa();
        nilaiRoe = getNilaiRoe();

        tvNilaiGpm.setText(nilaiGpm + "%");
        tvNilaiNpm.setText(nilaiNpm + "%");
        tvNilaiRoa.setText(nilaiRoa + "%");
        tvNilaiRoe.setText(nilaiRoe + "%");

        btDetailAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailAnalisis();
            }
        });

    }

    private void showDetailAnalisis() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_dialog_detail_analisis_profitabilitas);

        settingDialog(dialog);

        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawableResource(R.drawable.rounded_background);
        window.setBackgroundDrawableResource(R.drawable.cell_shape);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.y = -50;
        layoutParams.x = 120;
        window.setAttributes(layoutParams);
    }

    private void settingDialog(final Dialog dialog) {
        btTutupDialog = dialog.findViewById(R.id.btTutupDetailAnalisis);
        btTutupDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvdaGpm = dialog.findViewById(R.id.tvdaGpm);
        tvdaNpm = dialog.findViewById(R.id.tvdaNpm);
        tvdaGpm = dialog.findViewById(R.id.tvdaRoa);
        tvdaGpm = dialog.findViewById(R.id.tvdaRoe);

        tvdaGpm.setText("Gross Profit Margin = " + nilaiGpm + "%");
        tvdaNpm.setText("Gross Profit Margin = " + nilaiNpm + "%");
        tvdaRoa.setText("Gross Profit Margin = " + nilaiRoa + "%");
        tvdaRoe.setText("Gross Profit Margin = " + nilaiRoe + "%");

        tvdaIsiGpm.setText("Angka " + nilaiGpm  + "% berarti bahwa dari total penjualan bersih yang didapatkan, sebesar " + (100-nilaiGpm) + "% digunakan hanya untuk menutup Harga Pokok Penjualan sehingga yang tersisa hanya sebesar " + nilaiGpm + "% yang digunakan untuk menutup biaya operasional dan biaya lain. Jika biaya-biaya tersebut tidak melebihi " + nilaiGpm + "% maka perusahaan masih mendapatkan laba. Namun, jika biaya-biaya tersebut lebih besar dari " + nilaiGpm + "% maka perusahaan tidak mendapatkan laba sehingga perlu adanya penelusuran tentang adanya kemungkinan terdapat ketidakefisienan dalam hal biaya-biaya khususnya biaya yang berhubungan dengan Harga Pokok Penjualan.");


    }

    private int getNilaiRoe() {
        return 30;
    }

    private int getNilaiRoa() {
        return 15;
    }

    private int getNilaiNpm() {
        return 22;
    }

    private int getNilaiGpm() {

//        hanya untuk tampilan dulu
        return 41;
    }
}
