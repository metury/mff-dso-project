# Even subgraph matroids

On input we have following graph $G$:

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

## Matroid Nr.1

```mermaid
graph TD;
	0(" ");
	1(" ");
	2(" ");
	3(" ");
	0 -. "20.0" -.- 1;
	1 -. "5.0" -.- 3;
	1 -. "3.0" -.- 2;
	2 -. "0.0" -.- 3;
```

This matroid has a value: `0.0`.

## Matroid Nr.2

**This matroid is maximal with respect to the edge values.**

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

