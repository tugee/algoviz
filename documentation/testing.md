# Testing

## Unit testing
![](https://github.com/tugee/algoviz/blob/master/documentation/performanceTesting.png)
As we can see from the coverage report, about 53% of the code is covered by the tests. Performance tests rely on the other classes and the graphical UI are excluded from tests.
In the logic class the exception catchers are not covered by the tests. In the algorithms class we haven't covered the getter method and markPath method which interacts with the UI. In the data structures package similarly we haven't created tests for the getter classes.

## Testing the algorithms
I created a few simple tests for the algorithms. I tested the functioning on a simple diagonal path, on a impossible blocked wall path and on movingAI labs map. For the JPS algorithm, I also tested whether the visited Node count in a simple case where the start and finish nodes are on the same path marks 0 jump points.
Path finding tests utilizing the MovingAI labs pregenerated scenarios proved to be useful but not completely reliable as my algorithm did not use exactly similar movement rules.

## Performance testing
We run the three algorithms on a thousand specified scenarios on the Paris streetmap and a maze from MovingAI labs benchmarks. We discard the first run and only time the second run time for each pair of start and finish nodes for each algorithm. These performance tests are reproducible by running the UI and selecting a MovingAI labs map from the drop-down menu. I have specifically not included the initializing of maps or marking the path after finding the route from the start node to the finish node. 
```
algorithm.findPath();
long now = System.nanoTime();
algorithm.findPath();
long end = System.nanoTime();
 ```
_Utilized System.nanoTime() to measure the time taken to run the performance tests_

## Results
![](https://github.com/tugee/algoviz/blob/master/documentation/paris.png)

![](https://github.com/tugee/algoviz/blob/master/documentation/mazetest.png)

We see that Jump Point Search is clearly the quickest pathfinding algorithm. The suprising result that A* is slower than Dijkstra in the maze can be explained by the fact that the euclidean heuristic can guide the searching algorithm the wrong way when there are many obstacles and dead ends. 
