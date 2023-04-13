package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class for even subgrpah matroid.
 */
class EvenMatroid{
    /**
     * Used graph.
     */
    private Graph G;
    /**
     * Found matroids.
     */
    private ArrayList<HashSet<Edge>> matroids;
    /**
     * Default constructor.
     * @param G Given graph.
     */
    public EvenMatroid(Graph G){
        this.G = G;
        matroids = new ArrayList<HashSet<Edge>>();
    }
    /**
     * Find all matroids.
     * @return List of edge sets representing matroids.
     */
    public ArrayList<HashSet<Edge>> findEvenSubgraphs(){
        createMatroids();
        return matroids;
    }
    /**
     * Create all matroids recursively.
     */
    private void createMatroids(){
        HashSet<Edge> forbidden = new HashSet<Edge>();
        matroids.add(new HashSet<Edge>());
        for(int i = 0; i < G.edgeSize(); ++i){
            HashSet<Vertex> oddVertices = new HashSet<Vertex>();
            HashSet<Edge> matroid = new HashSet<Edge>();
            matroid.add(G.getEdge(i));
            oddVertices.add(G.getEdge(i).getFrom());
            oddVertices.add(G.getEdge(i).getTo());
            forbidden.add(G.getEdge(i));
            recursiveCreateMatroid(forbidden, oddVertices, matroid);
        }
    }
    /**
     * Recursive call on creating matroids.
     * @param forbidden Which edges cannot be used anymore.
     * @param oddVertices Which vertices have odd degree.
     * @param matroid Currently semi constructed matroid.
     */
    private void recursiveCreateMatroid(HashSet<Edge> forbidden, HashSet<Vertex> oddVertices, HashSet<Edge> matroid){
        if(oddVertices.isEmpty()){
            matroids.add(matroid);
        }
        HashSet<Edge> newForbidden = new HashSet<Edge>();
        for(Edge e : forbidden){
            newForbidden.add(e);
        }
        for(int i = 0; i < G.edgeSize(); ++i){
            Edge edge = G.getEdge(i);
            if(!forbidden.contains(edge) && !matroid.contains(edge)){
                newForbidden.add(edge);
                HashSet<Edge> newMatroid = new HashSet<Edge>();
                HashSet<Vertex> newOddVertices = new HashSet<Vertex>();
                for(Vertex v : oddVertices){
                    newOddVertices.add(v);
                }
                for(Edge e : matroid){
                    newMatroid.add(e);
                }
                newMatroid.add(edge);
                if(newOddVertices.contains(edge.getTo())){
                    newOddVertices.remove(edge.getTo());
                }
                else{
                    newOddVertices.add(edge.getTo());
                }
                if(newOddVertices.contains(edge.getFrom())){
                    newOddVertices.remove(edge.getFrom());
                }
                else{
                    newOddVertices.add(edge.getFrom());
                }
                recursiveCreateMatroid(newForbidden, newOddVertices, newMatroid);
            }
        }
    }
}
