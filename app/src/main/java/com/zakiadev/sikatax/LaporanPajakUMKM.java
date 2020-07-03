package com.zakiadev.sikatax;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class LaporanPajakUMKM extends AppCompatActivity {

    TableView<String[]> tableView;
    String []headerTablePajak= {"Besar Pajak", "Nilai"};
    String [][]isiPajak1Persen = {
            {"Pajak 1%","Rp50.000"}
    };
    String [][]isiPajak05Persen = {
            {"Pajak 0.5%","Rp25.000"}
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporan_pajak_umkm);

        tableView = (TableView<String[]>)findViewById(R.id.tvPajak);
        tableView.setHeaderBackgroundColor(Color.parseColor("#3498db"));

        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(this, headerTablePajak);
        simpleTableHeaderAdapter.setPaddings(80,5,5,5);
        tableView.setHeaderAdapter(simpleTableHeaderAdapter);

        tableView.setDataAdapter(new SimpleTableDataAdapter(this, isiPajak1Persen));

        TableColumnWeightModel columnWeightModel = new TableColumnWeightModel(2);
        columnWeightModel.setColumnWeight(1,1);
        columnWeightModel.setColumnWeight(2,3);
        tableView.setColumnModel(columnWeightModel);

    }
}
