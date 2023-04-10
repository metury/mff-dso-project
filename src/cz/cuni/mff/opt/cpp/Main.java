package cz.cuni.mff.opt.cpp;

import cz.cuni.mff.java.graphs.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Main class.
 */
class Main{
    /**
     * String which will be print to output file as a header for cycle subgraphs.
     */
    public static String CYCLE_MATROID_HEADER = "# Cycle Matroids";
    /**
     * String which will be print to output file as a header for even subset subgraphs.
     */
    public static String EVEN_SUBGRAPH_MATROID_HEADER = "# Even subgraph matroids";
    /**
     * Main function.
     * @param args Program arguments.
     */
    public static void main(String[] args){
        Graph G = new Graph("graphs/graph1");
        intersectionOfMatroids(G, "output/cycleMatroidsSimple.md", "output/evenSubMatroidsSimple.md", "output/resultSimple.md");
        Graph G2 = new Graph("graphs/petersen");
        intersectionOfMatroids(G2, "output/cycleMatroidsPetersen.md", "output/evenSubMatroidsPetersen.md", "output/resultPetersen.md");
        Graph G3 = new Graph("graphs/example");
        intersectionOfMatroids(G3, "output/cycleMatroidsExample.md", "output/evenSubMatroidsExample.md", "output/resultExample.md");
        
    }
    private static void CPP(Graph G){
        
    }
    private static void intersectionOfMatroids(Graph G, String cycleFilePath, String evenFilePath, String resultPath){
        CycleMatroid CM = new CycleMatroid(G);
        EvenMatroid EM = new EvenMatroid(G);
        
        ArrayList<HashSet<Edge>> I1 = EM.findEvenSubgraphs();
        ArrayList<HashSet<Edge>> I2 = CM.findCycleSubgraphs();
        visualizeMatroids(G, I1, CYCLE_MATROID_HEADER, cycleFilePath, false);
        visualizeMatroids(G, I2, EVEN_SUBGRAPH_MATROID_HEADER, evenFilePath, false);
               
        HashSet<Edge> B1 = findMaximalIndependentSet(I1);
        HashSet<Edge> B2 = findMaximalIndependentSet(I2);
        HashSet<Edge> result = maximalComonIndependentSet(I1, B1, I2, B2);
        visualizeMatroid(G, result, resultPath, false);
    }
    private static HashSet<Edge> maximalComonIndependentSet(ArrayList<HashSet<Edge>> I1, HashSet<Edge> B1, ArrayList<HashSet<Edge>> I2, HashSet<Edge> B2){
        HashSet<Edge> P = new HashSet<Edge>();
        
        while(!B1.isEmpty()){
            Edge[] edges = B1.toArray(new Edge[B1.size()]);
            Edge e = edges[0];
                if(B2.contains(e)){
                    P.add(e);
                    B2.remove(e);
                    B1.remove(e);
                }
                else{
                    ArrayList<HashSet<Edge>> I1d = new ArrayList<HashSet<Edge>>();
                    ArrayList<HashSet<Edge>> I2d = new ArrayList<HashSet<Edge>>();
                    for(HashSet<Edge> hs : I1){
                        I1d.add(hs);
                    }
                    for(HashSet<Edge> hs : I2){
                        I2d.add(hs);
                    }
                    I1d.remove(B1);
                    I2d.remove(B2);
                    HashSet<Edge> B1d = findMaximalIndependentSet(I1d);
                    HashSet<Edge> B2d = findMaximalIndependentSet(I2d);
                    HashSet<Edge> firstResult = maximalComonIndependentSet(I1, B1, I2, B2d);
                    HashSet<Edge> secondResult = maximalComonIndependentSet(I1, B1d, I2, B2);
                    if(getSumOfMatroid(firstResult) > getSumOfMatroid(secondResult)){
                        for(Edge edge : firstResult){
                            P.add(edge);
                        }
                    }
                    else{
                        for(Edge edge : secondResult){
                            P.add(edge);
                        }
                    }
                
            }
        }
        return P;
    }
    /**
     * Get the maximal independent set from all matroids.
     * @param matroids All matroids.
     * @return Set of edges as the maximal independent set.
     */
    private static HashSet<Edge> findMaximalIndependentSet(ArrayList<HashSet<Edge>> matroids){
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
    /**
     * Get the sum of all values of a matroid.
     * @param matroid Which matroid we are counting.
     * @return Sum of all values of all edges.
     */
    private static double getSumOfMatroid(HashSet<Edge> matroid){
        double sum = 0;
        for(Edge e : matroid){
            sum += e.getValue();
        }
        return sum;
    }
    /**
     * Visualize Matroids to an output file.
     * @param G Which graph we are using.
     * @param matroids List of all matroids.
     * @param header Header for the output file.
     * @param filePath Path to the output file.
     * @param append If the file should be appended or not.
     */
    public static void visualizeMatroids(Graph G, ArrayList<HashSet<Edge>> matroids, String header, String filePath, boolean append){
        HashSet<Edge> max = findMaximalIndependentSet(matroids);
         try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
             out.write(header + "\n\n");
             out.write("On input we have following graph $G$:\n\n");
             G.exportMermaid(out, true);
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
    public static void visualizeMatroid(Graph G, HashSet<Edge> matroid, String filePath, boolean append){
        try(BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append))){
            out.write("## Result\n\n");
            boolean [] unused = new boolean[G.edgeSize()];
            for(int i = 0; i < unused.length; ++i){
                unused[i] = true;
            }
            for(Edge e : matroid){
                unused[e.getId()] = false;
            }
            G.exportMermaid(out, true, unused);
            out.write("This matroid has a value: `" + getSumOfMatroid(matroid) + "`.\n\n");
        } catch(IOException ioe){
            System.err.println(ioe);
        }
    }
}
