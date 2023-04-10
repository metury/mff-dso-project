# Cycle Matroids

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

By this non-so efficient way we find all cycles and then try to combine as most of the cycles together. Then we get following so called **cycle matroids**.

## Matroid Nr.1

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

This matroid has a value: 8.0

