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
    public void findCycles(){
        for(int i = 0; i < G.edgeSize(); ++i){
            //System.out.println("==NEW ONE " + i);
            ArrayList<Edge> edges = new ArrayList<Edge>();
            edges.add(G.getEdge(i));
            recursiveFindCycles(G.getEdge(i),edges, G.getEdge(i).getFrom(), G.getEdge(i).getFrom());
        }
        for(ArrayList<Edge> cycle : cycles){
            System.out.print("Cyklus:");
            for(Edge e : cycle){
                System.out.print(e);
            }
            System.out.println();
        }
    }
    private void recursiveFindCycles(Edge edge, ArrayList<Edge> edges, Vertex start, Vertex from){
        Vertex v = edge.getSecondVertex(from);
        //System.out.println("By " + edge + " CURRENTLY AT " + v);
        if(v == start){
            cycles.add(edges);
            return;
        }
        for(Edge e : v){
            if(e == edge){
                continue;
            }
            //System.out.println("Trying EDGE " + e);
            if(!edges.contains(e)){
                //System.out.println("HIT");
                ArrayList<Edge> newEdges = new ArrayList<Edge>();
                //System.out.print("Found Path: ");
                for(Edge ecopy : edges){
                    newEdges.add(ecopy);
                    //System.out.print(ecopy);
                }
                //System.out.println();
                newEdges.add(e);
                recursiveFindCycles(e, newEdges, start, v);  
            }
        }
    }
}
