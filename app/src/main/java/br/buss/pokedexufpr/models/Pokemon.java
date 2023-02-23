package br.buss.pokedexufpr.models;

import java.io.Serializable;

public class Pokemon implements Serializable {

    private Integer idPokemon;
    private String pokemonName;
    private String nomeTipoPokemon;
    private String habilidadesPokemon;
    private String nomeCriador;
    private String imagemPokemonBase64;

    public String getImagemPokemonBase64() {
        return imagemPokemonBase64;
    }

    public void setImagemPokemonBase64(String imagemPokemonBase64) {
        this.imagemPokemonBase64 = imagemPokemonBase64;
    }

    public Integer getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(Integer idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getNomeTipoPokemon() {
        return nomeTipoPokemon;
    }

    public void setNomeTipoPokemon(String nomeTipoPokemon) {
        this.nomeTipoPokemon = nomeTipoPokemon;
    }

    public String getHabilidadesPokemon() {
        return habilidadesPokemon;
    }

    public void setHabilidadesPokemon(String habilidadesPokemon) {
        this.habilidadesPokemon = habilidadesPokemon;
    }

    public String getNomeCriador() {
        return nomeCriador;
    }

    public void setNomeCriador(String nomeCriador) {
        this.nomeCriador = nomeCriador;
    }

}
