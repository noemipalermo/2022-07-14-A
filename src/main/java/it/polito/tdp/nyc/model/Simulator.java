package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulator {

	// parametri in ingresso
	private double probShare;
	private int durationShare;
	
	// stato del sistema
	private Graph<NTA, DefaultWeightedEdge> grafo;
	private Map<NTA, Integer> numShare;
	private List<NTA> vertici ;
	
	// parametri in uscita
	private Map<NTA, Integer> numShareTot;
	
	
	// coda degli eventi
	private PriorityQueue<Event> queue;


	public Simulator( Graph<NTA, DefaultWeightedEdge> grafo, double probShare, int durationShare) {
		super();
		this.probShare = probShare;
		this.durationShare = durationShare;
		this.grafo = grafo;
	}

	public void initialize() {
		
		this.queue = new PriorityQueue<>();
		
		this.numShare = new HashMap<NTA, Integer>();
		this.numShareTot = new HashMap<NTA, Integer>();
		
		for(NTA n:  this.grafo.vertexSet()) { // inizializzo a 0 il numero delle condivisioni in entrambe le mappe
			this.numShare.put(n, 0);
			this.numShareTot.put(n, 0);
		}
		
		this.vertici = new ArrayList<>(this.grafo.vertexSet());
		// creo eventi iniziali
		for(int t=0; t<=100; t++) {
			if(Math.random()<=this.probShare) {
				int n= (int)(Math.random()*this.vertici.size()); // scelgo un numero casuale da 0 a num di vertici
				queue.add(new Event(EventType.SHARE, t, this.vertici.get(n), this.durationShare)); // nta estratto casualemnte -> metto i vertici in una lista perchè il set non è ordinato 
			}
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll();
			
			if(e.getTime()>=100) {
				break;
			}
			
			int time = e.getTime();
			int duration = e.getDuration();
			NTA nta = e.getNta();
			
			
			System.out.println(e.getType()+" - "+time+" - "+e.getNta()+" - "+e.getDuration());
			switch(e.getType()) {
			case SHARE:
				this.numShare.put(nta, this.numShare.get(nta)+1);
				this.numShareTot.put(nta, this.numShareTot.get(nta)+1);
				
				this.queue.add(new Event(EventType.STOP, time+duration, nta, 0));
				
				// ri-condivisione
				if(duration/2>0) {
					NTA nuovo = trovaNTA(nta);
					if(nuovo!=null) {
						this.queue.add(new Event(EventType.SHARE, time+1, nuovo, duration/2));
					}
				}
				break;
			
			case STOP:
				this.numShare.put(nta, this.numShare.get(nta)-1);
			
				break;
			}
		
		}

	}

	private NTA trovaNTA(NTA nta) {

		int max = -1;
		NTA best = null;
		
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(nta)) { // ciclo sugli ARCHI USCENTI da un determinato vertici
			NTA vicino  = Graphs.getOppositeVertex(this.grafo, e, nta);
			int peso = (int)this.grafo.getEdgeWeight(e);
			
			if(peso>max && this.numShare.get(vicino)==0) { // se ho un arco di buon peso che ha il vertice 'vicino' che non sta condividentp
				max = peso;
				best = vicino;
			}
		}
		return best;
	}

	public Map<NTA, Integer> getNumShareTot() {
		return numShareTot;
	}
	
	
}
