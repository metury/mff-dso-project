package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;

class Main{
	public static void main(String[] args){
		Graph G = new Graph("graphs/graph1");
		G.exportMermaidMd("graph.md");
	}
}
