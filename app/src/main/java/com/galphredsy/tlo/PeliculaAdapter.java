package com.galphredsy.tlo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    Interfaz interfaz;
    private List<PeliculaJson> list;
    private Context contexto;

    public PeliculaAdapter(Context context, List<PeliculaJson> list, Callback<List<PeliculaJson>> callback) {
        this.contexto = context;
        this.list = list;
    }

    public void setList(List<PeliculaJson> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;
        private TextView yearp;
        private String api;
        private String id;
        private Button button;
        CardView cardview;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.uTitle);
            image = (ImageView) v.findViewById(R.id.image);
            yearp = (TextView) v.findViewById(R.id.uAnio);
            cardview = (CardView) v.findViewById(R.id.card);
            button = (Button) v.findViewById(R.id.button3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.url_base_casa))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaz = retrofit.create(Interfaz.class);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PeliculaJson pelicula = list.get(position);
        ViewHolder Holder = (ViewHolder) holder;
        Holder.name.setText(pelicula.getTitulo());
        Holder.yearp.setText(pelicula.getAnio());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}