package br.buss.pokedexufpr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.HabilidadePokemon;
import br.buss.pokedexufpr.models.MessageFromApi;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.models.TipoPokemon;
import br.buss.pokedexufpr.utils.ImageHandler;
import br.buss.pokedexufpr.utils.StateManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPokemonActivity extends AppCompatActivity {

    private List<TipoPokemon> tiposPokemon;

    private Spinner spinnerTiposPokemon;
    private Spinner firstAbility;
    private Spinner secondAbility;
    private Spinner thirdAbility;
    private EditText pokemonName;
    private String imageBase64;
    private ImageView imageSetView;

    private List<HabilidadePokemon> habilidadesPokemons;

    ArrayAdapter<HabilidadePokemon> habilidadePokemonArrayAdapter;
    ArrayAdapter<TipoPokemon> tipoPokemonArrayAdapter;

    private Pokemon pokemonEditable;
    private boolean editing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pokemon);

        pokemonName = findViewById(R.id.viewPokemonName);

        spinnerTiposPokemon = findViewById(R.id.viewPokemonType);
        firstAbility = findViewById(R.id.viewFirstAbility);
        secondAbility = findViewById(R.id.viewSecondAbility);
        thirdAbility = findViewById(R.id.viewThirdAbility);
        imageSetView = findViewById(R.id.imageSetView);

        Bundle bundle = getIntent().getExtras();
        if (bundle.size() > 0) {
            pokemonEditable = (Pokemon) bundle.get("POKEMON");
            editing = true;
        }

        getAllPokemonTypes();
        getAllPokemonsAbilities();
    }

    public void setPokemonDataToSave() {
        imageSetView.setImageBitmap(ImageHandler.convertBase64ToImage(pokemonEditable.getImagemPokemonBase64()));
        pokemonName.setText(pokemonEditable.getPokemonName());

        String[] habilidadesPokemon = pokemonEditable.getHabilidadesPokemon() != null ? pokemonEditable.getHabilidadesPokemon().split(",") : null;

        if (habilidadesPokemon != null && !habilidadesPokemon[0].isEmpty()) {
            HabilidadePokemon habilidadePokemonActual;
            for (int i = 0; i < this.habilidadesPokemons.size(); i++) {
                if (habilidadePokemonArrayAdapter.getItem(i).getNomeHabilidadePokemon().equals(habilidadesPokemon[0])) {
                    habilidadePokemonActual = habilidadePokemonArrayAdapter.getItem(i);
                    firstAbility.setSelection(habilidadePokemonArrayAdapter.getPosition(habilidadePokemonActual));
                }
            }
        }

        if (habilidadesPokemon != null && habilidadesPokemon.length > 1 && !habilidadesPokemon[1].isEmpty()) {
            HabilidadePokemon habilidadePokemonActual;
            for (int i = 0; i < this.habilidadesPokemons.size(); i++) {
                if (habilidadePokemonArrayAdapter.getItem(i).getNomeHabilidadePokemon().equals(habilidadesPokemon[1])) {
                    habilidadePokemonActual = habilidadePokemonArrayAdapter.getItem(i);
                    secondAbility.setSelection(habilidadePokemonArrayAdapter.getPosition(habilidadePokemonActual));
                }
            }
        }

        if (habilidadesPokemon != null && habilidadesPokemon.length > 2 && !habilidadesPokemon[2].isEmpty()) {
            HabilidadePokemon habilidadePokemonActual;
            for (int i = 0; i < this.habilidadesPokemons.size(); i++) {
                if (habilidadePokemonArrayAdapter.getItem(i).getNomeHabilidadePokemon().equals(habilidadesPokemon[2])) {
                    habilidadePokemonActual = habilidadePokemonArrayAdapter.getItem(i);
                    thirdAbility.setSelection(habilidadePokemonArrayAdapter.getPosition(habilidadePokemonActual));
                }
            }
        }

        imageBase64 = pokemonEditable.getImagemPokemonBase64();

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

                    if (pokemonEditable != null) {
                        TipoPokemon tipoPokemon;
                        for (int i = 0; i < tiposPokemon.size(); i++) {
                            if (tipoPokemonArrayAdapter.getItem(i).getNomeTipoPokemon().equals(pokemonEditable.getNomeTipoPokemon())) {
                                tipoPokemon = tipoPokemonArrayAdapter.getItem(i);
                                spinnerTiposPokemon.setSelection(tipoPokemonArrayAdapter.getPosition(tipoPokemon));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TipoPokemon>> call, Throwable t) {
            }
        });
    }

    public void getAllPokemonsAbilities() {
        Call<List<HabilidadePokemon>> call = new RetrofitConfig().getPokeService().getAllPokemonAbilities();
        call.enqueue(new Callback<List<HabilidadePokemon>>() {
            @Override
            public void onResponse(Call<List<HabilidadePokemon>> call, Response<List<HabilidadePokemon>> response) {
                if (response.isSuccessful()) {
                    habilidadesPokemons = response.body();
                    habilidadesPokemons.add(0, new HabilidadePokemon("0", "", ""));
                    setSpinnerHabilidadesPokemon();


                    if (pokemonEditable != null) {
                        setPokemonDataToSave();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<HabilidadePokemon>> call, Throwable t) {
            }
        });
    }

    public void setSpinnerHabilidadesPokemon() {
        habilidadePokemonArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, habilidadesPokemons);

        firstAbility.setAdapter(habilidadePokemonArrayAdapter);
        secondAbility.setAdapter(habilidadePokemonArrayAdapter);
        thirdAbility.setAdapter(habilidadePokemonArrayAdapter);

        firstAbility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HabilidadePokemon thirdAbilitySelected = (HabilidadePokemon) thirdAbility.getSelectedItem();
                HabilidadePokemon secondAbilitySelected = (HabilidadePokemon) secondAbility.getSelectedItem();

                if ((habilidadesPokemons.get(position).equals(thirdAbilitySelected)
                        || habilidadesPokemons.get(position).equals(secondAbilitySelected)) && position != 0) {
                    firstAbility.setAdapter(null);
                    firstAbility.setAdapter(habilidadePokemonArrayAdapter);
                    Toast.makeText(RegisterPokemonActivity.this, "Não é possível selecionar a mesma habilidade duas vezes", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        secondAbility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HabilidadePokemon firstAbilitySelected = (HabilidadePokemon) firstAbility.getSelectedItem();
                HabilidadePokemon thirdAbilitySelected = (HabilidadePokemon) thirdAbility.getSelectedItem();

                if ((habilidadesPokemons.get(position).equals(thirdAbilitySelected)
                        || habilidadesPokemons.get(position).equals(firstAbilitySelected)) && position != 0) {
                    secondAbility.setAdapter(null);
                    secondAbility.setAdapter(habilidadePokemonArrayAdapter);
                    Toast.makeText(RegisterPokemonActivity.this, "Não é possível selecionar a mesma habilidade duas vezes", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        thirdAbility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HabilidadePokemon firstAbilitySelected = (HabilidadePokemon) firstAbility.getSelectedItem();
                HabilidadePokemon secondAbilitySelected = (HabilidadePokemon) secondAbility.getSelectedItem();

                if ((habilidadesPokemons.get(position).equals(firstAbilitySelected)
                        || habilidadesPokemons.get(position).equals(secondAbilitySelected)) && position != 0) {
                    thirdAbility.setAdapter(null);
                    thirdAbility.setAdapter(habilidadePokemonArrayAdapter);
                    Toast.makeText(RegisterPokemonActivity.this, "Não é possível selecionar a mesma habilidade duas vezes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setSpinnerTipoPokemonAdapter() {
        tipoPokemonArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposPokemon);
        spinnerTiposPokemon.setAdapter(tipoPokemonArrayAdapter);
        spinnerTiposPokemon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void savePokemon(View view) throws JSONException {

        if (pokemonName.getText().length() == 0) {
            Toast.makeText(this, "Preencha o nome do pokémon", Toast.LENGTH_SHORT).show();
            return;
        }

        TipoPokemon tipoPokemonSelecionado = (TipoPokemon) spinnerTiposPokemon.getSelectedItem();
        if (tipoPokemonSelecionado.getIdTipoPokemon() == 0) {
            Toast.makeText(this, "Preencha o tipo do pokémon", Toast.LENGTH_SHORT).show();
            return;
        }

        String nomePokemon = pokemonName.getText().toString();

        Integer idTipoPokemon = tipoPokemonSelecionado.getIdTipoPokemon();

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("nome_pokemon", nomePokemon);
        jsonParams.put("tipo_pokemon", idTipoPokemon);

        HabilidadePokemon habilidadePokemon1 = (HabilidadePokemon) firstAbility.getSelectedItem();
        jsonParams.put("id_habilidade_pokemon_1", Integer.parseInt(habilidadePokemon1.getIdHabilidadePokemon()));
        HabilidadePokemon habilidadePokemon2 = (HabilidadePokemon) secondAbility.getSelectedItem();
        jsonParams.put("id_habilidade_pokemon_2", Integer.parseInt(habilidadePokemon2.getIdHabilidadePokemon()));
        HabilidadePokemon habilidadePokemon3 = (HabilidadePokemon) thirdAbility.getSelectedItem();
        jsonParams.put("id_habilidade_pokemon_3", Integer.parseInt(habilidadePokemon3.getIdHabilidadePokemon()));

        if (habilidadePokemon1.getIdHabilidadePokemon().equals("0")
                && habilidadePokemon2.getIdHabilidadePokemon().equals("0")
                && habilidadePokemon3.getIdHabilidadePokemon().equals("0")) {
            Toast.makeText(this, "Selecione pelo menos uma habilidade", Toast.LENGTH_SHORT).show();
        }

        if (imageBase64 == null) {
            Toast.makeText(this, "Insira uma imagem", Toast.LENGTH_SHORT).show();
            return;
        }

        jsonParams.put("pokemon_image", imageBase64);

        if (pokemonEditable == null) {
            Call<MessageFromApi> call = new RetrofitConfig().getPokeService().savePokemon(jsonParams,
                    "Bearer " + StateManager.getTokenJwt());
            call.enqueue(new Callback<MessageFromApi>() {
                @Override
                public void onResponse(Call<MessageFromApi> call, Response<MessageFromApi> response) {
                    if (response.isSuccessful()) {
                        MessageFromApi message = response.body();
                        if (message != null) {
                            Toast.makeText(RegisterPokemonActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (!message.getMessage().equals("Pokémon com esse nome já existe")) {
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFromApi> call, Throwable t) {
                    System.out.println(t.toString());
                }
            });
        } else {
            jsonParams.put("id_pokemon", pokemonEditable.getIdPokemon());

            Call<MessageFromApi> call = new RetrofitConfig().getPokeService().editPokemon(jsonParams);
            call.enqueue(new Callback<MessageFromApi>() {
                @Override
                public void onResponse(Call<MessageFromApi> call, Response<MessageFromApi> response) {
                    if (response.isSuccessful()) {
                        MessageFromApi message = response.body();
                        if (message != null && message.getMessage() != null) {
                            Toast.makeText(RegisterPokemonActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (message != null && message.getMessage() != null && message.getMessage().equals("Pokémon editado com sucesso")) {
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFromApi> call, Throwable t) {
                    System.out.println(t.toString());
                }
            });
        }
    }

    public void insertPhoto(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Selecione uma opção");
        alertDialogBuilder.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        alertDialogBuilder.setNegativeButton("Tirar foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            imageBase64 = ImageHandler.convertImageToBase64(this, selectedImage);
            imageSetView.setImageURI(selectedImage);
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageSetView.setImageBitmap(imageBitmap);
            imageBase64 = ImageHandler.convertImageToBase64(this, ImageHandler.getImageUri(this, imageBitmap));
        }
    }

}