package br.buss.pokedexufpr.apiPokemon;

import org.json.JSONObject;

import br.buss.pokedexufpr.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/users/login")
    Call<User> loginUser(@Body JSONObject jsonParams);

}
