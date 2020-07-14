package com.zakiadev.sikatax;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.zakiadev.sikatax.adapter.MenuAdapter;
import com.zakiadev.sikatax.data.DataPerusahaan;
import com.zakiadev.sikatax.data.Menu;
import com.zakiadev.sikatax.db.DBAdapterMix;
import com.zakiadev.sikatax.utils.SpacingItemDecoration;
import com.zakiadev.sikatax.utils.ViewUtils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sulistyarif on 31/01/2018.
 */

public class MenuUtamaActivity extends AppCompatActivity {

    TextView tvNamaPerusahaan;
    TextView tvAlamatPerusahaan;
    RecyclerView rvMenu;
    MenuAdapter adapterMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama_square_sikatax);

        tvNamaPerusahaan = findViewById(R.id.tvNamaPerusahaanSquare);
        tvAlamatPerusahaan = findViewById(R.id.tvAlamatPerusahaanSquare);
        rvMenu = findViewById(R.id.rvMenu);

        showInfoNeracaAwal();

        adapterMenu = new MenuAdapter();
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));
        rvMenu.addItemDecoration(new SpacingItemDecoration(2, ViewUtils.dpToPx(this, 2), true));
        rvMenu.setHasFixedSize(true);
        rvMenu.setNestedScrollingEnabled(false);
        rvMenu.setAdapter(adapterMenu);

        fetchMenu();

    }

    private void fetchMenu(){
        ArrayList<Menu> listMenu = new ArrayList<>();
        listMenu.add(new Menu(R.string.menu1,R.drawable.menut1,new Intent(MenuUtamaActivity.this, PetunjukActivity.class)));
        listMenu.add(new Menu(R.string.menu2,R.drawable.menut2,new Intent(MenuUtamaActivity.this, MenuUtamaMateri.class)));
        listMenu.add(new Menu(R.string.menu3,R.drawable.menut3,new Intent(MenuUtamaActivity.this, JurnalKecActivity.class)));
        listMenu.add(new Menu(R.string.menu4,R.drawable.menut4,new Intent(MenuUtamaActivity.this, MenuLaporanAcvtivity.class)));
        listMenu.add(new Menu(R.string.menu5,R.drawable.menut5,new Intent(MenuUtamaActivity.this, SubMenuLaporanKeuangan.class)));
        listMenu.add(new Menu(R.string.menu6,R.drawable.menut6,new Intent(MenuUtamaActivity.this, SubMenuPajakUMKM.class)));
        listMenu.add(new Menu(R.string.menu7,R.drawable.menut7,new Intent(MenuUtamaActivity.this, MenuPengaturanActivity.class)));
        listMenu.add(new Menu(R.string.menu8,R.drawable.menut8,new Intent(MenuUtamaActivity.this, AboutAppsActivity.class)));

        adapterMenu.replaceAll(listMenu);
    }

    private void showInfoNeracaAwal() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MenuUtamaActivity.this);
        if (!sharedPreferences.getBoolean("infoNeracaAwal", false)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuUtamaActivity.this);
            builder.setMessage("Pengaturan Neraca Awal Terdapat di Pengaturan > Pengaturan Neraca Awal")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("infoNeracaAwal", true);
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataPerusahaan dataPerusahaan = new DBAdapterMix(MenuUtamaActivity.this).selectDataPerusahaan();
        tvNamaPerusahaan.setText(dataPerusahaan.getNamaPers());
        tvAlamatPerusahaan.setText(dataPerusahaan.getAlamat());
    }
}
