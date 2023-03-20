# Solving the Chinese postman problem with the use of matroid intersection algorithm

[The Chinese postman problem](https://en.wikipedia.org/wiki/Chinese_postman_problem) (CPP) is a classic problem in graph theory that asks for the shortest closed walk in a graph that visits every edge at least once. Two ways to solve it: reducing it to a problem of finding

1. minimal t-join (lecture this Friday, probably Greedy algorithm)
2. maximum-weight Eulerian subgraph of the original graph

The problem of finding a maximum-weight Eulerian subgraph of G is dual to the problem of finding a minimal t-join in G.

There are several matroid algorithms that can be used to solve the Chinese postman problem. Two possible options and how to use them for finding maximum-weight
Eulerian subgraph:

1. Greedy algorithm: This algorithm can be used to find a maximum-weight Eulerian subgraph of the graph by greedily adding edges to form an Eulerian subgraph. To use this algorithm, we can define a weight function on the edges of the graph and sort them in non-increasing order of weight. We can then start with an empty subgraph and iteratively add edges to it in the sorted order, as long as adding an edge does not create an odd-length cycle. The resulting subgraph is an Eulerian subgraph of the graph, and it has maximum weight among all Eulerian subgraphs.

2. **Matroid intersection algorithm:** This algorithm can be used to find a maximum-weight Eulerian subgraph of the graph by **finding a maximum-weight common independent set of two matroids**. To use this algorithm, we can define two matroids M1 and M2 on the edges of the graph as follows: M1 is the graphic matroid, which includes all edges of the graph. M2 is the matroid defined by the cycles of the graph, where a cycle is independent if it does not contain any odd-length cycle. We can then find a maximum-weight common independent set of M1 and M2 using the matroid intersection algorithm. The resulting set of edges forms a maximum-weight Eulerian subgraph of the graph.

Both of these algorithms have polynomial time complexity, and they can be mplemented efficiently using modern optimization software libraries like [Gurobi](https://www.gurobi.com/) or [CPLEX](https://www.ibm.com/products/ilog-cplex-optimization-studio).

### **Project suggestion:**

showing how CPP can be solved by reducing it to the problem of finding a maximum-
weight Eulerian subgraph with the use of matroid intersection algorithm, then
acknowledge that it is dual to the t-join problem which we had on a lecture and how
it corresponds.
