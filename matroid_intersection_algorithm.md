## Defining matroids ##
A finite matroid M is pairing (X, I) where X is a finite set called ground set and I is a family of subsets of X called the independent set satisfying three axioms.

### M1: even subgraph matroid ###
We will define the first matroid (M1) as even subgraph matroid:
The ground set of the matroid is the set of all subgraphs of a given graph.
Independent set: A subset of subgraphs is independent if and only if it does not contain any odd degree vertices.

Axioms:

(I1) The empty set is independent, i.e., ∅ ∈ I. Empty set does not contain any odd degree vertices and therefore satisfies the first axiom.

(I2) Hereditary property: every subset of an independent set is independent. Specifically, if a subgraph does not contain any odd degree vertices, then any subset of that subgraph also does not contain any odd degree vertices.

(I3) Exchange Property: The exchange axiom states that any independent set can be extended by adding an element from a larger independent set. This property holds for the even subgraph matroid, since any subset of a set of subgraphs that do not contain any odd degree vertices can be extended by adding another subgraph that also does not contain any odd degree vertices.

Therefore, the even subgraph matroid satisfies the axioms of a matroid, and can be used in matroid intersection algorithm.

Independent set in even subraph matroid for graph G=(V,E):

![cycle matroid independen set examples](/cycle_matroid.png "")

### M2: cycle matroid ###
The term “cycle matroid” of a graph G = (N, E) is well-known. It is frequently used as a simple introduction to basic matroid concepts. In that introductory example, a set of edges X ⊆ E is said to be “independent” if it contains no cycles. A maximal, independent set is thus a spanning tree of G. However, it is not at all what we mean by a “cycle matroid”. The elements of graph based matroids are the edges of the graph. The ground set of our “cycle matroid” are the cycles themselves. We use nodes and edges only to help describe the individual cycles. [[John L. Pfaltz: Cycle Matroids]](https://www.cs.virginia.edu/~jlp/19.CYCLE.pdf).
An independent set of this matroid is a set of cycle subgraphs that do not share any edges.

Axioms:

(I1) The empty set is independent, i.e., ∅ ∈ I. //

(I2) Hereditary property: every subset of an independent set is independent. //

(I3) Exchange Property //

Independent set in cycle matroid for graph G=(V,E):

![even subraph matroid independen set examples](/evensub_matroid.png "")

## Matroid Intersection Algorithm ##
The matroid intersection problem is to find a largest common independent set in two matroids over the same ground set. If the elements of the matroid are assigned real weights, the weighted matroid intersection problem is to find a common independent set with the maximum possible weight.

More specifically, let M1 = (E, I1) and M2 = (E, I2) be two matroids defined over the same ground set E. Let w: E -> R be a weight function that assigns a weight to each element of E. The goal of the matroid intersection algorithm is to find an independent set I* that is a member of both I1 and I2 and has maximum total weight w(I*).

The idea of the matroid intersection algorithm was first proposed by mathematicians Jack Edmonds and Richard M. Karp. In 1972 they introduced the concept of matroid intersection and showed that it can be used to solve a wide range of combinatorial optimization problems, including network flow, matching, and shortest path problems.

Using the matroid intersection algorithm, **we want to find the edges whose duplication will be the most efficient solution to the CPP**.

![intersection](/intersection.png "")