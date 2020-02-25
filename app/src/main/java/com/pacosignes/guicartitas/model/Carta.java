package com.pacosignes.guicartitas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Carta {

	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("marca")
	@Expose
	private String marca;
	@SerializedName("modelo")
	@Expose
	private String modelo;
	@SerializedName("motor")
	@Expose
	private int motor;
	@SerializedName("cilindros")
	@Expose
	private int cilindros;
	@SerializedName("potencia")
	@Expose
	private String potencia;
	@SerializedName("revolucinoes")
	@Expose
	private int revolucinoes;
	@SerializedName("velocidad")
	@Expose
	private int velocidad;
	@SerializedName("consumo")
	@Expose
	private double consumo;

	
	public Carta() {
		
	}
	
	public Carta(int id, String marca, String modelo, int motor, int cilindros, String potencia, int revolucinoes,
			int velocidad, double consumo) {
		super();
		this.id=id;
		this.marca = marca;
		this.modelo = modelo;
		this.motor = motor;
		this.cilindros = cilindros;
		this.potencia = potencia;
		this.revolucinoes = revolucinoes;
		this.velocidad = velocidad;
		this.consumo = consumo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getMotor() {
		return motor;
	}
	public void setMotor(int motor) {
		this.motor = motor;
	}
	public int getCilindros() {
		return cilindros;
	}
	public void setCilindros(int cilindros) {
		this.cilindros = cilindros;
	}
	public String getPotencia() {
		return potencia;
	}
	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	public int getRevolucinoes() {
		return revolucinoes;
	}
	public void setRevolucinoes(int revolucinoes) {
		this.revolucinoes = revolucinoes;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public double getConsumo() {
		return consumo;
	}
	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	@Override
	public String toString() {
		return "Carta{" +
				"id=" + id +
				", marca='" + marca + '\'' +
				", modelo='" + modelo + '\'' +
				", motor=" + motor +
				", cilindros=" + cilindros +
				", potencia='" + potencia + '\'' +
				", revolucinoes=" + revolucinoes +
				", velocidad=" + velocidad +
				", consumo=" + consumo +
				'}';
	}
}
