package com.galphredsy.tlo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
//    private final String URL_BASE = "http://10.5.4.138:8081/ProyectoNetflix/";
    private final String URL_BASE = "http://192.168.1.39:8081/ProyectoNetflix/";
    private Interfaz interfaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        interfaz = retrofit.create(Interfaz.class);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Call<Usuario> call = interfaz.guardarUsuario("123");
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()){
                            Usuario u = response.body();
                            cambiar(u.getId_usuario());
                        }
                    }
                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Log.e("No furula",t.getMessage());

                    }
                });
            }
        }

    }
    public void cambiar(String string){
        Intent intent = new Intent(this, Buscador.class);
        intent.putExtra("key", string);
        startActivity(intent);
    }
}
