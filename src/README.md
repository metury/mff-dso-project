# Programming part

Now we will take a deeper dive into the real implementation we have made. This code was made mainly for showcasing purposes. We will be using graph library written in Java, that can be found on [this link](https://gitlab.mff.cuni.cz/turekto1/zs-java-graphs). Thus the code itself is as well written in Java. Also some code is not the same as in the source files, jsut to be easily readable.

First we have how all independent sets are created:

```java
public void createIndependentSets(String fileOutput){
	HashSet<Edge> set = new HashSet<Edge>();
	// This is to get rid of the duplicates.
	HashSet<Vertex> added = new HashSet<Vertex>();
	for(Vertex v : Vi){
		added.add(v);
		for(Edge e : v){
			set.add(e);
			createIndependentSetsRec(set, added);
			set.remove(e);
		}
		added.remove(v);
	}
	printIndependentSets(fileOutput);
}
```

As you can see it is made recursively, so we need one part of the code, to see the whole picture.

```java
private void createIndependentSetsRec(HashSet<Edge> set, HashSet<Vertex> added){
	for(Vertex v : Vi){
		if(added.contains(v)){
			continue;
		}
		for(Edge e : v){
			added.add(v);
			set.add(e);
			createIndependentSetsRec(set, added);
			added.remove(v);
			set.remove(e);
		}
	}
	HashSet<Edge> I = set.copy();
	if(I.size() > 0){
		independentSets.add(I);
	}
}
```

As we may already see, this algorithm is just greedily creating all possible *independent sets*. By running this code on both $V_{1}$ and $V_{2}$ we get two matroids.

The very last thing is to use these matroids to find the maximal matching in bipartite graph. This is made by intersection of these matroids. And because in both matroids it is so that the vertices from $V_{1}$ (resp. $V_{2}$) need to have degree at most one. So if we intersect these matroids we get sets where both properties hold. So we have matching in bipartite graph. After choosing the biggest set we get the (one of many) maximal matching. This is quite easy to make and the code looks like this:

```java
public static HashSet<Edge> maximalIntersection(ArrayList<HashSet<Edge>> I1, ArrayList<HashSet<Edge>> I2){
	I2.retainAll(I1); // Keep all which are also in I1.
	I2.sort((a, b) -> a.size() - b.size()); // Sort it by size.
	return I2.get(I2.size() - 1); // Return the biggest.
}
```

After adding some code we can have nice export files:

- [1](output/1.md) *this graph is not bipartite*
- [2](output/2.md) *this is simple bipartite graph*
- [3](output/3,md) *this is $K_{4,4}$ graph*
- [4](output/4.md) *just another bipartite graph, jsut a litle bigger* (files with all sets are not present due to their file size)

Also there are [RESULTS] of how much time it took to finish each part. Just keep in mind, that it also have to write to each file, which may take quite some time.

