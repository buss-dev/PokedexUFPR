package br.buss.pokedexufpr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Erro");
                        alertDialogBuilder.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.setMessage(user.getMessage());
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }

                    if (user != null && user.getMessage().equals("Usuário logado com sucesso!")) {
                        StateManager.setTokenJwt(user.getToken());
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Usuário logado");
                        alertDialogBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.setMessage(user.getMessage());
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
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