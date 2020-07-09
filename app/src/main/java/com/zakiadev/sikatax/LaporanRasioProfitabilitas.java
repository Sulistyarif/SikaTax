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
    int pendapatanOp, pendapatanNonOp, bebanOp, bebanNonOp, totalAset, ekuitas;

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

        pendapatanOp = 0;
        pendapatanNonOp = 0;
        bebanOp = 0;
        bebanNonOp = 0;
        totalAset = 0;
        ekuitas = 0;

        getLaporanLabaRugi();
        getLaporanNeraca();


//        di dalam getNilaiGpm terdapat method memasukkan nilai pendapatan dan beban
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

    private void getLaporanNeraca() {

//        pada method ini akan didapatkan nilai totalAset, dan juga nilai ekuitas

//                pengambilan data untuk aktiva lancar
        ArrayList<DataSaldo> dataSaldos = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMar(0, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo;

        int aktivaLancar = 0;

        for (int i = 0; i< dataSaldos.size(); i++){
            dataSaldo = dataSaldos.get(i);

            aktivaLancar += dataSaldo.getNominal();

        }

//                pengambilan data untuk aktiva tetap
        ArrayList<DataSaldo> dataSaldos1 = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMar(1, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo1;

        int aktivaTetap = 0;

        for (int i = 0; i< dataSaldos1.size(); i++){
            dataSaldo1 = dataSaldos1.get(i);

            aktivaTetap += dataSaldo1.getNominal();

        }

//                total aset tetap merupakan aset tetap yang dikurangi dengan penyusutan bulan ini
        ArrayList<DataSaldo> dataSaldosPeny = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMar(10, bulanDipilih, tahunDipilih);

        for (int i = 0; i< dataSaldosPeny.size(); i++){
            dataSaldo1 = dataSaldosPeny.get(i);

            aktivaTetap -= dataSaldo1.getNominal();

        }

        totalAset = aktivaLancar + aktivaTetap;

//                menghitung total laba tanggal tersebut (nggak tau ini, apakah emang laba itu dimasukkan ke dalam modal pemilik atau gimana)
        ArrayList<DataSaldo> dataSaldos4 = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectModalNeracaMar(bulanDipilih,tahunDipilih);
        DataSaldo dataSaldo4;

        int modalPemilik = 0;

        for (int i = 0; i< dataSaldos4.size(); i++){
            dataSaldo4 = dataSaldos4.get(i);

            modalPemilik += dataSaldo4.getNominal();

        }

        ekuitas = modalPemilik;

    }

    private void getLaporanLabaRugi() {

//        pada method ini akan didapatkan nilai pendapatanOp, pendapatanNonOp, bebanOp, bebanNonOp

//                pengambilan data untuk pendapatan (pendapatan operasional)
        ArrayList<DataSaldo> dataSaldos = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMarLabaRugi(5, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo;

        pendapatanOp = 0;

        for (int i = 0; i< dataSaldos.size(); i++){
            dataSaldo = dataSaldos.get(i);

            pendapatanOp += dataSaldo.getNominal();

        }

//                pengambilan data untuk pendapatan luar usaha (pendapatan non operasional)
        ArrayList<DataSaldo> dataSaldos2 = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMarLabaRugi(6, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo2;

        pendapatanNonOp = 0;

        for (int i = 0; i< dataSaldos2.size(); i++){
            dataSaldo2 = dataSaldos2.get(i);

            pendapatanNonOp += dataSaldo2.getNominal();

        }

//                pengambilan data untuk beban biaya operasional

        ArrayList<DataSaldo> dataSaldos1 = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMarLabaRugi(7, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo1;

        bebanOp = 0;

        for (int i = 0; i< dataSaldos1.size(); i++){
            dataSaldo1 = dataSaldos1.get(i);

            bebanOp += dataSaldo1.getNominal();

        }

//                pengambilan data untuk biaya luar usaha
        ArrayList<DataSaldo> dataSaldos3 = new DBAdapterMix(LaporanRasioProfitabilitas.this).selectRiwayatJenisBlnThnMarLabaRugi(8, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo3;

        bebanNonOp = 0;

        for (int i = 0; i< dataSaldos3.size(); i++){
            dataSaldo3 = dataSaldos3.get(i);

            bebanNonOp += dataSaldo3.getNominal();

        }

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

        DecimalFormat df = new DecimalFormat("#0.00");

        tvdaGpm = dialog.findViewById(R.id.tvdaGpm);
        tvdaNpm = dialog.findViewById(R.id.tvdaNpm);
        tvdaRoa = dialog.findViewById(R.id.tvdaRoa);
        tvdaRoe = dialog.findViewById(R.id.tvdaRoe);

        tvdaIsiGpm = dialog.findViewById(R.id.tvdaIsiGpm);
        tvdaIsiNpm = dialog.findViewById(R.id.tvdaIsiNpm);
        tvdaIsiRoa = dialog.findViewById(R.id.tvdaIsiRoa);
        tvdaIsiRoe = dialog.findViewById(R.id.tvdaIsiRoe);

        tvdaGpm.setText("Gross Profit Margin = " + nilaiGpm + "%");
        tvdaNpm.setText("Nett Profit Margin = " + nilaiNpm + "%");
        tvdaRoa.setText("Return of Assets = " + nilaiRoa + "%");
        tvdaRoe.setText("Return of Equity = " + nilaiRoe + "%");

        tvdaIsiGpm.setText("Angka " + nilaiGpm + "% berarti bahwa dari total penjualan bersih yang didapatkan, sebesar " + (100-nilaiGpm) + "% digunakan hanya untuk menutup Harga Pokok Penjualan sehingga yang tersisa hanya sebesar " + nilaiGpm + "% yang digunakan untuk menutup biaya operasional dan biaya lain. Jika biaya-biaya tersebut tidak melebihi " + nilaiGpm + "% maka perusahaan masih mendapatkan laba. Namun, jika biaya-biaya tersebut lebih besar dari " + nilaiGpm + "% maka perusahaan tidak mendapatkan laba sehingga perlu adanya penelusuran tentang adanya kemungkinan terdapat ketidakefisienan dalam hal biaya-biaya khususnya biaya yang berhubungan dengan Harga Pokok Penjualan.");
        tvdaIsiNpm.setText("Angka " + nilaiNpm + "% berarti bahwa dari total penjualan bersih yang didapatkan, sebesar " + (100-nilaiNpm) + "% digunakan untuk menutup semua biaya seperti Harga Pokok Penjualan, Biaya Operasional (Gaji, Sewa, Pemasaran, dll), dan termasuk pajak yang dibayarkan");
        tvdaIsiRoa.setText("Angka " + nilaiRoa + "% berarti bahwa perusahaan mampu menghasilkan laba sebesar " + nilaiRoa +"% dari total aset yang digunakan untuk menghasilkan laba tersebut.");
        tvdaIsiRoe.setText("Angka " + nilaiRoe + "% berarti bahwa perusahaan mampu memberikan imbal hasil usaha untuk setiap Rp 1 yang diinvestasikan di perusahaan, pemilik mendapatkan tambahan nilai ekuitas Rp" + (df.format(Double.valueOf(nilaiRoe)/100)) + ". Dengan kata lain, dari total investasi yang dilakukan di perusahaan, pemilik atau investor mendapatkan kenaikan nilai ekuitas sebesar " + nilaiRoe + "%. ");

    }

    private int getNilaiRoe() {

        nilaiRoe = 0;

        int lababersih = pendapatanOp + pendapatanNonOp - bebanOp - bebanNonOp;
        nilaiRoe = lababersih / ekuitas;

        return nilaiRoe;
    }

    private int getNilaiRoa() {

        nilaiRoa = 0;

        int lababersih = pendapatanOp + pendapatanNonOp - bebanOp - bebanNonOp;
        nilaiRoa = lababersih / totalAset;

        return nilaiRoa;
    }

    private int getNilaiNpm() {

        nilaiNpm = 0;

        int lababersih = pendapatanOp + pendapatanNonOp - bebanOp - bebanNonOp;
        int pajak = lababersih*1/100;
        int labaBersihSetelahPajak = lababersih - pajak;
        int penjualan = pendapatanOp + pendapatanNonOp;

        nilaiNpm = labaBersihSetelahPajak / penjualan;

        return nilaiNpm;
    }

    private int getNilaiGpm() {

        nilaiGpm = 0;

        int pendapatan = pendapatanOp + pendapatanNonOp;
        int laba = pendapatanOp + pendapatanNonOp - bebanOp - bebanNonOp;

        nilaiGpm = laba / pendapatan;

        return nilaiGpm;
    }
}