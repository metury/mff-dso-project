# Chinese postman problem

On input we are given graph $G$ and we have to find the solution to the *Chinese postman problem*. Given graph is:

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -- "20.0" --- 1;
	1 -- "5.0" --- 3;
	1 -- "3.0" --- 2;
	2 -- "0.0" --- 3;
```

Now we will find all **Cycle subgraphs matroids** and **Even subgraph matroids** and use them to use the intersection of matroids to solve the problem.Because the number of all possible matroids is way too many, we will show only the maximal of each.

## Cycle Matroids

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -. "20.0" -.- 1;
	1 -- "5.0" --- 3;
	1 -- "3.0" --- 2;
	2 -- "0.0" --- 3;
```

This matroid has a value: `8.0`.

## Even subgraph matroids

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -. "20.0" -.- 1;
	1 -- "5.0" --- 3;
	1 -- "3.0" --- 2;
	2 -- "0.0" --- 3;
```

This matroid has a value: `8.0`.

By using the matroid intersection algorithm we get this following result:

## Result of the intersection algorithm

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -. "20.0" -.- 1;
	1 -- "5.0" --- 3;
	1 -- "3.0" --- 2;
	2 -- "0.0" --- 3;
```

This matroid has a value: `8.0`.

To solve the *Chinese Postman problem* we duplicate the edges not in the matroid intersection and get the multigraph $H$:

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -- "20.0" --- 1;
	1 -- "5.0" --- 3;
	1 -- "3.0" --- 2;
	2 -- "0.0" --- 3;
	0 -- "20.0" --- 1;
```

Now we are garanteed to be able to find an euler path. This euler path is the shortest solution to the *Chinese postman problem*.We won't be showing the euler path itself because it is not that hard to find it.

For those who may be interested in number result, it is: `48.0`.
