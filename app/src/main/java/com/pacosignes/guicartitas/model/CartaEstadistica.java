package com.pacosignes.guicartitas.model;

public class CartaEstadistica implements Comparable<CartaEstadistica> {

    private int idCarta;
    private int ganadas;
    private int perdidas;
    private int empatadas;
    private int winrate;

    public CartaEstadistica(){

    }
    public CartaEstadistica(int idCarta, int ganadas, int perdidas, int empatadas) {
        this.idCarta = idCarta;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.empatadas = empatadas;
        calcularWinrate();
    }
    private void calcularWinrate(){
        double aux=((double)ganadas/(ganadas+perdidas+empatadas))*100;
        winrate=(int) aux;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public int getGanadas() {
        return ganadas;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getEmpatadas() {
        return empatadas;
    }

    public void setEmpatadas(int empatadas) {
        this.empatadas = empatadas;
    }

    public int getWinrate() {
        return winrate;
    }

    public void setWinrate(int winrate) {
        this.winrate = winrate;
    }

    @Override
    public int compareTo(CartaEstadistica o) {

        if(this.winrate==o.winrate){
            return this.getGanadas()-o.getGanadas();
        }
        return this.winrate-o.winrate;
    }
}
