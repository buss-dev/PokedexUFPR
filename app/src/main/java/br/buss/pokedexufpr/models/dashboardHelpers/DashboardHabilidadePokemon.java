package br.buss.pokedexufpr.models.dashboardHelpers;

public class DashboardHabilidadePokemon {
    private String nomeHabilidadePokemon;
    private String qtdPokemonHabilidade;

    public String getNomeHabilidadePokemon() {
        return nomeHabilidadePokemon;
    }

    public void setNomeHabilidadePokemon(String nomeHabilidadePokemon) {
        this.nomeHabilidadePokemon = nomeHabilidadePokemon;
    }

    public String getQtdPokemonHabilidade() {
        return qtdPokemonHabilidade;
    }

    public void setQtdPokemonHabilidade(String qtdPokemonHabilidade) {
        this.qtdPokemonHabilidade = qtdPokemonHabilidade;
    }

    public DashboardHabilidadePokemon(String nomeHabilidadePokemon, String qtdPokemonHabilidade) {
        this.nomeHabilidadePokemon = nomeHabilidadePokemon;
        this.qtdPokemonHabilidade = qtdPokemonHabilidade;
    }
}
