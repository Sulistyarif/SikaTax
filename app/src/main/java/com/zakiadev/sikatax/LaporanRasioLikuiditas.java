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

import com.zakiadev.sikatax.data.DataSaldo;
import com.zakiadev.sikatax.db.DBAdapterMix;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LaporanRasioLikuiditas extends AppCompatActivity {

    Button btDetailAnalisis, btTutupDialog;
    TextView tvNilaiCr, tvNilaiQr;
    TextView tvdaGpm, tvdaIsiGpm, tvdaNpm, tvdaIsiNpm, tvdaRoa, tvdaIsiRoa, tvdaRoe, tvdaIsiRoe;
    Spinner spBulan,spTahun;
    private String[] listBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] listTahun = new String[50];
    int bulanDipilih, tahunDipilih;
    String strBulan, strTahun;
    double nilaiCr, nilaiQr;
    DecimalFormat df;
    int nilaiPersekot, asetLancar, utangLancar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_rasio_likuiditas_activity);

        df = new DecimalFormat("#0.00");

        settingDateSpinner();
        tampilkanRasioLikuiditas();

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

//        penormalan bulan dan tahun
        bulanDipilih = bulanDipilih+1;
        tahunDipilih = tahunDipilih+1990;

//        ketika spinner bulan diganti
        spBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bulanDipilih = position+1;
                strBulan = parent.getItemAtPosition(position).toString();
                Log.i("Bulan yang dipilih : ", String.valueOf(position));
                tampilkanRasioLikuiditas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ketika spinner tahun diganti
        spTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tahunDipilih = position+1990;
                strTahun = parent.getItemAtPosition(position).toString();
                Log.i("Tahun yang dipilih : ", String.valueOf(position));
                tampilkanRasioLikuiditas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tampilkanRasioLikuiditas() {

        tvNilaiCr = findViewById(R.id.tvNilaiCr);
        tvNilaiQr = findViewById(R.id.tvNilaiQr);
        btDetailAnalisis = findViewById(R.id.btDetailLikuiditas);

        getLaporanNeraca();

        nilaiCr = getNilaiCr();
        nilaiQr = getNilaiQr();

        tvNilaiCr.setText(String.valueOf(df.format(nilaiCr)));
        tvNilaiQr.setText(String.valueOf(df.format(nilaiQr)));

        btDetailAnalisis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanDetailAnalisis();
            }
        });

    }

    private void getLaporanNeraca() {

        asetLancar = 0;
//                pengambilan data untuk aktiva lancar (aset lancar)
        ArrayList<DataSaldo> dataSaldos = new DBAdapterMix(LaporanRasioLikuiditas.this).selectRiwayatJenisBlnThnMar(0, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo;

        for (int i = 0; i< dataSaldos.size(); i++){
            dataSaldo = dataSaldos.get(i);

            asetLancar += dataSaldo.getNominal();

        }

//                pengambilan data untuk hutang lancar
        ArrayList<DataSaldo> dataSaldos2 = new DBAdapterMix(LaporanRasioLikuiditas.this).selectRiwayatJenisBlnThnMar(2, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo2;

        utangLancar = 0;

        for (int i = 0; i< dataSaldos2.size(); i++){
            dataSaldo2 = dataSaldos2.get(i);

            utangLancar += dataSaldo2.getNominal();

        }

//        pengambilan data persekot biaya
        nilaiPersekot = new DBAdapterMix(LaporanRasioLikuiditas.this).selectNilaiPersekot(bulanDipilih, tahunDipilih);

    }

    private void tampilkanDetailAnalisis() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_dialog_detail_analisis_profitabilitas);

        settingDialog(dialog);

        dialog.show();

        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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
        tvdaRoa = dialog.findViewById(R.id.tvdaRoa);
        tvdaRoe = dialog.findViewById(R.id.tvdaRoe);

        tvdaIsiGpm = dialog.findViewById(R.id.tvdaIsiGpm);
        tvdaIsiNpm = dialog.findViewById(R.id.tvdaIsiNpm);
        tvdaIsiRoa = dialog.findViewById(R.id.tvdaIsiRoa);
        tvdaIsiRoe = dialog.findViewById(R.id.tvdaIsiRoe);

        tvdaRoa.setVisibility(View.GONE);
        tvdaRoe.setVisibility(View.GONE);
        tvdaIsiRoa.setVisibility(View.GONE);
        tvdaIsiRoe.setVisibility(View.GONE);

        tvdaGpm.setText("Current Ratio = " + df.format(nilaiCr));
        tvdaNpm.setText("Quick Ratio = " + df.format(nilaiQr));

        tvdaIsiGpm.setText("Angka " + df.format(nilaiCr) + " berarti bahwa setiap Rp1 utang lancar dapat dijamin oleh Rp" + df.format(nilaiCr) + " aset lancar");
        tvdaIsiNpm.setText("Angka " + df.format(nilaiQr) + "  berarti bahwa setiap Rp1 utang lancar dapat dijamin oleh Rp" + df.format(nilaiQr) + " aset lancar yang telah dikurangi persediaan dan persekot biaya");

    }

    private double getNilaiQr() {

        nilaiQr = 0;
        nilaiQr = ((double) asetLancar - (double) nilaiPersekot ) / (double) utangLancar;
        Log.i("nilaiLikuiditas", "Nilai aset lancar: " + asetLancar + "; nilai persekot: " + nilaiPersekot + "; nilai utang lancar:" + utangLancar);

        return nilaiQr;
    }

    private double getNilaiCr() {

        nilaiCr = 0;
        nilaiCr = (double) asetLancar / (double) utangLancar;

        return nilaiCr;
    }

}