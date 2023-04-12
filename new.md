## Defining matroids ##
A finite **matroid $\mathcal{M}$** is structure - pairing $(X, I)$ where $X$ is a finite set called ground set and $I$ is a family of subsets of $X$ called the independent set satisfying three axioms:
- $(I_{1})$ The empty set is independent, i.e., $\emptyset \in I$
- $(I_{2})$ Hereditary property: every subset of an independent set is independent.
- $(I_{3})$ Exchange Property: The exchange axiom states that any independent set can be extended by adding an element from a larger independent set.
- $(I_{3'})$ If $A$ is any subset of $X$ then all the maximal (w.r.t. inclusion) subsets $Y$ of $A$ with $Y$ ∈ $I$ have the same cardinality.

## Matroid intersection ##
In combinatorial optimization, the matroid intersection problem (defined as $\mathcal{M_1}$ ∩ $\mathcal{M_2}$ = ($X$, $I_1$ ∩ $I_2$)) is to find a largest common independent set in two matroids over the same ground set. If the elements of the matroid are assigned real weights, the weighted matroid intersection problem is to find a common independent set with the maximum possible weight. 
That's why we distinguish:
- the **cardinality intersection problem**, in which we seek an intersection containing a maximum number of elements,
- the **weighted intersection problem**, in which we seek an intersection of maximum total weight, with respect to a given weighting of the elements. [1]

The idea of the matroid intersection algorithm was first proposed by mathematicians Jack Edmonds and Richard M. Karp. In 1972 they introduced the concept of matroid intersection and showed that it can be used to solve a wide range of combinatorial optimization problems, 
including network flow, shortest path problems, finding maximum matchings and maximum weight matchings in bipartite graphs, which will be our topic today.



## Transversal matroids ##

## Maximum matching in bipartite graphs ##
Recall that a matching in a graph is a set of edges such that no two of them share a vertex. 
The problem of constructing a maximum-size matching in a bipartite graph can be formulated using matroids as follows: 
let $G$ be a bipartite graph with no isolated vertices with parts $V_1$ and $V_2$ and edge set $E$. We define two matroids $\mathcal{M_1}$ and $\mathcal{M_2}$ with
the ground set E. [2]

G = ($V_1$ ∪ $V_2$, $E$ ⊆ $V_1$ × $V_2$) can be written as matroid intersection using
- $I_1$ = { $F$ ⊆ $E$ | ∀ $v$ ∈ $V_1$ : | $F$ ∩ δ($v$)| ≤ 1 }
- $I_2$ = { $F$ ⊆ $E$ | ∀ $v$ ∈ $V_2$ : | $F$ ∩ δ($v$)| ≤ 1 } [3]

Here, $I_i$ are all edge collections such that each node in $V_i$ has at most one incident edge. 
Hence, the problem of constructing a maximum-size matching can be formulated as the problem of constructing a maximum-size intersection $I_1$ ∩ $I_2$ independent in two given matroids with the same ground set.
The matroids $\mathcal{M_1}$ and $\mathcal{M_2}$ are transversal matroids. Observe that if $G$ has no isolated vertices, then the rank of $\mathcal{M_1}$ is | $V_1$ | and the rank of $\mathcal{M_2}$ is | $V_2$ |.



## Resources ##
[[1] Lawler, Eugene L.: Matroid intersection algorithms](https://link.springer.com/article/10.1007/BF01681329)

[[2] RNDr. Ondřej Pangrác, Ph.D.: Matroid intersection problem](https://iuuk.mff.cuni.cz/~pangrac/vyuka/matroids/matroid-ch3.pdf)

[[3] Jan Vondrák: Polyhedral techniques in combinatorial optimization](https://theory.stanford.edu/~jvondrak/CS369P/lec10.pdf)
<!--- Vondrák completed a bachelor's degree in Physics (1995) and a M.S. (1999) and Ph.D. (2007) in Computer Science at Charles University under advisor Martin Loebl. -->
