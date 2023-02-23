package br.buss.pokedexufpr.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.buss.pokedexufpr.PokemonActivity;
import br.buss.pokedexufpr.R;
import br.buss.pokedexufpr.models.Pokemon;
import br.buss.pokedexufpr.utils.ImageHandler;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {

    private List<Pokemon> pokemons;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pokemonName;
        TextView pokemonType;
        ImageView pokemonImage;
        ImageView pokemonTypeImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonName = itemView.findViewById(R.id.pokemonName);
            pokemonType = itemView.findViewById(R.id.pokemonType);
            pokemonImage = itemView.findViewById(R.id.pokemonImage);
            pokemonTypeImage = itemView.findViewById(R.id.pokemonTypeImage);
        }

    }

    public PokemonAdapter(List<Pokemon> list) {
        pokemons = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View transactionItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(transactionItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        holder.pokemonName.setText(pokemon.getPokemonName());
        holder.pokemonType.setText(pokemon.getNomeTipoPokemon());
        holder.pokemonImage.setImageBitmap(ImageHandler.convertBase64ToImage(pokemon.getImagemPokemonBase64()));
        holder.pokemonTypeImage.setImageResource(idTypeImageDrawable(pokemon.getNomeTipoPokemon()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(holder.itemView.getContext(), PokemonActivity.class);
                it.putExtra("POKEMON", pokemons.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public int idTypeImageDrawable(String nomeTipo) {
        switch (nomeTipo) {
            case "Elétrico":
                return R.drawable.pokemon_electric;
            case "Água":
                return R.drawable.pokemon_water;
            case "Planta":
                return R.drawable.pokemon_grass;
            case "Fogo":
                return R.drawable.pokemon_fire;
            case "Lutador":
                return R.drawable.pokemon_fightning;
            case "Psíquico":
                return R.drawable.pokemon_psychic;
            case "Fantasma":
                return R.drawable.pokemon_ghost;
            case "Sombrio":
                return R.drawable.pokemon_dark;
            case "Fada":
                return R.drawable.pokemon_fairy;
            case "Voador":
                return R.drawable.pokemon_flight;
            case "Inseto":
                return R.drawable.pokemon_bug;
            case "Pedra":
                return R.drawable.pokemon_stone;
            case "Terrestre":
                return R.drawable.pokemon_ground;
            case "Gelo":
                return R.drawable.pokemon_ice;
            case "Aço":
                return R.drawable.pokemon_steel;
            case "Venenoso":
                return R.drawable.pokemon_poison;
            case "Dragão":
                return R.drawable.pokemon_dragon;
            case "Normal":
                return R.drawable.pokemon_normal;
        }
        return 0;
    }
}
