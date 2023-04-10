package cz.cuni.mff.opt.cpp;

import java.util.*;
import cz.cuni.mff.java.graphs.*;

class CycleMatroid{
    private Graph G;
    private ArrayList<HashSet<Edge>> cycles;
    private ArrayList<ArrayList<HashSet<Edge>>> matroids;
    public CycleMatroid(Graph G){
        this.G = G;
        cycles = new ArrayList<HashSet<Edge>>();
        matroids = new ArrayList<ArrayList<HashSet<Edge>>>();   
    }
    public void printMatroids(){
        for(ArrayList<HashSet<Edge>> matroid : matroids){
            System.out.println("MATROID:");
            for(HashSet<Edge> cycle : matroid){
                System.out.print(" - Cycle: ");
                for(Edge e : cycle){
                    System.out.print(e);
                }
                System.out.println();
            }
        }
    }
    public void combineCycles(){
        ArrayList<HashSet<Edge>> forbidden = new ArrayList<HashSet<Edge>>();
        for(HashSet<Edge> cycle : cycles){
            forbidden.add(cycle);
            HashSet<Edge> used = new HashSet<Edge>();
            for(Edge e : cycle){
                used.add(e);
            }
            ArrayList<HashSet<Edge>> matroid = new ArrayList<HashSet<Edge>>();
            matroid.add(cycle);
            recursiveCombineCycles(forbidden, matroid, used);
        }
    }
    private void recursiveCombineCycles(ArrayList<HashSet<Edge>> forbidden, ArrayList<HashSet<Edge>> matroid, HashSet<Edge> used){
        boolean found = true;
        MAINLOOP: for(HashSet<Edge> cycle : cycles){
            if(forbidden.contains(cycle)){
                continue;
            }
            HashSet<Edge> newUsed = new HashSet<Edge>();
            for(Edge e : cycle){
                if(used.contains(e)){
                    continue MAINLOOP;
                }
                newUsed.add(e);
            }
            found = false;
            ArrayList<HashSet<Edge>> newMatroid = new ArrayList<HashSet<Edge>>();
            for(HashSet<Edge> c : matroid){
                newMatroid.add(c);
            }
            newMatroid.add(cycle);
            for(Edge usedE : used){
                newUsed.add(usedE);
            }
            recursiveCombineCycles(forbidden, newMatroid, newUsed);
        }
        if(found){
            matroids.add(matroid);
        }
    }
    public void printCycles(){
        for(HashSet<Edge> cycle : cycles){
            System.out.print("Cyklus:");
            for(Edge e : cycle){
                System.out.print(e);
            }
            System.out.println(" With sum: " + getValueOfCycle(cycle));
        }
    }
    private double getValueOfCycle(HashSet<Edge> cycle){
        double sum = 0;
        for(Edge e : cycle){
            sum += e.getValue();
        }
        return sum;
    }
    public void findCycles(){
        HashSet<Edge> forbidden = new HashSet<Edge>();
        for(int i = 0; i < G.edgeSize(); ++i){
            HashSet<Edge> edges = new HashSet<Edge>();
            edges.add(G.getEdge(i));
            recursiveFindCycles(G.getEdge(i),edges, G.getEdge(i).getFrom(), G.getEdge(i).getFrom(), forbidden);
            forbidden.add(G.getEdge(i));
        }
    }
    private void recursiveFindCycles(Edge edge, HashSet<Edge> edges, Vertex start, Vertex from, HashSet<Edge> forbidden){
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
                HashSet<Edge> newEdges = new HashSet<Edge>();
                for(Edge ecopy : edges){
                    newEdges.add(ecopy);
                }
                newEdges.add(e);
                recursiveFindCycles(e, newEdges, start, v, forbidden);  
            }
        }
    }
}
