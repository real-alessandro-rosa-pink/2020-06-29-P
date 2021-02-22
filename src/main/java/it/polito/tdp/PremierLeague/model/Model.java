package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Partita, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Partita> idMapPartiteTutte;
	private List<Adiacenza> archi;
	
	private Double pesoCamminoMigliore;
	private List<Partita> camminoPesoMassimo;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMapPartiteTutte = new HashMap<>();
		this.dao.popolaIdMapVertici(idMapPartiteTutte);
	}
	
	public List<Integer> getMesi() {
		return this.dao.getMesi();
	}
	
	public String creaGrafo(Integer timePlayed, Integer mese) {
		// INSTANZIAMENTO DEL GRAFO
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.archi = this.dao.getArchi(timePlayed, mese, idMapPartiteTutte);
		// POPOLAMENTO DEI VERTICI
		for(Integer vertice : this.dao.getVertici(mese)) {
			this.grafo.addVertex(idMapPartiteTutte.get(vertice));
		}
		// POPOLAMENTO DEGLI ARCHI
		for(Adiacenza arco : this.dao.getArchi(timePlayed, mese, idMapPartiteTutte)) {
			Graphs.addEdge(this.grafo, arco.getPartita1(), arco.getPartita2(), arco.getPeso());
		}
		// RITORNO DEL MESSAGGIO
		String message = String.format("Grafo creato\n#VERTICI: %d\n#ARCHI: %d\n\n", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		return message;
	}
	
	public List<Adiacenza> connessioneMassima() {
		List<Adiacenza> archiConnessioneMassima = new ArrayList<>();
		Collections.sort(this.archi);
		Integer pesoMassimo = this.archi.get(0).getPeso();
		for(int i = 0; i<this.archi.size(); i++) {
			if(this.archi.get(i).getPeso() == pesoMassimo) {
				archiConnessioneMassima.add(this.archi.get(i));
			}
		}
		return archiConnessioneMassima;
	}
	
	public List<Partita> getPartiteGrafo() {
		List<Partita> verticiGrafo = new ArrayList<>();
		for(Partita p : this.grafo.vertexSet()) {
			verticiGrafo.add(p);
		}
		return verticiGrafo;
	}
	
	public List<Partita> trovaPercorsoPesoMassimo(Partita sorgente, Partita destinazione) {
		this.camminoPesoMassimo = new ArrayList<>();
		this.pesoCamminoMigliore = 0.0;
		List<Partita> parziale = new ArrayList<>();
		parziale.add(sorgente);
		ricorsione(parziale, destinazione, 1);
		return this.camminoPesoMassimo;
	}
	
	private void ricorsione(List<Partita> parziale, Partita destinazione, Integer livello) {
		// CONDIZIONE DI TERMINAZIONE
		if( parziale.get(parziale.size()-1).getPartitaId() == destinazione.getPartitaId() ) {
			// Calcolo il peso del cammino, se il peso è maggiore della soluzione attuale, 
			// allora aggiorno il peso
			Double pesoAttuale = 0.0;
			for(int i = 0; i<parziale.size()-1; i++) {
				pesoAttuale = pesoAttuale + this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i), parziale.get(i+1)));
			}
			if( pesoAttuale > this.pesoCamminoMigliore ) {
				this.pesoCamminoMigliore = pesoAttuale;
				this.camminoPesoMassimo = new ArrayList<>(parziale);
			}
		}
		
		Partita ultimo = parziale.get(parziale.size()-1);
		List<Partita> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for(Partita vicino : vicini) {
			// Il cammino deve essere aciclico => la soluzione parziale non deve contenere il vicino
			if( parziale.contains(vicino) == false ) {
				if( vicino.getTeamAwayId() != parziale.get(parziale.size()-1).getTeamAwayId() || vicino.getTeamHomeId() != parziale.get(parziale.size()-1).getTeamAwayId() ) {
					// RISOLVO IL PROBLEMA AL LIVELLO N
					parziale.add(vicino);
					// PASSO IL PROBLEMA AD UN'ALTRA FUNZIONE RICORSIVA
					ricorsione(parziale, destinazione, livello+1);
					// BACKTRACKING
					parziale.remove(vicino);
				}
			}
		}
		
	}
	
}
