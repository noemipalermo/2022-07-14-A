package it.polito.tdp.nyc.model;

public class Arco implements Comparable<Arco>{
	
	private String v1;
	private String v2;
	private Double peso;
	
	
	public Arco(String v1, String v2, Double peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}


	public String getV1() {
		return v1;
	}


	public String getV2() {
		return v2;
	}


	public Double getPeso() {
		return peso;
	}


	@Override
	public int compareTo(Arco other) {
		return this.getPeso().compareTo(other.getPeso()); // perchè ho usato Double con D maiuscola e non double
	
		// se invece di Double uso un Integer -> other.peso-this.peso
	}


	@Override
	public String toString() {
		return "Arco [v1=" + v1 + ", v2=" + v2 + ", peso=" + peso + "]";
	}
	
	
	
	

}
