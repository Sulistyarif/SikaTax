package com.zakiadev.sikatax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zakiadev.sikatax.data.DataAkun;
import com.zakiadev.sikatax.db.DBAdapterMix;

/**
 * Created by Sulistyarif on 07/02/2018.
 */

public class SplashScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
        if (!sharedPreferences.getBoolean("firstTime", false)) {
//            Toast.makeText(SplashScreenActivity.this, "Hanya akan muncul pertama kali install", Toast.LENGTH_LONG).show();
            Log.i("firstInstallation", "Hanya akan berjalan pada saat pertama kali install");
            masukanDataDefault();
//            nanti dipindah ke simpan data yang di simpan neraca awal
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingNeracaAwalActivity.this);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("firstTime", true);
//            editor.commit();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, SettingAwalPerusahaanActivity.class));
                    finish();
                }
            }, 2000L);

//        } else if (!sharedPreferences.getBoolean("neracaSaldo", false)){
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(SplashScreenActivity.this, SettingNeracaAwalActivity.class));
//                    finish();
//                }
//            }, 2000L);
        }else if (!sharedPreferences.getBoolean("DataPerusahaan", false)){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, SettingAwalPerusahaanActivity.class));
                    finish();
                }
            }, 1000L);

        }else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, MenuUtamaActivity.class));
                    finish();
                }
            }, 1000L);
        }

    }

    private void masukanDataDefault() {

        String[][] assetLancar = {
                {"1101", "Kas"},
                {"1102", "Kas di Bank"},
                {"1103", "Piutang Usaha"},
                {"1104", "Piutang Sewa"},
                {"1105", "Sewa Dibayar Dimuka"},
                {"1106", "Gaji Dibayar Dimuka"},
                {"1107", "Asuransi Dibayar Dimuka"},
                {"1108", "Iklan Dibayar Dimuka"},
                {"1109", "Telepon Dibayar Dimuka"},
                {"1110", "Listrik Dibayar Dimuka"},
                {"1111", "Perlengkapan"},
                {"1112", "Alat Tulis Kantor"}
        };

        String[][] assetTetap ={
                {"1203", "Peralatan"},
                {"1205", "Mesin"},
                {"1202", "Bangunan"},
                {"1204", "Kendaraan"},
                {"1201", "Tanah"}
        };

        String [][] utangJangkaPendek = {
                {"2101", "Utang Usaha"},
                {"2102", "Utang Gaji"},
                {"2103", "Utang Pajak"},
                {"2104", "Utang Bunga"},
                {"2105", "Utang Beban"},
                {"2106", "Utang Wesel"},
                {"2107", "Pendapatan Diterima Dimuka"},
                {"2108", "Pendapatan Sewa Diterima Dimuka"}
        };

        String[][] utangJangkaPanjang = {
                {"2201", "Utang Bank"},
                {"2202", "Utang Hipotek"}
        };

        String[][] modal = {
                {"3101", "Modal Pemilik"},
                {"3102", "Hibah"},
                {"3103", "Sumbangan"}
        };

        String[][] pendapatanUsaha = {
                {"4101", "Pendapatan Jasa"},
                {"4102", "Pendapatan Sewa"}
        };

        String[][] pendapatanNonOperasional = {
                {"4201","Pendapatan bunga"},
                {"4202", "Pendapatan Lain-Lain"},
                {"3104", "Ikhtisar Laba/Rugi"},
                {"3105", "Laba Penjualan Aset"},
                {"3106", "Rugi Penjualan Aset"}
        };

        String[][] biayaUsaha = {
                {"5101", "Beban Gaji"},
                {"5102", "Beban Perlengkapan"},
                {"5103", "Beban Sewa"},
                {"5104", "Beban Listrik"},
                {"5105", "Beban Air"},
                {"5106", "Beban Telepon"},
                {"5107", "Beban Iklan"},
                {"5108", "Beban Asuransi"},
                {"5109", "Beban Pemeliharaan Peralatan"},
                {"5110", "Beban Pemeliharaan Mesin"},
                {"5111", "Beban Penyusutan Bangunan"},
                {"5112", "Beban Penyusutan Peralatan"},
                {"5113", "Beban Penyusutan Kendaraan"},
                {"5114", "Beban Penyusutan Mesin"}
        };

        String[][] biayaLuarUsaha = {
                {"5201", "Beban Bunga"},
                {"5202", "Beban Administrasi Bank"},
                {"5203", "Beban Pajak"},
                {"5204", "Kerugian Piutang Tak Tertagih"}
        };

        String[][] pengembalianEkuitas = {
                {"6101", "Prive Pemilik"},
                {"6102", "Dividen"}
        };

        String[][] akunLainLain = {
                {"7101", "Akumulasi Penyusutan Peralatan"},
                {"7102", "Akumulasi Penyusutan Mesin"},
                {"7103", "Akumulasi Penyusutan Bangunan"},
                {"7104", "Akumulasi Penyusutan Kendaraan"},
                {"7105", "Akumulasi Penyusutan Kendaraan"},
                {"7106", "Cadangan Kerugian Piutang"}
        };

//        memasukkan data asset lancar
        for (int i = 0; i<assetLancar.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(assetLancar[i][0]);
            dataAkun.setNamaAkun(assetLancar[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,0);
        }

//        memasukkan data asset tetap
        for (int i = 0; i<assetTetap.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(assetTetap[i][0]);
            dataAkun.setNamaAkun(assetTetap[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,1);
        }

//        memasukkan data utang jangka pendek
        for (int i = 0; i<utangJangkaPendek.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(utangJangkaPendek[i][0]);
            dataAkun.setNamaAkun(utangJangkaPendek[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,2);
        }

//        memasukkan data utang jangka panjang
        for (int i = 0; i<utangJangkaPanjang.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(utangJangkaPanjang[i][0]);
            dataAkun.setNamaAkun(utangJangkaPanjang[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,3);
        }

//        memasukkan data modal utawa ekuitas
        for (int i = 0; i<modal.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(modal[i][0]);
            dataAkun.setNamaAkun(modal[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,4);
        }

//        memasukkan data pendapatan usaha
        for (int i = 0; i<pendapatanUsaha.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(pendapatanUsaha[i][0]);
            dataAkun.setNamaAkun(pendapatanUsaha[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,5);
        }

//        memasukkan data pendapatan luar usaha (update tgl 22-2-18)
        for (int i = 0; i<pendapatanNonOperasional.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(pendapatanNonOperasional[i][0]);
            dataAkun.setNamaAkun(pendapatanNonOperasional[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,6);
        }


//        memasukkan data biaya usaha
        for (int i = 0; i<biayaUsaha.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(biayaUsaha[i][0]);
            dataAkun.setNamaAkun(biayaUsaha[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,7);
        }

//        memasukkan data biaya luar usaha
        for (int i = 0; i<biayaLuarUsaha.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(biayaLuarUsaha[i][0]);
            dataAkun.setNamaAkun(biayaLuarUsaha[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,8);
        }

//        memasukkan data pengembalian ekuitas
        for (int i = 0; i<pengembalianEkuitas.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(pengembalianEkuitas[i][0]);
            dataAkun.setNamaAkun(pengembalianEkuitas[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,9);
        }

//        memasukkan data akun lain lain
        for (int i = 0; i<akunLainLain.length; i++){
            DataAkun dataAkun = new DataAkun();
            dataAkun.setKodeAkun(akunLainLain[i][0]);
            dataAkun.setNamaAkun(akunLainLain[i][1]);

            new DBAdapterMix(SplashScreenActivity.this).insertAkun(dataAkun,10);
        }

    }

}
