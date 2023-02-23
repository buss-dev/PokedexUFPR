package br.buss.pokedexufpr.models;

public class TipoPokemon {

    private Integer idTipoPokemon;
    private String nomeTipoPokemon;

    public String getNomeTipoPokemon() {
        return nomeTipoPokemon;
    }

    public void setNomeTipoPokemon(String nomeTipoPokemon) {
        this.nomeTipoPokemon = nomeTipoPokemon;
    }

    public Integer getIdTipoPokemon() {
        return idTipoPokemon;
    }

    public void setIdTipoPokemon(Integer idTipoPokemon) {
        this.idTipoPokemon = idTipoPokemon;
    }

    public TipoPokemon(Integer idTipoPokemon, String nomeTipoPokemon) {
        this.idTipoPokemon = idTipoPokemon;
        this.nomeTipoPokemon = nomeTipoPokemon;
    }

    @Override
    public String toString() {
        return getNomeTipoPokemon();
    }
}
