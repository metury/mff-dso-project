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
	9(" ");
	0 -- "3.0" --- 1;
	1 -- "3.14" --- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -- "1.0" --- 0;
	0 -- "2.0" --- 5;
	1 -- "7.89" --- 6;
	2 -- "16.0" --- 7;
	3 -- "3.33" --- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -- "6.66" --- 9;
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
	9(" ");
	0 -. "3.0" -.- 1;
	1 -. "3.14" -.- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -. "1.0" -.- 0;
	0 -. "2.0" -.- 5;
	1 -. "7.89" -.- 6;
	2 -- "16.0" --- 7;
	3 -. "3.33" -.- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -. "6.66" -.- 9;
```

This matroid has a value: `124.59999999999998`.

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
	9(" ");
	0 -. "3.0" -.- 1;
	1 -. "3.14" -.- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -. "1.0" -.- 0;
	0 -. "2.0" -.- 5;
	1 -. "7.89" -.- 6;
	2 -- "16.0" --- 7;
	3 -. "3.33" -.- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -. "6.66" -.- 9;
```

This matroid has a value: `124.59999999999998`.

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
	9(" ");
	0 -. "3.0" -.- 1;
	1 -. "3.14" -.- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -. "1.0" -.- 0;
	0 -. "2.0" -.- 5;
	1 -. "7.89" -.- 6;
	2 -- "16.0" --- 7;
	3 -. "3.33" -.- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -. "6.66" -.- 9;
```

This matroid has a value: `124.59999999999998`.

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
	9(" ");
	0 -- "3.0" --- 1;
	1 -- "3.14" --- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -- "1.0" --- 0;
	0 -- "2.0" --- 5;
	1 -- "7.89" --- 6;
	2 -- "16.0" --- 7;
	3 -- "3.33" --- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -- "6.66" --- 9;
	1 -- "7.89" --- 6;
	4 -- "1.0" --- 0;
	7 -- "6.66" --- 9;
	1 -- "3.14" --- 2;
	3 -- "3.33" --- 8;
	0 -- "2.0" --- 5;
	0 -- "3.0" --- 1;
```

Now we are garanteed to be able to find an euler path. This euler path is the shortest solution to the *Chinese postman problem*.

### Euler path: 

First we will index our vertices and then show a path.

```mermaid
graph TD;
	0("0.0");
	1("1.0");
	2("2.0");
	3("3.0");
	4("4.0");
	5("5.0");
	6("6.0");
	7("7.0");
	8("8.0");
	9("9.0");
	0 -- "3.0" --- 1;
	1 -- "3.14" --- 2;
	2 -- "5.0" --- 3;
	3 -- "6.0" --- 4;
	4 -- "1.0" --- 0;
	0 -- "2.0" --- 5;
	1 -- "7.89" --- 6;
	2 -- "16.0" --- 7;
	3 -- "3.33" --- 8;
	4 -- "5.27" --- 9;
	5 -- "8.99" --- 7;
	5 -- "9.0" --- 8;
	6 -- "42.01" --- 8;
	6 -- "32.33" --- 9;
	7 -- "6.66" --- 9;
	1 -- "7.89" --- 6;
	4 -- "1.0" --- 0;
	7 -- "6.66" --- 9;
	1 -- "3.14" --- 2;
	3 -- "3.33" --- 8;
	0 -- "2.0" --- 5;
	0 -- "3.0" --- 1;
```

Euler path is as follows: `0 -> 1 -> 6 -> 9 -> 7 -> 9 -> 4 -> 0 -> 5 -> 8 -> 3 -> 8 -> 6 -> 1 -> 2 -> 7 -> 5 -> 0 -> 4 -> 3 -> 2 -> 1 -> 0`.

For those who may be interested in number result, it is: `178.63999999999996`.
