package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;

class Main{
    public static void main(String[] args){
        Graph G = new Graph("graphs/graph1");
        CycleMatroid CM = new CycleMatroid(G);
        EvenMatroid EM = new EvenMatroid(G);
        CM.visualizeMatroids("cycleMatroids1.md", false);
        EM.createMatroid();
        
        Graph G2 = new Graph("graphs/petersen");
        CycleMatroid CM2 = new CycleMatroid(G2);
        EvenMatroid EM2 = new EvenMatroid(G2);
        CM2.visualizeMatroids("cycleMatroids2.md", false);
        EM2.createMatroid();
    }
}
