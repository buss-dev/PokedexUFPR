package br.buss.pokedexufpr.models.dashboardHelpers;

public class DashboardTipoPokemon {
    private String nomeTipoPokemon;
    private String qtdTipoPokemon;

    public String getNomeTipoPokemon() {
        return nomeTipoPokemon;
    }

    public void setNomeTipoPokemon(String nomeTipoPokemon) {
        this.nomeTipoPokemon = nomeTipoPokemon;
    }

    public String getQtdTipoPokemon() {
        return qtdTipoPokemon;
    }

    public void setQtdTipoPokemon(String qtdTipoPokemon) {
        this.qtdTipoPokemon = qtdTipoPokemon;
    }

    public DashboardTipoPokemon(String nomeTipoPokemon, String qtdTipoPokemon) {
        this.nomeTipoPokemon = nomeTipoPokemon;
        this.qtdTipoPokemon = qtdTipoPokemon;
    }

}
