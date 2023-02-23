package br.buss.pokedexufpr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.buss.pokedexufpr.adapter.PokemonAdapter;
import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.DashboardData;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.models.dashboardHelpers.DashboardHabilidadePokemon;
import br.buss.pokedexufpr.models.dashboardHelpers.DashboardTipoPokemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private DashboardData dashboardData = new DashboardData();

    private TextView firstMostAbility;
    private TextView firstMostAbilityValue;
    private TextView secondMostAbility;
    private TextView secondMostAbilityValue;
    private TextView thirdMostAbility;
    private TextView thirdMostAbilityValue;
    private TextView firstMostType;
    private TextView firstMostTypeValue;
    private TextView secondMostType;
    private TextView secondMostTypeValue;
    private TextView thirdMostType;
    private TextView thirdMostTypeValue;
    private TextView registeredPokemons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firstMostAbility = findViewById(R.id.first_most_ability);
        firstMostAbilityValue = findViewById(R.id.first_most_ability_value);

        secondMostAbility = findViewById(R.id.second_most_ability);
        secondMostAbilityValue = findViewById(R.id.second_most_ability_value);

        thirdMostAbility = findViewById(R.id.third_most_ability);
        thirdMostAbilityValue = findViewById(R.id.third_most_ability_value);

        firstMostType = findViewById(R.id.first_most_type);
        firstMostTypeValue = findViewById(R.id.first_most_type_value);

        secondMostType = findViewById(R.id.second_most_type);
        secondMostTypeValue = findViewById(R.id.second_most_type_value);

        thirdMostType = findViewById(R.id.third_most_type);
        thirdMostTypeValue = findViewById(R.id.third_most_type_value);

        registeredPokemons = findViewById(R.id.textView8);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Integer itemId = item.getItemId();

        if (itemId == R.id.exitMenu) {
            finishAffinity();
        }

        if (itemId == R.id.registerPokemon) {
            Intent it = new Intent(this, RegisterPokemonActivity.class);
            it.putExtra("ID_TELA", R.id.registerPokemon);
            startActivity(it);
        }

        if (itemId == R.id.searchByAbility) {
            Intent it = new Intent(this, PokemonListActivity.class);
            it.putExtra("ID_TELA", R.id.searchByAbility);
            startActivity(it);
        }

        if (itemId == R.id.searchByType) {
            Intent it = new Intent(this, PokemonListActivity.class);
            it.putExtra("ID_TELA", R.id.searchByType);
            startActivity(it);
        }

        if (itemId == R.id.listAllPokemons) {
            Intent it = new Intent(this, ListAllPokemonsActivity.class);
            it.putExtra("ID_TELA", R.id.listAllPokemons);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<DashboardData> call = new RetrofitConfig().getPokeService().getDashboardData();
        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                if (response.isSuccessful()) {
                    dashboardData = response.body();
                    setDashboardValues();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    public void setDashboardValues() {
        String pokemonsQuantity = dashboardData.getQuantidade();
        List<DashboardHabilidadePokemon> mostAbilites = dashboardData.getHabilidades();
        List<DashboardTipoPokemon> mostTypes = dashboardData.getTipos();

        if (mostAbilites.size() < 3) {
            while (mostAbilites.size() < 3) {
                mostAbilites.add(new DashboardHabilidadePokemon("", ""));
            }
        }

        if (mostTypes.size() < 3) {
            while (mostTypes.size() < 3) {
                mostTypes.add(new DashboardTipoPokemon("", ""));
            }
        }

        firstMostAbility.setText(mostAbilites.get(0).getNomeHabilidadePokemon());
        firstMostAbilityValue.setText(mostAbilites.get(0).getQtdPokemonHabilidade());

        secondMostAbility.setText(mostAbilites.get(1).getNomeHabilidadePokemon());
        secondMostAbilityValue.setText(mostAbilites.get(1).getQtdPokemonHabilidade());

        thirdMostAbility.setText(mostAbilites.get(2).getNomeHabilidadePokemon());
        thirdMostAbilityValue.setText(mostAbilites.get(2).getQtdPokemonHabilidade());

        firstMostType.setText(mostTypes.get(0).getNomeTipoPokemon());
        firstMostTypeValue.setText(mostTypes.get(0).getQtdTipoPokemon());

        secondMostType.setText(mostTypes.get(1).getNomeTipoPokemon());
        secondMostTypeValue.setText(mostTypes.get(1).getQtdTipoPokemon());

        thirdMostType.setText(mostTypes.get(2).getNomeTipoPokemon());
        thirdMostTypeValue.setText(mostTypes.get(2).getQtdTipoPokemon());

        registeredPokemons.setText(pokemonsQuantity);

    }

}