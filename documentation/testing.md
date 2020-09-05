# Testing

## Unit testing
![](https://github.com/tugee/algoviz/blob/master/documentation/performanceTesting.png)
As we can see from the coverage report, about 53% of the code is covered by the tests. Performance tests rely on the other classes and the graphical UI are excluded from tests.
In the logic class the exception catchers are not covered by the tests. In the algorithms class we haven't covered the getter method and markPath method which interacts with the UI. In the data structures package similarly we haven't created tests for the getter classes.

## Performance testing
We run the three algorithms on a thousand specified scenarios on the Paris streetmap and a maze from MovingAI labs benchmarks. We discard the first run and only time the second run time for each pair of start and finish nodes for each algorithm. These performance tests are reproducible by running the UI and selecting a MovingAI labs map from the drop-down menu. 

![](https://github.com/tugee/algoviz/blob/master/documentation/paris.png)

![](https://github.com/tugee/algoviz/blob/master/documentation/mazetest.png)
