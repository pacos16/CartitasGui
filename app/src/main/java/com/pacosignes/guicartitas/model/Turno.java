package com.pacosignes.guicartitas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Turno  {

	@SerializedName("partida")
	@Expose
	private int partida;
	@SerializedName("cartaJugador")
	@Expose
	private int cartaJugador;
	@SerializedName("cartaCpu")
	@Expose
	private int cartaCpu;
	@SerializedName("caracteristica")
	@Expose
	private int caracteristica;
	@SerializedName("numTurno")
	@Expose
	private int numTurno;
	@SerializedName("ataque")
	@Expose
	private boolean ataque;
	@SerializedName("resultado")
	@Expose
	private int resultado;
	
	public Turno() {
		
	}
	
	public Turno(int partida, int cartaJugador, int cartaCpu,int caracteristica,int numTurno, boolean ataque, int resultado) {
		super();
		this.partida = partida;
		this.cartaJugador = cartaJugador;
		this.cartaCpu = cartaCpu;
		this.numTurno=numTurno;
		this.caracteristica=caracteristica;
		this.ataque = ataque;
		this.resultado = resultado;
	}

	public int getNumTurno() {
		return numTurno;
	}

	public void setNumTurno(int numTurno) {
		this.numTurno = numTurno;
	}

	public int getPartida() {
		return partida;
	}

	public void setPartida(int partida) {
		this.partida = partida;
	}

	public int getCartaJugador() {
		return cartaJugador;
	}

	public void setCartaJugador(int cartaJugador) {
		this.cartaJugador = cartaJugador;
	}

	public int getCartaCpu() {
		return cartaCpu;
	}

	public void setCartaCpu(int cartaCpu) {
		this.cartaCpu = cartaCpu;
	}

	public int getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(int caracteristica) {
		this.caracteristica = caracteristica;
	}

	public boolean isAtaque() {
		return ataque;
	}

	public void setAtaque(boolean ataque) {
		this.ataque = ataque;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	
	
	

}
