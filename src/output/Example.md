# Chinese postman problem

On input we are given graph $G$ and we have to find the solution to the *Chinese postman problem*. Given graph is:

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	4(" ");
	5(" ");
	6(" ");
	7(" ");
	8(" ");
	0 -- "70.0" --- 1;
	0 -- "70.0" --- 3;
	1 -- "50.0" --- 2;
	1 -- "50.0" --- 5;
	2 -- "40.0" --- 6;
	2 -- "80.0" --- 4;
	3 -- "70.0" --- 2;
	3 -- "60.0" --- 4;
	4 -- "100.0" --- 6;
	4 -- "90.0" --- 8;
	5 -- "40.0" --- 7;
	5 -- "60.0" --- 6;
	6 -- "40.0" --- 7;
	6 -- "70.0" --- 8;
```

Now we will find all **Cycle subgraphs matroids** and **Even subgraph matroids** and use them to use the intersection of matroids to solve the problem.Because the number of all possible matroids is way too many, we will show only the maximal of each.

## Cycle Matroids

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	4(" ");
	5(" ");
	6(" ");
	7(" ");
	8(" ");
	0 -- "70.0" --- 1;
	0 -- "70.0" --- 3;
	1 -- "50.0" --- 2;
	1 -. "50.0" -.- 5;
	2 -. "40.0" -.- 6;
	2 -- "80.0" --- 4;
	3 -. "70.0" -.- 2;
	3 -- "60.0" --- 4;
	4 -- "100.0" --- 6;
	4 -- "90.0" --- 8;
	5 -- "40.0" --- 7;
	5 -- "60.0" --- 6;
	6 -- "40.0" --- 7;
	6 -- "70.0" --- 8;
```

This matroid has a value: `730.0`.

## Even subgraph matroids

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	4(" ");
	5(" ");
	6(" ");
	7(" ");
	8(" ");
	0 -- "70.0" --- 1;
	0 -- "70.0" --- 3;
	1 -- "50.0" --- 2;
	1 -. "50.0" -.- 5;
	2 -. "40.0" -.- 6;
	2 -- "80.0" --- 4;
	3 -. "70.0" -.- 2;
	3 -- "60.0" --- 4;
	4 -- "100.0" --- 6;
	4 -- "90.0" --- 8;
	5 -- "40.0" --- 7;
	5 -- "60.0" --- 6;
	6 -- "40.0" --- 7;
	6 -- "70.0" --- 8;
```

This matroid has a value: `730.0`.

By using the matroid intersection algorithm we get this following result:

## Result of the intersection algorithm

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	4(" ");
	5(" ");
	6(" ");
	7(" ");
	8(" ");
	0 -- "70.0" --- 1;
	0 -- "70.0" --- 3;
	1 -- "50.0" --- 2;
	1 -. "50.0" -.- 5;
	2 -. "40.0" -.- 6;
	2 -- "80.0" --- 4;
	3 -. "70.0" -.- 2;
	3 -- "60.0" --- 4;
	4 -- "100.0" --- 6;
	4 -- "90.0" --- 8;
	5 -- "40.0" --- 7;
	5 -- "60.0" --- 6;
	6 -- "40.0" --- 7;
	6 -- "70.0" --- 8;
```

This matroid has a value: `730.0`.

To solve the *Chinese Postman problem* we duplicate the edges not in the matroid intersection and get the multigraph $H$:

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	4(" ");
	5(" ");
	6(" ");
	7(" ");
	8(" ");
	0 -- "70.0" --- 1;
	0 -- "70.0" --- 3;
	1 -- "50.0" --- 2;
	1 -- "50.0" --- 5;
	2 -- "40.0" --- 6;
	2 -- "80.0" --- 4;
	3 -- "70.0" --- 2;
	3 -- "60.0" --- 4;
	4 -- "100.0" --- 6;
	4 -- "90.0" --- 8;
	5 -- "40.0" --- 7;
	5 -- "60.0" --- 6;
	6 -- "40.0" --- 7;
	6 -- "70.0" --- 8;
	3 -- "70.0" --- 2;
	2 -- "40.0" --- 6;
	1 -- "50.0" --- 5;
```

Now we are garanteed to be able to find an euler path. This euler path is the shortest solution to the *Chinese postman problem*.We won't be showing the euler path itself because it is not that hard to find it.

For those who may be interested in number result, it is: `1050.0`.
