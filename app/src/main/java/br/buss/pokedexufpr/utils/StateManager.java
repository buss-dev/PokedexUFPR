package br.buss.pokedexufpr.utils;

public class StateManager {

    private static String tokenJwt = null;

    public static String getTokenJwt() {
        return tokenJwt;
    }

    public static void setTokenJwt(String tokenJwt) {
        StateManager.tokenJwt = tokenJwt;
    }
}
