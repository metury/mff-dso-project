package cz.cuni.mff.opt.cpp;

import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;
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
    public void visualizeMatroids(String filePath, boolean append){
        matroids.add(new ArrayList<HashSet<Edge>>());
        findCycles();
        combineCycles();
        try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
            out.write("# Cycle Matroids\n\n");
            out.write("On input we have following graph $G$:\n\n");
            G.exportMermaid(out, true);
            out.write("By this non-so efficient way we find all cycles and then try to combine as most of the cycles together. Then we get following so called **cycle matroids**.\n\n");
            int index = 1;
            ArrayList<HashSet<Edge>> max = getMaxmatroid();
            for(ArrayList<HashSet<Edge>> matroid : matroids){
                out.write("## Matroid Nr." + index++ + "\n\n");
                if(max == matroid){
                    out.write("**This matroid is maximal with respect to the edge values.**\n\n");
                }
                boolean [] unused = new boolean[G.edgeSize()];
                for(int i = 0; i < unused.length; ++i){
                    unused[i] = true;
                }
                for(HashSet<Edge> cycle : matroid){
                    for(Edge e : cycle){
                        unused[e.getId()] = false;
                    }
                }
                G.exportMermaid(out, true, unused);
                out.write("This matroid has a value: `" + getSumOfMatroid(matroid) + "`.\n\n");
            }
        } catch(IOException ioe){
            System.err.println(ioe);
        }
    }
    private ArrayList<HashSet<Edge>> getMaxmatroid(){
        double sum = 0;
        ArrayList<HashSet<Edge>> max = new ArrayList<HashSet<Edge>>();
        for(ArrayList<HashSet<Edge>> matroid : matroids){
            double matroidSum = getSumOfMatroid(matroid);
            if(matroidSum > sum){
                sum = matroidSum;
                max = matroid;
            }
        }
        return max;
    }
    private double getSumOfMatroid(ArrayList<HashSet<Edge>> matroid){
        double sum = 0;
        for(HashSet<Edge> cycle : matroid){
            sum += getSumOfCycle(cycle);
        }
        return sum;
    }
    private void combineCycles(){
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
    private double getSumOfCycle(HashSet<Edge> cycle){
        double sum = 0;
        for(Edge e : cycle){
            sum += e.getValue();
        }
        return sum;
    }
    private void findCycles(){
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
