package br.buss.pokedexufpr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.DashboardData;
import br.buss.pokedexufpr.models.MessageFromApi;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.utils.ImageHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonActivity extends AppCompatActivity {

    private TextView viewPokemonName;
    private TextView viewPokemonType;
    private TextView viewFirstAbility;
    private TextView viewSecondAbility;
    private TextView viewThirdAbility;
    private TextView viewCriadoPor;
    private ImageView viewPokemonImage;
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        viewPokemonName = findViewById(R.id.viewPokemonName);
        viewPokemonType = findViewById(R.id.viewPokemonType);
        viewFirstAbility = findViewById(R.id.viewFirstAbility);
        viewSecondAbility = findViewById(R.id.viewSecondAbility);
        viewThirdAbility = findViewById(R.id.viewThirdAbility);
        viewCriadoPor = findViewById(R.id.viewCriadoPor);
        viewPokemonImage = findViewById(R.id.viewPokemonImage);

        Bundle bundle = getIntent().getExtras();
        pokemon = (Pokemon) bundle.get("POKEMON");
        setPokemonData(pokemon);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pokemon, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getPokemon();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deletPokemonMenu) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Aguarde ...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("id_pokemon", pokemon.getIdPokemon());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Call<MessageFromApi> call = new RetrofitConfig().getPokeService().deletePokemon(jsonParams);
            call.enqueue(new Callback<MessageFromApi>() {
                @Override
                public void onResponse(Call<MessageFromApi> call, Response<MessageFromApi> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        MessageFromApi message = response.body();
                        Toast.makeText(PokemonActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<MessageFromApi> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }

        if (item.getItemId() == R.id.editPokemonMenu) {
            Intent it = new Intent(PokemonActivity.this, RegisterPokemonActivity.class);
            it.putExtra("POKEMON", pokemon);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPokemon() throws JSONException {
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("id_pokemon", pokemon.getIdPokemon());

        Call<Pokemon> call = new RetrofitConfig().getPokeService().getPokemonById(jsonParams);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    pokemon = response.body();
                    setPokemonData(pokemon);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
            }
        });
    }

    public void setPokemonData(Pokemon pokemon) {

        String[] habilidadesPokemon = pokemon.getHabilidadesPokemon() != null ? pokemon.getHabilidadesPokemon().split(",") : null;

        if (habilidadesPokemon != null && !habilidadesPokemon[0].isEmpty()) {
            viewFirstAbility.setText(habilidadesPokemon[0]);
        } else {
            viewFirstAbility.setText(" - ");
        }

        if (habilidadesPokemon != null && habilidadesPokemon.length > 1 && !habilidadesPokemon[1].isEmpty()) {
            viewSecondAbility.setText(habilidadesPokemon[1]);
        } else {
            viewSecondAbility.setText(" - ");
        }

        if (habilidadesPokemon != null && habilidadesPokemon.length > 2 && !habilidadesPokemon[2].isEmpty()) {
            viewThirdAbility.setText(habilidadesPokemon[2]);
        } else {
            viewThirdAbility.setText(" - ");
        }

        viewPokemonImage.setImageBitmap(ImageHandler.convertBase64ToImage(pokemon.getImagemPokemonBase64()));

        viewPokemonName.setText(pokemon.getPokemonName());
        viewCriadoPor.setText(pokemon.getNomeCriador());
        viewPokemonType.setText(pokemon.getNomeTipoPokemon());
    }
}