package com.pacosignes.guicartitas.model;

public enum Caracteristicas {
	
	MOTOR("Motor"),CILINDROS("Cilindros"),POTENCIA("Potencia")
	,REVOLUCIONES("Revoluciones"),VELOCIDAD("Velocidad"),CONSUMO("Consumo");

	public String nombre;
	Caracteristicas(String s){
		nombre=s;
	}

}
