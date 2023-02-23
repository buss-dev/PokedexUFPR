package br.buss.pokedexufpr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.buss.pokedexufpr.adapter.PokemonAdapter;
import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.models.TipoPokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonListActivity extends AppCompatActivity {

    private EditText nameAbilityEditText;
    private Spinner spinnerTypeSearch;
    private List<TipoPokemon> tiposPokemon;
    private List<Pokemon> pokemonList = new ArrayList<>();
    private Integer idTela;

    private RecyclerView recyclerViewPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        recyclerViewPokemon = findViewById(R.id.recyclerPokemonList);

        nameAbilityEditText = findViewById(R.id.editTextAbilityNameSearch);
        spinnerTypeSearch = findViewById(R.id.spinnerPokemonTypesSearch);

        Bundle bundle = getIntent().getExtras();
        idTela = (int) bundle.get("ID_TELA");

        if (idTela == R.id.searchByType) {
            nameAbilityEditText.setVisibility(View.INVISIBLE);
            spinnerTypeSearch.setVisibility(View.VISIBLE);
        }

        getAllPokemonTypes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            TipoPokemon tipoPokemonSelecionado = (TipoPokemon) spinnerTypeSearch.getSelectedItem();
            if (tipoPokemonSelecionado != null) {
                getPokemons();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPokemons(View view) throws JSONException {
        getPokemons();
    }

    public void getPokemons() throws JSONException {
        JSONObject jsonParams = new JSONObject();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (idTela == R.id.searchByType) {
            TipoPokemon tipoPokemonSelecionado = (TipoPokemon) spinnerTypeSearch.getSelectedItem();
            Integer idTipoPokemon = tipoPokemonSelecionado.getIdTipoPokemon();
            jsonParams.put("id_tipo_pokemon", idTipoPokemon);
        } else {
            jsonParams.put("nome_habilidade_pokemon", nameAbilityEditText.getText().toString());
        }

        Call<List<Pokemon>> call = idTela == R.id.searchByType ?
                new RetrofitConfig().getPokeService().getPokemonsByType(jsonParams)
                : new RetrofitConfig().getPokeService().getPokemonsByAbility(jsonParams);

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if (response.isSuccessful()) {
                    pokemonList = response.body();
                    updateRecyclerPokemon();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void getAllPokemonTypes() {
        Call<List<TipoPokemon>> call = new RetrofitConfig().getPokeService().getAllTypes();
        call.enqueue(new Callback<List<TipoPokemon>>() {
            @Override
            public void onResponse(Call<List<TipoPokemon>> call, Response<List<TipoPokemon>> response) {
                if (response.isSuccessful()) {
                    tiposPokemon = response.body();
                    tiposPokemon.add(0, new TipoPokemon(0, ""));
                    setSpinnerTipoPokemonAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<TipoPokemon>> call, Throwable t) {
            }
        });
    }

    private void setSpinnerTipoPokemonAdapter() {
        ArrayAdapter<TipoPokemon> tipoPokemonArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposPokemon);
        spinnerTypeSearch.setAdapter(tipoPokemonArrayAdapter);
        spinnerTypeSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        updateRecyclerPokemon();
    }

    public void updateRecyclerPokemon() {
        PokemonAdapter pokemonAdapter = new PokemonAdapter(pokemonList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewPokemon.setLayoutManager(layoutManager);
        recyclerViewPokemon.setHasFixedSize(true);
        recyclerViewPokemon.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewPokemon.setAdapter(pokemonAdapter);
    }

}