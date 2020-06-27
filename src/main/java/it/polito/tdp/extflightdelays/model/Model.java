package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private ExtFlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> idMap;
	
	public Model() {
		dao= new ExtFlightDelaysDAO();
		idMap= new HashMap<>();
	}
	
	public void creaGrafo(Integer distanza) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllAirports(idMap);
		
		Graphs.addAllVertices(grafo, idMap.values());
		
		for(Rotte r: dao.getRotte(idMap, distanza)) {
			if(this.grafo.containsVertex(r.getA1()) && this.grafo.containsVertex(r.getA2())) {
				DefaultWeightedEdge e= this.grafo.getEdge(r.getA1(), r.getA2());
					if(e==null) {
						Graphs.addEdgeWithVertices(grafo,r.getA1(),r.getA2(), r.getPeso());
				}else {
					Double peso= this.grafo.getEdgeWeight(e);
					Double pesoNuovo= (peso+r.getPeso())/2;
					Graphs.addEdge(grafo, r.getA1(), r.getA2(), pesoNuovo);
				}
			}
		}
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size()+" vertici e con "+ this.grafo.edgeSet().size()+" archi\n");
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Airport> getAeroporti(){
		return this.dao.getAeroporti(idMap);
	}
	
	public List<Rotte> listaVicini(Airport a){
		List<Rotte> result= new ArrayList<>();
		
		List<Airport> vicini= Graphs.neighborListOf(grafo, a);
		for(Airport ai: vicini) {
			Double peso= this.grafo.getEdgeWeight(this.grafo.getEdge(a, ai));
			result.add(new Rotte(a, ai, peso));
		}
		Collections.sort(result);
		return result;
	}

}
