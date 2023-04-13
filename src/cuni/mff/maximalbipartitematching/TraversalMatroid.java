package cuni.mff.maximalbipartitematching;

import cz.cuni.mff.java.graphs.*;
import java.io.*;
import java.util.*;

class TraversalMatroid{
	private ArrayList<HashSet<Edge>> independentSets;
	private Graph G;
	private HashSet<Vertex> Vi;
	public TraversalMatroid(Graph G, HashSet<Vertex> Vi){
		this.G = G;
		independentSets = new ArrayList<HashSet<Edge>>();
		this.Vi = Vi;
	}
	public void createIndependentSets(String fileOutput){
		HashSet<Edge> set = new HashSet<Edge>();
		HashSet<Vertex> added = new HashSet<Vertex>();
		for(Vertex v : Vi){
			added.add(v);
			for(Edge e : v){
				set.add(e);
				createIndependentSetsRec(set, added);
				set.remove(e);
			}
			added.remove(v);
		}
		printIndependentSets(fileOutput);
	}
	public ArrayList<HashSet<Edge>> getSets(){
		return independentSets;
	}
	private void createIndependentSetsRec(HashSet<Edge> set, HashSet<Vertex> added){
		for(Vertex v : Vi){
			if(added.contains(v)){
				continue;
			}
			for(Edge e : v){
				added.add(v);
				set.add(e);
				createIndependentSetsRec(set, added);
				added.remove(v);
				set.remove(e);
			}
		}
		HashSet<Edge> I = new HashSet<Edge>();
		for(Edge e : set){
			I.add(e);
		}
		if(I.size() > 0){
			independentSets.add(I);
		}
	}
	public void printMaxIndependentSet(BufferedWriter out) throws IOException{
		independentSets.sort((I1, I2) -> I1.size() - I2.size());
		for(Vertex v : Vi){
			v.setValue(0);
		}
		boolean[] dotted = new boolean[G.edgeSize()];
		for(int i = 0; i < G.edgeSize(); ++i){
			dotted[i] = true;
		}
		for(Edge e : independentSets.get(independentSets.size() - 1)){
			dotted[e.getId()] = false;
		}
		G.exportMermaid(out, true, dotted);
		for(Vertex v : Vi){
			v.setValue(Double.NaN);
		}
	}
	private void printIndependentSets(String fileOutput){
		independentSets.sort((I1, I2) -> I1.size() - I2.size());
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fileOutput))){
			out.write("# Original graph\n\n");
			G.exportMermaid(out, true);
			for(Vertex v : Vi){
				v.setValue(0);
			}
			out.write("## Independent sets\n\n");
			boolean[] dotted = new boolean[G.edgeSize()];
			for(HashSet<Edge> set : independentSets){
				for(int i = 0; i < G.edgeSize(); ++i){
					dotted[i] = true;
				}
				for(Edge e : set){
					dotted[e.getId()] = false;
				}
				G.exportMermaid(out, true, dotted);
			}
			for(Vertex v : Vi){
				v.setValue(Double.NaN);
			}
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
}
