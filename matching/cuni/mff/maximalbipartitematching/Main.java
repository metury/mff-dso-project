package cuni.mff.maximalbipartitematching;

import cz.cuni.mff.java.graphs.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.Clock;

class Main{
	public static void main(String[] args){
		showCaseAlgorithm("graphs/1", "output/1.md", "output/1(1).md", "output/1(2).md");
		showCaseAlgorithm("graphs/2", "output/2.md", "output/2(1).md", "output/2(2).md");
	}
	public static void showCaseAlgorithm(String graphInput, String fileOutput, String v1Output, String v2Output){
		Graph G = new Graph(graphInput);
		try(BufferedWriter out = new BufferedWriter(new FileWriter(fileOutput))){
			out.write("# Maximal matching in bipartite graph\n\n");
			out.write("We have following Graph $G$:\n\n");
			G.exportMermaid(out, true);
			HashSet<Vertex> V1 = new HashSet<Vertex>();
			HashSet<Vertex> V2 = new HashSet<Vertex>();
			boolean isBip = isBipartite(G, V1, V2);
			if(!isBip){
				out.write("We may already see, that the graph itself is not bipartite or there is a vertex which is left out.\n");
				return;
			}
			for(Vertex v : V1){
				v.setValue(1);
			}
			for(Vertex v : V2){
				v.setValue(2);
			}
			out.write("We may see two parts on the graph. We can visualize it by giving them indexes.\n\n");
			G.exportMermaid(out, true);
			for(Vertex v : V1){
				v.setValue(Double.NaN);
			}
			for(Vertex v : V2){
				v.setValue(Double.NaN);
			}
			out.write("Now we will find all independent sets of a transversal matroid. Because there may be a way more graphs, we will put it in a separate files.");
			out.write("First part we have [here](./" + v1Output + ") and second [here](./" + v2Output + ").\n\n" );
			out.write("## Intersection\n\n");
			out.write("Last think to show we will find the maximal independent sets from both which are the same.\n\n");
			TraversalMatroid TM1 = new TraversalMatroid(G, V1);
			TraversalMatroid TM2 = new TraversalMatroid(G, V2);
			TM1.createIndependentSets(v1Output);
			TM2.createIndependentSets(v2Output);
			HashSet<Edge> max = maximalIntersection(TM1.getSets(), TM2.getSets());
			boolean[] dotted = new boolean[G.edgeSize()];
			for(int i = 0; i < G.edgeSize(); ++i){
				dotted[i] = true;
			}
			for(Edge e : max){
				dotted[e.getId()] = false;
			}
			G.exportMermaid(out, true, dotted);
		} catch(IOException ioe){
			System.err.println(ioe);
		}
	}
	public static HashSet<Edge> maximalIntersection(ArrayList<HashSet<Edge>> I1, ArrayList<HashSet<Edge>> I2){
		I2.retainAll(I1);
		I2.sort((a, b) -> a.size() - b.size());
		return I2.get(I2.size() - 1);
	}
	public static boolean isBipartite(Graph G, HashSet<Vertex> V1, HashSet<Vertex> V2){
		if(G.vertexSize() == 0){
			return true;
		}
		HashSet<Vertex> found = new HashSet<Vertex>();
		Stack<Vertex> stackV1 = new Stack<Vertex>();
		Stack<Vertex> stackV2 = new Stack<Vertex>();
		stackV1.push(G.getVertex(0));
		while(found.size() < G.vertexSize()){
			if(stackV1.isEmpty() && stackV2.isEmpty()){
				// There are vertices that can't be reached.
				return false;
			}
			while(!stackV1.isEmpty()){
				Vertex v = stackV1.pop();
				for(Edge e : v){
					Vertex u = e.getSecondVertex(v);
					if(V1.contains(u)){
						// Vertex would be in both parts.
						return false;
					}
					if(found.contains(u)){
						continue;
					}
					V2.add(u);
					stackV2.push(u);
					found.add(u);
				}
			}
			while(!stackV2.isEmpty()){
				Vertex v = stackV2.pop();
				for(Edge e : v){
					Vertex u = e.getSecondVertex(v);
					if(V2.contains(u)){
						// Vertex would be in both parts.
						return false;
					}
					if(found.contains(u)){
						continue;
					}
					V1.add(u);
					stackV1.push(u);
					found.add(u);
				}
			}
		}
		return true;
	}
}
