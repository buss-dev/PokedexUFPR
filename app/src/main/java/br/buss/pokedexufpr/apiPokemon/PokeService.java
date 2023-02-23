package br.buss.pokedexufpr.apiPokemon;

import org.json.JSONObject;

import java.util.List;

import br.buss.pokedexufpr.models.DashboardData;
import br.buss.pokedexufpr.models.HabilidadePokemon;
import br.buss.pokedexufpr.models.MessageFromApi;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.models.TipoPokemon;
import br.buss.pokedexufpr.utils.StateManager;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PokeService {

    @GET("/pokemon/getAll")
    Call<List<Pokemon>> getAllPokemons();

    @HTTP(method = "POST", path = "/pokemon/getByType", hasBody = true)
    Call<List<Pokemon>> getPokemonsByType(@Body JSONObject jsonParams);

    @HTTP(method = "POST", path = "/pokemon/getByAbility", hasBody = true)
    Call<List<Pokemon>> getPokemonsByAbility(@Body JSONObject jsonParams);

    @HTTP(method = "POST", path = "/pokemon/getById", hasBody = true)
    Call<Pokemon> getPokemonById(@Body JSONObject jsonParams);

    @GET("/pokemon/getDashboardData")
    Call<DashboardData> getDashboardData();

    @GET("/pokemon/getAllTypes")
    Call<List<TipoPokemon>> getAllTypes();

    @POST("/pokemon/savePokemon")
    Call<MessageFromApi> savePokemon(@Body JSONObject jsonParams, @Header("Authorization") String jwt);

    @GET("/pokemon/getAllAbilities")
    Call<List<HabilidadePokemon>> getAllPokemonAbilities();

    @HTTP(method = "DELETE", path = "/pokemon/deletePokemon", hasBody = true)
    Call<MessageFromApi> deletePokemon(@Body JSONObject jsonParams);

    @HTTP(method = "POST", path = "/pokemon/editPokemon", hasBody = true)
    Call<MessageFromApi> editPokemon(@Body JSONObject jsonParams);
}
