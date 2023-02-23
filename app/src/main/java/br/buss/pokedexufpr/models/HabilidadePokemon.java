package br.buss.pokedexufpr.models;

public class HabilidadePokemon {

    private String idHabilidadePokemon;
    private String nomeHabilidadePokemon;
    private String idTipoPokemon;

    public HabilidadePokemon(String idHabilidadePokemon, String nomeHabilidadePokemon, String idTipoPokemon) {
        this.idHabilidadePokemon = idHabilidadePokemon;
        this.nomeHabilidadePokemon = nomeHabilidadePokemon;
        this.idTipoPokemon = idTipoPokemon;
    }

    public String getIdHabilidadePokemon() {
        return idHabilidadePokemon;
    }

    public void setIdHabilidadePokemon(String idHabilidadePokemon) {
        this.idHabilidadePokemon = idHabilidadePokemon;
    }

    public String getNomeHabilidadePokemon() {
        return nomeHabilidadePokemon;
    }

    public void setNomeHabilidadePokemon(String nomeHabilidadePokemon) {
        this.nomeHabilidadePokemon = nomeHabilidadePokemon;
    }

    public String getIdTipoPokemon() {
        return idTipoPokemon;
    }

    public void setIdTipoPokemon(String idTipoPokemon) {
        this.idTipoPokemon = idTipoPokemon;
    }

    @Override
    public String toString() {
        return nomeHabilidadePokemon;
    }
}
