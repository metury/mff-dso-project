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
    public static String CYCLE_MATROID_HEADER = "## Cycle Matroids";
    /**
     * String which will be print to output file as a header for even subset subgraphs.
     */
    public static String EVEN_SUBGRAPH_MATROID_HEADER = "## Even subgraph matroids";
    /**
     * String which will be print to ouptu file as a header for result of intersection algorithm.
     */
    public static String ALG_RESULT = "## Result of the intersection algorithm";
    /**
     * Main function.
     * @param args Program arguments.
     */
    public static void main(String[] args){
        Graph G = new Graph("graphs/simple");
        CPP(G, "output/Simple.md");
        Graph G2 = new Graph("graphs/petersen");
        CPP(G2, "output/Petersen.md");
        Graph G3 = new Graph("graphs/example");
        CPP(G3, "output/Example.md");
        Graph G4 = new Graph("graphs/special");
        CPP(G4, "output/Special.md");
    }
    private static void CPP(Graph G, String outputFile){
        try(BufferedWriter out = new BufferedWriter(new FileWriter(outputFile, false))){
            // Print details.
            out.write("# Chinese postman problem\n\n");
            out.write("On input we are given graph $G$ and we have to find the solution to the *Chinese postman problem*. Given graph is:\n\n");
            G.exportMermaid(out, true);
            out.write("Now we will find all **Cycle subgraphs matroids** and **Even subgraph matroids** and use them to use the intersection of matroids to solve the problem.");
            out.write("Because the number of all possible matroids is way too many, we will show only the maximal of each.\n\n");
            
            // Use the algorithm for intersection.
            HashSet<Edge> maximalSet = intersectionOfMatroids(G, out);
            
            // Get rid of the edges from the result.
            HashSet<Edge> allEdges = new HashSet<Edge>();
            for(int i = 0; i < G.edgeSize(); ++i){
                allEdges.add(G.getEdge(i));
            }
            allEdges.removeAll(maximalSet);
            Graph H = G.clone();
            for(Edge e : allEdges){
                H.addEdge(e.getValue(), e.getFrom().getId(), e.getTo().getId());
            }
            
            // Print details.
            out.write("To solve the *Chinese Postman problem* we duplicate the edges not in the matroid intersection and get the multigraph $H$:\n\n");
            H.exportMermaid(out, true);
            out.write("Now we are garanteed to be able to find an euler path. This euler path is the shortest solution to the *Chinese postman problem*.");
            out.write("We won't be showing the euler path itself because it is not that hard to find it.");
            double numberResult = 0;
            for(int i = 0; i < H.edgeSize(); ++i){
                numberResult += H.getEdge(i).getValue();
            }
            out.write("\n\nFor those who may be interested in number result, it is: `" + numberResult + "`.\n");
        } catch(IOException ioe){
            System.err.println(ioe);
        }
    }
    /**
     * Use intersection algorithm.
     * @param G Given graph.
     * @param cycleFilePath Path to the output file for cycle matroid.
     * @param evenFilePath Path to the output file for even subgraph matroid.
     * @param resultPath Path to the output file for the result.
     * @return Result of the intersection algorithm.
     */
    private static HashSet<Edge> intersectionOfMatroids(Graph G, BufferedWriter out) throws IOException{
        CycleMatroid CM = new CycleMatroid(G);
        EvenMatroid EM = new EvenMatroid(G);
        
        ArrayList<HashSet<Edge>> I1 = EM.findEvenSubgraphs();
        ArrayList<HashSet<Edge>> I2 = CM.findCycleSubgraphs();
        
        HashSet<Edge> B1 = findMaximalIndependentSet(I1);
        HashSet<Edge> B2 = findMaximalIndependentSet(I2);
        
        visualizeMatroid(G, B1, out, CYCLE_MATROID_HEADER);
        visualizeMatroid(G, B2, out, EVEN_SUBGRAPH_MATROID_HEADER);
        
        HashSet<Edge> result = maximalComonIndependentSet(I1, B1, I2, B2);
        
        out.write("By using the matroid intersection algorithm we get this following result:\n\n");
        visualizeMatroid(G, result, out, ALG_RESULT);
        return result;
    }
    /**
     * Find maximal comon independetn set in all matroids by given algorithm recursively
     * @param I1 Group of matroids.
     * @param B1 Maximal matroid of I1.
     * @param I2 Group of matroids.
     * @param B2 Maximal matroid of I2.
     * @return Maximal intersection of matroids.
     */
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
    public static void visualizeMatroids(Graph G, ArrayList<HashSet<Edge>> matroids, String header, BufferedWriter out) throws IOException{
        HashSet<Edge> max = findMaximalIndependentSet(matroids);
        out.write(header + "\n\n");
        out.write("On input we have following graph $G$:\n\n");
        G.exportMermaid(out, true);
        int index = 1;
        for(HashSet<Edge> matroid : matroids){
            out.write("### Matroid Nr." + index++ + "\n\n");
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
    }
    /**
     * Visualize one matroid (usually the result) to one file.
     * @param G Given graph.
     * @param matroid Which matroid to show.
     * @param filePath Path to the output file.
     * @param append If the file should be appended or not.
     */
    public static void visualizeMatroid(Graph G, HashSet<Edge> matroid, BufferedWriter out, String header) throws IOException{
        out.write(header + "\n\n");
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
}
