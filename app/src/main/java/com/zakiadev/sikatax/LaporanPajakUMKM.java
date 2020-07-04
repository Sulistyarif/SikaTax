package com.zakiadev.sikatax;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zakiadev.sikatax.data.DataSaldo;
import com.zakiadev.sikatax.db.DBAdapterMix;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class LaporanPajakUMKM extends AppCompatActivity {

    TextView tvBesarPajak, tvNilaiPajak;
    Spinner spBulan,spTahun;
    private String[] listBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] listTahun = new String[50];
    int bulanDipilih, tahunDipilih;
    String strBulan, strTahun;
    int totalPendapatan, besarPajak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_pajak_umkm);

        settingDateSpinner();

        tampilkanPajak();

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
                tampilkanPajak();
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
                tampilkanPajak();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void tampilkanPajak() {

        int persenPajak = getIntent().getIntExtra("persenPajak",99);

        tvBesarPajak = findViewById(R.id.tvBesarPajak);
        tvNilaiPajak = findViewById(R.id.tvNilaiPajak);

        totalPendapatan = hitungTotalPendapatan();

//        setting separator ribuan
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

//        mengambil nilai persen pajak

        if (persenPajak == 0){
            tvBesarPajak.setText("Pajak 1%");
            besarPajak = totalPendapatan*1/100;
            tvNilaiPajak.setText("Rp" + formatter.format(besarPajak));
        }else if(persenPajak == 1){
            tvBesarPajak.setText("Pajak 0.5%");
            besarPajak = totalPendapatan*1/200;
            tvNilaiPajak.setText("Rp" + formatter.format(besarPajak));
        }else {
            finish();
        }

    }

    private int hitungTotalPendapatan() {

        int gabunganPendapatanOpNonOp = 0;

//                pengambilan data untuk pendapatan (pendapatan operasional)
        ArrayList<DataSaldo> dataSaldos = new DBAdapterMix(LaporanPajakUMKM.this).selectRiwayatJenisBlnThnMarLabaRugi(5, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo;

        int pendapatanOp = 0;

        for (int i = 0; i< dataSaldos.size(); i++){
            dataSaldo = dataSaldos.get(i);

            String kodeAkun = dataSaldo.getKodeAkun();
            String namaAkun = dataSaldo.getNamaAkun();
            String nominal = String.valueOf(dataSaldo.getNominal());

            pendapatanOp += dataSaldo.getNominal();
        }

//                pengambilan data untuk pendapatan luar usaha (pendapatan non operasional)
        ArrayList<DataSaldo> dataSaldos2 = new DBAdapterMix(LaporanPajakUMKM.this).selectRiwayatJenisBlnThnMarLabaRugi(6, bulanDipilih, tahunDipilih);
        DataSaldo dataSaldo2;

        int pendapatanNonOp = 0;

        for (int i = 0; i< dataSaldos2.size(); i++){
            dataSaldo2 = dataSaldos2.get(i);

            String kodeAkun = dataSaldo2.getKodeAkun();
            String namaAkun = dataSaldo2.getNamaAkun();
            int nominal = (int)dataSaldo2.getNominal();

            pendapatanNonOp += dataSaldo2.getNominal();

        }

        gabunganPendapatanOpNonOp = pendapatanOp + pendapatanNonOp;

//        return ntar diganti yaa
        return gabunganPendapatanOpNonOp;
    }
}
