package br.buss.pokedexufpr.apiPokemon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
//                .baseUrl("http://192.168.18.5:5000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public PokeService getPokeService() {
        return this.retrofit.create(PokeService.class);
    }

    public AuthService getAuthService() {
        return this.retrofit.create(AuthService.class);
    }
}
