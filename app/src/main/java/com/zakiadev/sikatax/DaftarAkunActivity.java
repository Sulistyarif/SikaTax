package com.zakiadev.sikatax;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zakiadev.sikatax.data.DataAkun;
import com.zakiadev.sikatax.db.DBAdapterMix;
import com.zakiadev.sikatax.tablehelper.TableHelperDataAkun;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Sulistyarif on 04/02/2018.
 */

public class DaftarAkunActivity extends AppCompatActivity {

    TableView<String[]> tableView;
    FloatingActionButton fab;
    TableHelperDataAkun tableHelperDataAkun;
    EditText etKodeAkun, etNamaAkun;
    TextView tvJudul;
    Button btAddData;
    int jenisAkun;
    String judulAkun;
    float mLastMotionY;
    float mLastMotionX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jurnal_kecil_activity_try_fixed);

        jenisAkun = getIntent().getIntExtra("jenisAkun", 99);
        judulAkun = getIntent().getStringExtra("judulAkun");

        fab = (FloatingActionButton)findViewById(R.id.btnAddJurnal);


        tvJudul = (TextView)findViewById(R.id.tvJudul);
        tvJudul.setText(judulAkun);

        tableHelperDataAkun = new TableHelperDataAkun(this);
        tableView = (TableView<String[]>)findViewById(R.id.tvKecJurnal);
//        tableView.setColumnCount(4);
        tableView.setHeaderBackgroundColor(Color.parseColor("#c199d1"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableHelperDataAkun.getColHeader()));
        if (jenisAkun == 11){
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, tableHelperDataAkun.getDataAll()));
            fab.setVisibility(View.INVISIBLE);
        }else {
            tableView.setDataAdapter(new SimpleTableDataAdapter(this, tableHelperDataAkun.getDataAkun(jenisAkun)));
            fab.setVisibility(View.VISIBLE);
        }

//        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(DaftarAkunActivity.this, 2, 200);
//        columnModel.setColumnWidth(0, 105);
//        columnModel.setColumnWidth(1, 300);
//        tableView.setColumnModel(columnModel);

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(2);
        columnWeightModel.setColumnWeight(0,2);
        columnWeightModel.setColumnWeight(1,5);
        tableView.setColumnModel(columnWeightModel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DaftarAkunActivity.this);
                dialog.setTitle("Tambah Data Akun");
                dialog.setContentView(R.layout.card_tambah_data);

                etKodeAkun = (EditText)dialog.findViewById(R.id.etKodeAkun);
                etNamaAkun = (EditText)dialog.findViewById(R.id.etNamaAkun);

                btAddData = (Button)dialog.findViewById(R.id.btAddAkun);

                btAddData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String kodeBaru = etKodeAkun.getText().toString();
                        String namaBaru = etNamaAkun.getText().toString();

                        DataAkun dataAkun = new DataAkun();
                        dataAkun.setKodeAkun(kodeBaru);
                        dataAkun.setNamaAkun(namaBaru);

                        new DBAdapterMix(DaftarAkunActivity.this).insertAkun(dataAkun,jenisAkun);

                        tableView.setDataAdapter(new SimpleTableDataAdapter(DaftarAkunActivity.this, tableHelperDataAkun.getDataAkun(jenisAkun)));

                        etKodeAkun.setText("");
                        etNamaAkun.setText("");
                        dialog.dismiss();

                    }
                });
                dialog.show();


            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                tableView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLastMotionX != 0 && mLastMotionY != 0 && Math.abs(mLastMotionX - ev.getRawX()) > Math.abs(mLastMotionY - ev.getRawY())) {
                    tableView.requestDisallowInterceptTouchEvent(false);
                } else {
                    tableView.requestDisallowInterceptTouchEvent(true);
                }
                mLastMotionX = ev.getRawX();
                mLastMotionY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                tableView.requestDisallowInterceptTouchEvent(true);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
