package br.buss.pokedexufpr.models;

import java.util.List;

import br.buss.pokedexufpr.models.dashboardHelpers.DashboardHabilidadePokemon;
import br.buss.pokedexufpr.models.dashboardHelpers.DashboardTipoPokemon;

public class DashboardData {
    private String quantidade;
    private List<DashboardTipoPokemon> tipos;
    private List<DashboardHabilidadePokemon> habilidades;

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public List<DashboardTipoPokemon> getTipos() {
        return tipos;
    }

    public void setTipos(List<DashboardTipoPokemon> tipos) {
        this.tipos = tipos;
    }

    public List<DashboardHabilidadePokemon> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<DashboardHabilidadePokemon> habilidades) {
        this.habilidades = habilidades;
    }
}
