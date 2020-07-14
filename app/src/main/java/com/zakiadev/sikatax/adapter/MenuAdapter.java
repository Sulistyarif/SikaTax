package com.zakiadev.sikatax.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zakiadev.sikatax.R;
import com.zakiadev.sikatax.data.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuHolder> {
    private List<Menu> listGetMenu = new ArrayList<>();
    public MenuAdapter() {}

    public void replaceAll(List<Menu> items) {
        listGetMenu.clear();
        listGetMenu = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu, viewGroup, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        holder.bind(listGetMenu.get(position));
    }

    @Override
    public int getItemCount() {
        return listGetMenu.size();
    }

    static class MenuHolder extends RecyclerView.ViewHolder {
        TextView menu;

        MenuHolder(View itemView) {
            super(itemView);
            menu = itemView.findViewById(R.id.tvNama);
            menu.setTextColor(Color.BLACK);
        }

        void bind(final Menu item) {
           menu.setText(item.getNama());
           menu.setCompoundDrawablesWithIntrinsicBounds(0,item.getGambar(),0,0);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   itemView.getContext().startActivity(item.getNavigate());
               }
           });
        }
    }
}

