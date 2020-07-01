package com.zakiadev.sikatax.tablehelper;

import android.content.Context;

import com.zakiadev.sikatax.data.DataJurnal;
import com.zakiadev.sikatax.data.DataJurnalMar;
import com.zakiadev.sikatax.db.DBAdapterMix;

import java.util.ArrayList;

/**
 * Created by Sulistyarif on 02/02/2018.
 */

public class TableHelperDataJurnalKec {
    Context context;

    private String[] colHeader = {"Tanggal", "Keterangan"};
    private String[][] dataJurnal;

    public TableHelperDataJurnalKec(Context context) {
        this.context = context;
    }

    public String[] getColHeader() {
        return colHeader;
    }

    public String[][] getDataJurnal2() {

        ArrayList<DataJurnal> dataJurnals = new DBAdapterMix(context).selectJurnal();
        DataJurnal dataJurnal;

        this.dataJurnal = new String[dataJurnals.size()][6];
        for (int i = 0; i< dataJurnals.size(); i++){
            dataJurnal = dataJurnals.get(i);
            this.dataJurnal[i][0] = dataJurnal.getTgl();

            this.dataJurnal[i][1] = dataJurnal.getKeterangan();
            this.dataJurnal[i][2] = dataJurnal.getId();
            this.dataJurnal[i][3] = dataJurnal.getNamaDebet();
            this.dataJurnal[i][4] = dataJurnal.getNamaKredit();
            this.dataJurnal[i][5] = String.valueOf(dataJurnal.getNominalDebet());

        }

        return this.dataJurnal;
    }

    public String[][] getDataJurnal3(){
        ArrayList<DataJurnalMar> dataJurnalMars = new DBAdapterMix(context).selectJurnalMar();
        DataJurnalMar dataJurnalMar;

        this.dataJurnal = new String[dataJurnalMars.size()][4];
        for (int i = 0; i < dataJurnalMars.size(); i++){
            dataJurnalMar = dataJurnalMars.get(i);

            this.dataJurnal[i][0] = dataJurnalMar.getTgl();
            this.dataJurnal[i][1] = dataJurnalMar.getKet();
            this.dataJurnal[i][2] = dataJurnalMar.getPid();
            this.dataJurnal[i][3] = String.valueOf(dataJurnalMar.getKode_trans());

        }
        return this.dataJurnal;
    }

}
