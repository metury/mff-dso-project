package cz.cuni.mff.opt.cpp;

import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;
import cz.cuni.mff.java.graphs.*;

/**
 * Class for cycle subgraph matroids.
 */
class CycleMatroid{
    /**
     * Given graph.
     */
    private Graph G;
    /**
     * ALl cycles in the graph.
     */
    private ArrayList<HashSet<Edge>> cycles;
    /**
     * All matroids in the graph. Represented as a list of cycle lists. Where cycle is a set of edges.
     */
    private ArrayList<ArrayList<HashSet<Edge>>> matroids;
    /**
     * Default constructor.
     * @param G Given graph.
     */
    public CycleMatroid(Graph G){
        this.G = G;
        cycles = new ArrayList<HashSet<Edge>>();
        matroids = new ArrayList<ArrayList<HashSet<Edge>>>();   
    }
    /**
     * Find all cycle subgraph matroids.
     * @return List of sets of edges representing matroids.
     */
    public ArrayList<HashSet<Edge>> findCycleSubgraphs(){
        createMatroids();
        ArrayList<HashSet<Edge>> ret = new ArrayList<HashSet<Edge>>();
        for(ArrayList<HashSet<Edge>> matroid : matroids){
            HashSet<Edge> newMatroid = new HashSet<Edge>();
            for(HashSet<Edge> cycle : matroid){
                for(Edge e : cycle){
                    newMatroid.add(e);
                }
            }
            ret.add(newMatroid);
        }
        return ret;
    }
    /**
     * Recursively create all matroids. Firstly by creating all cycles.
     */
    private void createMatroids(){
        matroids.add(new ArrayList<HashSet<Edge>>());
        findCycles();
        ArrayList<HashSet<Edge>> forbidden = new ArrayList<HashSet<Edge>>();
        for(HashSet<Edge> cycle : cycles){
            forbidden.add(cycle);
            HashSet<Edge> used = new HashSet<Edge>();
            for(Edge e : cycle){
                used.add(e);
            }
            ArrayList<HashSet<Edge>> matroid = new ArrayList<HashSet<Edge>>();
            matroid.add(cycle);
            recursiveCreateMatroids(forbidden, matroid, used);
        }
    }
    /**
     * Recusrive call on creating matroids.
     * @param forbidden Which cycles can't be used.
     * @param matroid Currently semi constructed matroid.
     * @param used Which edges have been already used in used cycles.
     */
    private void recursiveCreateMatroids(ArrayList<HashSet<Edge>> forbidden, ArrayList<HashSet<Edge>> matroid, HashSet<Edge> used){
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
            recursiveCreateMatroids(forbidden, newMatroid, newUsed);
        }
        if(found){
            matroids.add(matroid);
        }
    }
    /**
     * Recursively find all cycles in graph.
     */
    private void findCycles(){
        HashSet<Edge> forbidden = new HashSet<Edge>();
        for(int i = 0; i < G.edgeSize(); ++i){
            HashSet<Edge> edges = new HashSet<Edge>();
            edges.add(G.getEdge(i));
            recursiveFindCycles(G.getEdge(i),edges, G.getEdge(i).getFrom(), G.getEdge(i).getFrom(), forbidden);
            forbidden.add(G.getEdge(i));
        }
    }
    /**
     * Recursive call to find cycles.
     * @param edge Last used edge.
     * @param edges Currently used semi build cycle.
     * @param start Begining of the cycles, thus the end as well.
     * @param from Vertex on the edge we use as start.
     * @param forbidden Which edges cannot be used anymore.
     */
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
