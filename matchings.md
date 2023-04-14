# Finding maximum matching in bipartite graphs with the use of matroid intersection algorithm #

## Defining matroids ##
A finite **matroid $\mathcal{M}$** is structure - pairing $(X, \varphi)$ where $X$ is a finite set called ground set and $\varphi$ is a family of subsets of $X$ called the independent set satisfying three axioms:
- $(I_{1})$ The empty set is independent, i.e., $\emptyset \in \varphi$
- $(I_{2})$ Hereditary property: every subset of an independent set is independent.
- $(I_{3})$ Exchange Property: The exchange axiom states that any independent set can be extended by adding an element from a larger independent set.
- $(I_{3'})$ If $A$ is any subset of $X$ then all the maximal (w.r.t. inclusion) subsets $Y$ of $A$ with $Y$ ∈ $\varphi$ have the same cardinality.

## Matroid intersection ##
In combinatorial optimization, the matroid intersection problem (defined as $\mathcal{M_1}$ ∩ $\mathcal{M_2}$ = ($X$, $\varphi_1$ ∩ $\varphi_2$)) is to find a largest common independent set in two matroids over the same ground set. If the elements of the matroid are assigned real weights, the weighted matroid intersection problem is to find a common independent set with the maximum possible weight. 
That's why we distinguish:
- the **cardinality intersection problem**, in which we seek an intersection containing a maximum number of elements,
- the **weighted intersection problem**, in which we seek an intersection of maximum total weight, with respect to a given weighting of the elements. [1]

There are several algorithms for matroid intersection, each running in polynomial time, for example:
- Edmonds' algorithm uses linear programming and polyhedra
- three Lawler's algorithms, all using augmenting sequences
- Cunningham's algorithm for both general and linear matroids
- many more

The idea of the matroid intersection algorithm was first proposed by mathematicians Jack Edmonds and Richard M. Karp. In 1972 they introduced the concept of matroid intersection and showed that it can be used to solve a wide range of combinatorial optimization problems, 
including network flow, shortest path problems, finding maximum matchings and maximum weight matchings in bipartite graphs, which will be our topic today.


## Transversal matroids ##

Transversal matroids are those matroids where the independent sets can be considered as the partial transversals of a family of sets. For a given transversal matroid such a family of sets is called a presentation of the matroid. Transversal matroids were discovered several years ago by Edmonds and Fulkerson, and since then there has been considerable interest in their properties.

### Formal definition
Let $C_i$ be a collection of disjoint sets("categories"). Let $d_i$ be integers with $0 \leq d_i \leq |C_i|$ ("capacities"). Define a subset $I \subset \bigcup_{i} C_i\$ to be "independent" when for every index $i$, $|I \cap C_i| \leq d_i$.  The sets satisfying this condition form the independent sets of a matroid, called **a partition matroid**.

In some publications, the notion of a partition matroid is defined more restrictively, with every $d_i = 1$. The partitions that obey this more restrictive definition are **the transversal matroids** of the family of disjoint sets given by their blocks.

**Input:** disjoint sets $X_v$, $v \in V$

$\mathcal{F} = (X, \varphi)$

$X = \bigcup_{v \in V} X_v\$

$A \in \varphi \Leftrightarrow (v \in V)(|A \cap X_v| \leq 1)$

**Claim:** $\mathcal{F}$ is a matroid.

To prove that $\mathcal{F} = (X, \varphi)$ defined as $X = \bigcup_{v \in V} X_v$ and $A \in \varphi \Leftrightarrow (v \in V)(|A \cap X_v| \leq 1)$ is a matroid, we need to show that it satisfies the three axioms of a matroid:

$(I_1)$ The empty set is independent: since $|\emptyset \cap X_v| = 0$ for all $v\in V$, it follows that $\emptyset \in \varphi$.

$(I_2)$ Hereditary property: let $A$ be an independent set and $B\subseteq A$. Then for all $v\in V$, we have $|B \cap X_v| \leq |A \cap X_v| \leq 1$. Therefore, $B$ is also independent.

$(I_3)$ Exchange property: The exchange property $(I_3)$ states that for any independent sets $A, B \in \varphi$ with $|A| < |B|$, there exists an element $x \in B\setminus A$ such that $A \cup {x} \in \varphi$.

To prove this, we first note that since $A$ and $B$ are independent, we have $|A\cap X_i|\leq 1$ and $|B\cap X_i| \leq 1$ for all $i\in V$. Also, $|A| < |B|$ implies that there exists some $i\in V$ such that $|A\cap X_i| < |B\cap X_i|$.

Let $x$ be an arbitrary element of $B\cap X_i$ such that $x\notin A$. We claim that $A\cup{x} \in \varphi$. To show this, we need to verify that $|A\cup{x} \cap X_j|\leq 1$ for all $j\in V$.

If $j\neq i$, then we have $|A\cap X_j| \leq 1$ and $|A\cup{x}\cap X_j| = |A\cap X_j| \leq 1$, so $A\cup{x}$ is independent in $X_j$.

If $j=i$, then we have $|A\cap X_i| < |B\cap X_i|$, so $x$ is the unique element of $B\cap X_i$ that is not in $A$. Therefore, $|A\cup{x}\cap X_i| = |A\cap X_i| + 1 \leq |B\cap X_i| \leq 1$, so $A\cup{x}$ is independent in $X_i$.

Thus, $A\cup{x}$ is independent in all $X_j$ and hence it is an independent set in $\mathcal{F}$. Therefore, $\mathcal{F}$ satisfies the exchange property $(I_3)$ and is a matroid, specifically a transversal matroid.

## Maximum matching in bipartite graphs ##
In the mathematical field of graph theory, a bipartite graph is a graph whose vertices can be divided into two disjoint and independent sets $V_1$ and $V_2$, that is every edge connects a vertex in $V_1$ to one in $V_2$.

Recall that a matching in a graph is a set of edges such that no two of them share a vertex. A maximum matching is a matching that contains the largest possible number of edges. 
The problem of constructing a maximum-size matching in a bipartite graph can be formulated using matroids as follows: 
let $G$ be a bipartite graph with no isolated vertices with parts $V_1$ and $V_2$ and edge set $E$. We define two matroids $\mathcal{M_1}$ and $\mathcal{M_2}$ with
the ground set E. [2]

G = ($V_1$ ∪ $V_2$, $E$ ⊆ $V_1$ × $V_2$) can be written as matroid intersection using
- $I_1$ = { $F$ ⊆ $E$ | ∀ $v$ ∈ $V_1$ : | $F$ ∩ δ($v$)| ≤ 1 }
- $I_2$ = { $F$ ⊆ $E$ | ∀ $v$ ∈ $V_2$ : | $F$ ∩ δ($v$)| ≤ 1 } [3]

where δ($v$) represents edges incident to vertex $v$

Here, $I_i$ are all edge collections such that each node in $V_i$ has at most one incident edge. 
In other words: $I_i$ is all subsets of the edges such that: every vertex $v$
in $V_i$ satisfy that: the cardinality of matching intersect all edges that are incident to that particular vector in $V_i$ has to be at most one

Hence, the problem of constructing a maximum-size matching can be formulated as the problem of constructing a maximum-size intersection $I_1$ ∩ $I_2$ independent in two given matroids with the same ground set.
The matroids $\mathcal{M_1}$ and $\mathcal{M_2}$ are transversal matroids. In our definition of transversal matroids, the independent sets are those that correspond to matchings in a bipartite graph, and the exchange property can be proved by showing that if we have two matchings where the larger matching has one more edge than the smaller matching, then there exists an edge in the larger matching that can be added to the smaller matching to obtain another matching.

Observe that if $G$ has no isolated vertices, then the rank of $\mathcal{M_1}$ is | $V_1$ | and the rank of $\mathcal{M_2}$ is | $V_2$ |.


For the algorithm refer to [programming part](https://github.com/metury/mff-dso-project/blob/main/src/README.md).


## Resources ##
[[1] Lawler, Eugene L.: Matroid intersection algorithms](https://link.springer.com/article/10.1007/BF01681329)

[[2] RNDr. Ondřej Pangrác, Ph.D.: Matroid intersection problem](https://iuuk.mff.cuni.cz/~pangrac/vyuka/matroids/matroid-ch3.pdf)

[[3] Jan Vondrák: Polyhedral techniques in combinatorial optimization](https://theory.stanford.edu/~jvondrak/CS369P/lec10.pdf)

[[4] Brualdi, Dinolt: Characterizations of Transversal Matroids and Their Presentation](https://www.sciencedirect.com/science/article/pii/009589567290041X)

