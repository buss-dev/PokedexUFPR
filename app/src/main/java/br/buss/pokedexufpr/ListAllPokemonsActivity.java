package br.buss.pokedexufpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.buss.pokedexufpr.adapter.PokemonAdapter;
import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.models.TipoPokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAllPokemonsActivity extends AppCompatActivity {

    private RecyclerView listAllPokemons;
    private List<Pokemon> pokemonList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_pokemons);

        progressDialog = new ProgressDialog(this);

        listAllPokemons = findViewById(R.id.recyclerListAll);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPokemons();
    }

    public void getPokemons() {
        progressDialog.setMessage("Aguarde ...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        Call<List<Pokemon>> call = new RetrofitConfig().getPokeService().getAllPokemons();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    pokemonList = response.body();
                    updateRecyclerPokemon();
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                System.out.println(t.toString());
                progressDialog.dismiss();
            }
        });
    }

    public void updateRecyclerPokemon() {
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemonList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listAllPokemons.setLayoutManager(layoutManager);
        listAllPokemons.setHasFixedSize(true);
        listAllPokemons.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        listAllPokemons.setAdapter(pokemonAdapter);
        progressDialog.dismiss();
    }

}