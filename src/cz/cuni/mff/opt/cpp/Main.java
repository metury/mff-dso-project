package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;

class Main{
	public static void main(String[] args){
		Graph G = new Graph("graphs/graph1");
		G.exportMermaidMd("graph.md");
                CycleMatroid CM = new CycleMatroid(G);
                CM.findCycles();
                System.out.println("===");
                Graph G2 = new Graph("graphs/petersen");
                G.exportMermaidMd("petersen.md");
                CycleMatroid CM2 = new CycleMatroid(G2);
                CM2.findCycles();
	}
}
