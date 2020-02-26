package com.galphredsy.tlo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Buscador extends AppCompatActivity {

    private PeliculaAdapter mAdapter;
    private RecyclerView recyclerViewPelicula;
    private static final int RC_SIGN_IN = 123;
    private Interfaz interfaz;
    private Button button;
    private EditText titulo;
    private String api;
    private String id;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        button = (Button) findViewById(R.id.buscar);
        titulo = (EditText) findViewById(R.id.title);
        context = this;

        Bundle bundle = getIntent().getExtras();
        api = bundle.getString("key");
        recyclerViewPelicula = findViewById(R.id.recycler);
        recyclerViewPelicula.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPelicula.setHasFixedSize(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf(R.string.url_base_casa))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaz = retrofit.create(Interfaz.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.puntuadas:
                puntuadas();
                return true;
            case R.id.guardadas:
                guardadas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void buscarPelis(View v) {
        Call<List<PeliculaJson>> call = interfaz.buscarPelicula(api, "b", titulo.getText().toString());
        call.enqueue(new Callback<List<PeliculaJson>>() {
            @Override
            public void onResponse(Call<List<PeliculaJson>> call, Response<List<PeliculaJson>> response) {
                if (response.isSuccessful()) {
                    mAdapter = new PeliculaAdapter(context, response.body(), this);
                    recyclerViewPelicula.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PeliculaJson>> call, Throwable t) {
                Log.e("No furula", t.getMessage());

            }
        });

    }

    private void puntuadas() {
        Call<List<PeliculaJson>> call = interfaz.getPeliculas(api, "l");
        call.enqueue(new Callback<List<PeliculaJson>>() {
            @Override
            public void onResponse(Call<List<PeliculaJson>> call, Response<List<PeliculaJson>> response) {
                if (response.isSuccessful()) {
                    mAdapter = new PeliculaAdapter(context, response.body(), this);
                    recyclerViewPelicula.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PeliculaJson>> call, Throwable t) {
                Log.e("No furula", t.getMessage());
            }
        });
    }

    private void guardadas() {
        Call<List<PeliculaJson>> call = interfaz.guardarPelicula(api, "g", id);
        call.enqueue(new Callback<List<PeliculaJson>>() {
            @Override
            public void onResponse(Call<List<PeliculaJson>> call, Response<List<PeliculaJson>> response) {
                if (response.isSuccessful()) {
                    mAdapter = new PeliculaAdapter(context, response.body(), this);
                    recyclerViewPelicula.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<PeliculaJson>> call, Throwable t) {
                Log.e("No furula", t.getMessage());
            }
        });
    }
}
