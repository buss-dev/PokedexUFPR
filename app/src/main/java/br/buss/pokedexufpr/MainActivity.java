package br.buss.pokedexufpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.buss.pokedexufpr.apiPokemon.RetrofitConfig;
import br.buss.pokedexufpr.models.User;
import br.buss.pokedexufpr.utils.StateManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void loginUser(View view) throws JSONException {
        if (editTextLogin.getText().length() == 0 || editTextPassword.getText().length() == 0) {
            Toast.makeText(this, "Preencha os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("login", login);
        jsonParams.put("password", password);

        Call<User> call = new RetrofitConfig().getAuthService().loginUser(jsonParams);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();

                    if (user != null && user.getMessage() != null) {
                        Toast.makeText(MainActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    if (user != null && user.getMessage().equals("Usuário logado com sucesso!")) {
                        StateManager.setTokenJwt(user.getToken());
                        Intent it = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(it);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

}