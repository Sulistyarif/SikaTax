package com.zakiadev.sikatax.data;

import android.content.Intent;

public class Menu {
    int nama,gambar;
    Intent navigate;

    public Menu(int nama, int gambar, Intent navigate) {
        this.nama = nama;
        this.gambar = gambar;
        this.navigate = navigate;
    }

    public int getNama() {
        return nama;
    }

    public int getGambar() {
        return gambar;
    }

    public Intent getNavigate() {
        return navigate;
    }
}
