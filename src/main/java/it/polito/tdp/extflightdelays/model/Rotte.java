package it.polito.tdp.extflightdelays.model;

public class Rotte implements Comparable<Rotte> {
	Airport a1;
	Airport a2;
	Double peso;
	
	public Rotte(Airport a1, Airport a2, Double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public Airport getA1() {
		return a1;
	}

	public void setA1(Airport a1) {
		this.a1 = a1;
	}

	public Airport getA2() {
		return a2;
	}

	public void setA2(Airport a2) {
		this.a2 = a2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Rotte o) {
		return -(this.getPeso().compareTo(o.getPeso()));
	}

	@Override
	public String toString() {
		return a2 + " " + peso ;
	}
	
	
	

}
