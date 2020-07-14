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
import com.zakiadev.sikatax.data.Menu;
import com.zakiadev.sikatax.utils.SpacingItemDecoration;
import com.zakiadev.sikatax.utils.ViewUtils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sulistyarif on 31/01/2018.
 */

public class MenuUtamaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvNamaPerusahaan;
    TextView tvAlamatPerusahaan;
    RecyclerView rvMenu;
    MenuAdapter adapterMenu;
    LinearLayout menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.menu_utama_square);
        setContentView(R.layout.menu_utama_square_sikatax);

        tvNamaPerusahaan = (TextView)findViewById(R.id.tvNamaPerusahaanSquare);
        tvAlamatPerusahaan = (TextView)findViewById(R.id.tvAlamatPerusahaanSquare);
        rvMenu = findViewById(R.id.rvMenu);

        /*menu1 = (LinearLayout)findViewById(R.id.llmenu1);
        menu2 = (LinearLayout)findViewById(R.id.llmenu2);
        menu3 = (LinearLayout)findViewById(R.id.llmenu3);
        menu4 = (LinearLayout)findViewById(R.id.llmenu4);
        menu5 = (LinearLayout)findViewById(R.id.llmenu5);
        menu6 = (LinearLayout)findViewById(R.id.llmenu6);
        menu7 = (LinearLayout)findViewById(R.id.llmenu7);
        menu8 = (LinearLayout)findViewById(R.id.llmenu8);

        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);
        menu5.setOnClickListener(this);
        menu6.setOnClickListener(this);
        menu7.setOnClickListener(this);
        menu8.setOnClickListener(this);*/

//        showInfoNeracaAwal();

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
        /*DataPerusahaan dataPerusahaan = new DBAdapterMix(MenuUtamaActivity.this).selectDataPerusahaan();
        tvNamaPerusahaan.setText(dataPerusahaan.getNamaPers());
        tvAlamatPerusahaan.setText(dataPerusahaan.getAlamat());*/
    }

    @Override
    public void onClick(View view) {
        /*switch (view.getId()){
            case R.id.llmenu1:{
                // menu petunjuk
                Intent intent = new Intent(MenuUtamaActivity.this, PetunjukActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu2:{
                // sub menu materi
                Intent intent = new Intent(MenuUtamaActivity.this, MenuUtamaMateri.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu3:{
                // jurnal
                Intent intent = new Intent(MenuUtamaActivity.this, JurnalKecActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu4:{
                // sub menu laporan
                Intent intent = new Intent(MenuUtamaActivity.this, MenuLaporanAcvtivity.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu5:{
                // sub menu laporan keuangan
                Intent intent = new Intent(MenuUtamaActivity.this, SubMenuLaporanKeuangan.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu6:{
                // sub menu pajak umkm
                Intent intent = new Intent(MenuUtamaActivity.this, SubMenuPajakUMKM.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu7:{
                // sub menu pengaturan
                Intent intent = new Intent(MenuUtamaActivity.this, MenuPengaturanActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.llmenu8:{
                // menu tentang
                Intent intent = new Intent(MenuUtamaActivity.this, AboutAppsActivity.class);
                startActivity(intent);
                break;
            }
        }*/
    }
}
