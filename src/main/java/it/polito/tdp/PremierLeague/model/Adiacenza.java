package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable<Adiacenza> {
	
	private Partita partita1;
	private Partita partita2;
	private Integer peso;
	
	public Adiacenza(Partita partita1, Partita partita2, Integer peso) {
		super();
		this.partita1 = partita1;
		this.partita2 = partita2;
		this.peso = peso;
	}
	
	public Partita getPartita1() {
		return partita1;
	}
	
	public void setPartita1(Partita partita1) {
		this.partita1 = partita1;
	}
	
	public Partita getPartita2() {
		return partita2;
	}
	
	public void setPartita2(Partita partita2) {
		this.partita2 = partita2;
	}
	
	public Integer getPeso() {
		return peso;
	}
	
	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Adiacenza other) {
		return -this.peso.compareTo(other.getPeso());
	}

	@Override
	public String toString() {
		String output = "[" +partita1.getPartitaId()+ "] " + partita1.getTeamHomeName() + " vs. " + partita1.getTeamAwayName() + " - [";
		output = output + partita2.getPartitaId() + "]" + partita2.getTeamHomeName() + " vs. " + partita2.getTeamAwayName() + " (" + this.getPeso() + ")";
		return output;
	}

}