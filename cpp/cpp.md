# Chinese Postman Problem

## Conditions

1. Go through each street to deliver mail.
2. Start and end at the post office.
3. Try to finish asap.

## Problem

You have

1. map of the neighbourhood
2. total metres shown

How can you minimaze the distance travelled and
deliver all the mails efficiently?

**Kwan Mei-Ko** proposed this problem in 1960.

## Definition

**The Chinese Postman Problem** is about finding
the minimum length closed walk that traverses
each edge at least once.

This problem is also knows as the route inspection
problem.

**A walk** is a path in which edges may be repeated.

**A closed walk** is a walk that starts and finishes at
the same vertex.

## Eulerian

The ideal scenario for a postman would be to
traverse each edge exactly once and finish
at the starting vertex. We would have what
is known as **an Eulerian graph**.

How do we figure out that the graph is Eulerian?
If all vertices have even degree, then the graph is
Eulerian.

## Terminology

**An Euler path** is a path that uses every edge of a graph 
exactly once.
**An Euler circuit** is a circuit that uses every edge
of a graph exactly once.
**An Euler circuit** starts and ends at the same vertex.
**An Euler path** starts and ends at different vertices.

**Semi-Eulerian** 2 vertices have odd degree.
Quick fix: we add extra edge between them.

## Steps to the algorithm

1) Find all the odd vertices in the graph.

2) Find all the possible ways to pair up these odd
vertices.

3) For each odd vertex pairing, find the shortest
path connecting them.

4) Determine the combination of pairings that has
the shortest total length.

5) Add the combination of pairings found
previously as edges to the original graph.

6) The optimal Chinese postman route is the sum of
all edges in the new graph.

## Applications

The Chinese postman problem is relevant to any situation
that requires finding the shortest possible route that
passes through all points in a network or graph.

1. Road and network planning
2. Flight paths
3. Electrical grid maintenance
4. DNA sequencing
5. Chemistry

## Generalization - Undirected solution and T-joins

The undirected route inspection problem can be solved
in **polynomial time**.
The algorithm for this problem is based on the concept
of a $T$-join.
**A $T$-join** is an edge set $J$ such that odd incident edges
of vertices form set $T$.
$T$-join exists only if every connected component of
the graph has an even number of vertices in $T$.
The minimum $T$-join consists of $|T|/2$ paths that join
vertices of $T$ in pairs.

**The optimal solution for a minimum $T$-join** can be found
by constructing a complete graph on $T$ vertices and finding
a minimum weight perfect matching.
For the route inspection problem, $T$ should be all odd-degree
vertices.
The whole graph is connected and has an even number of
odd vertices, so a $T$-join always exists.
Doubling the edges of a $T$-join creates an Eulerian multigraph.
**An Euler tour of the multigraph** is an optimal solution
to the route inspection problem.

## Generalization - Directed solution

On a directed graph, the same general ideas apply, but
different techniques must be used.
If the directed graph is Eulerian, finding an Euler cycle
is enough.
If not Eulerian, finding $T$-joins is necessary, which
requires finding paths between vertices with
in/out-degree imbalances.
Solving this requires a minimum-cost flow problem with
supply and demand units equal to excess in/out-degree.
This can be solved in **$O(|V|2|E|)$ time**.
A solution exists only if the graph is strongly connected.
