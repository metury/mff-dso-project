package cz.cuni.mff.opt.cpp;

import java.util.*;
import cz.cuni.mff.java.graphs.*;

class CycleMatroid{
    private Graph G;
    private ArrayList<ArrayList<Edge>> cycles;
    public CycleMatroid(Graph G){
        this.G = G;
        cycles = new ArrayList<ArrayList<Edge>>();
    }
    public void printCycles(){
        for(ArrayList<Edge> cycle : cycles){
            System.out.print("Cyklus:");
            for(Edge e : cycle){
                System.out.print(e);
            }
            System.out.println(" With sum: " + getValueOfCycle(cycle));
        }
    }
    private double getValueOfCycle(ArrayList<Edge> cycle){
        double sum = 0;
        for(Edge e : cycle){
            sum += e.getValue();
        }
        return sum;
    }
    public void findCycles(){
        ArrayList<Edge> forbidden = new ArrayList<Edge>();
        for(int i = 0; i < G.edgeSize(); ++i){
            ArrayList<Edge> edges = new ArrayList<Edge>();
            edges.add(G.getEdge(i));
            recursiveFindCycles(G.getEdge(i),edges, G.getEdge(i).getFrom(), G.getEdge(i).getFrom(), forbidden);
            forbidden.add(G.getEdge(i));
        }
    }
    private void recursiveFindCycles(Edge edge, ArrayList<Edge> edges, Vertex start, Vertex from, ArrayList<Edge> forbidden){
        Vertex v = edge.getSecondVertex(from);
        if(v == start){
            cycles.add(edges);
            return;
        }
        for(Edge e : v){
            if(e == edge || forbidden.contains(e)){
                continue;
            }
            if(!edges.contains(e)){
                ArrayList<Edge> newEdges = new ArrayList<Edge>();
                for(Edge ecopy : edges){
                    newEdges.add(ecopy);
                }
                newEdges.add(e);
                recursiveFindCycles(e, newEdges, start, v, forbidden);  
            }
        }
    }
}
