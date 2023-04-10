package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

class EvenMatroid{
    private Graph G;
    private ArrayList<HashSet<Edge>> matroids;
    public EvenMatroid(Graph G){
        this.G = G;
        matroids = new ArrayList<HashSet<Edge>>();
    }
    public void visualizeMatroids(String filePath, boolean append){
        createMatroids();
        HashSet<Edge> max = getMaxMatroid();
         try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
             out.write("# Even subgraph matroids\n\n");
             out.write("On input we have following graph $G$:\n\n");
             G.exportMermaid(out, true);
             out.write("By this non-so efficient way we find all even subgraph matroids by greedily going through all combinations. Then we get so called **even subgraph matroids**.\n\n");
             int index = 1;
             for(HashSet<Edge> matroid : matroids){
                 out.write("## Matroid Nr." + index++ + "\n\n");
                 if(max == matroid){
                    out.write("**This matroid is maximal with respect to the edge values.**\n\n");
                }
                boolean [] unused = new boolean[G.edgeSize()];
                for(int i = 0; i < unused.length; ++i){
                    unused[i] = true;
                }
                for(Edge e : matroid){
                    unused[e.getId()] = false;
                }
                G.exportMermaid(out, true, unused);
                 out.write("This matroid has a value: `" + getSumOfMatroid(matroid) + "`.\n\n");
             } 
         } catch(IOException ioe){
             System.err.println(ioe);
         }
    }
    private HashSet<Edge> getMaxMatroid(){
        double sum = 0;
        HashSet<Edge> max = new HashSet<Edge>();
        for(HashSet<Edge> matroid : matroids){
            double matroidSum = getSumOfMatroid(matroid);
            if(matroidSum > sum){
                sum = matroidSum;
                max = matroid;
            }
        }
        return max;
    }
    private double getSumOfMatroid(HashSet<Edge> matroid){
        double sum = 0;
        for(Edge e : matroid){
            sum += e.getValue();
        }
        return sum;
    }
    private void createMatroids(){
        HashSet<Edge> forbidden = new HashSet<Edge>();
        for(int i = 0; i < G.edgeSize(); ++i){
            HashSet<Vertex> oddVertices = new HashSet<Vertex>();
            HashSet<Edge> matroid = new HashSet<Edge>();
            matroid.add(G.getEdge(i));
            oddVertices.add(G.getEdge(i).getFrom());
            oddVertices.add(G.getEdge(i).getTo());
            forbidden.add(G.getEdge(i));
            recursiveCreateMatroid(forbidden, oddVertices, matroid);
        }
        matroids.add(new HashSet<Edge>());
    }
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
