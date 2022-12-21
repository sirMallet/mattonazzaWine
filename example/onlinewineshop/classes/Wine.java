package com.example.onlinewineshop.classes;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.sql.*;

public class Wine {
	private String nome;
	private String region;
	private int vintage;
	private String varietal;
	private int Valutazione;
	private float prezzo;
	private int qta;
	public Wine(){
		this.nome = "";
		this.region = "";
		this.vintage = 0;
		this.varietal = "";
		this.Valutazione = 0;
		this.prezzo = 0;
		this.qta=0;

	}
	
	public Wine(String nome, String region, int vintage, String varietal, int Valutazione, float prezzo,int qta){
		this.nome = nome;
		this.region = region;
		this.vintage = vintage;
		this.varietal = varietal;
		this.Valutazione = Valutazione;
		this.prezzo =  prezzo;
		this.qta = qta;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getVintage() {
		return vintage;
	}
	public void setVintage(int vintage) {
		this.vintage = vintage;
	}
	public String getVarietal() {
		return varietal;
	}
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
	public int getValutazione() {
		return Valutazione;
	}

	public void setValutazione(int valutazione) {
		Valutazione = valutazione;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public int getQta() {
		return qta;
	}


	public void setQta(int qta) {
		this.qta = qta;
	}

	//Override toString method
	@Override
	public String toString() {
		return "Wine [nome=" + nome + ", region=" + region + ", vintage=" + vintage + ", varietal=" + varietal
				+ ", Valutazione=" + Valutazione + ", prezzo=" + prezzo + "]";
	}
}
