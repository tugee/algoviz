# Implementation
This program has a graphical user interface that visualizes three different pathfinding algorithms, A*, Dijkstra and Jump Point Search. The data structures and algorithms are implemented in separate classes within suitably grouped packages. The user interface is done utilizing the JavaFX library, but otherwise all of the algorithms and data structures are user implemented. 

## User I/O

The user can select the start and finish nodes for the respective algorithms to find a path inbetween. The user can also draw walls to block the path of these algorithms, which is useful when trying to understand how sensitive to change these algorithms are. The map has one blank map and 4 pre-loaded maps from MovingAI. These maps are read from .map files which are formed of "@" and ".". 

## Data structures
We have implemented a Node, Minimum Heap and a List implementation for Nodes. The minimum heap is not general and works only for Nodes.

## Algorithms
The A* and Dijkstra algorithms have a similar implementation with the only difference being that A* includes a heuristic that evaluates each node also considering their euclidean distance from the Finish node. Jump Point Search is drastically different and depends on a recursive jumping algorithm which constantly prunes its neighbours for so-called "forced" neighbours which stops the recursion and returns the node to which these forced neighbours are neighbours to. Jump Point Search might "visit" more nodes than say A* but since it adds often an order of magnitude fewer nodes to the minimum heap it performs much better in the time comparison.

The final implementation of the algorithms do not differ in time or space complexity in comparison to the one's in the specification document. JPS being a pruning technique built on top of A* means its complexities are comparable to the one in A*. 


## Performance comparison
My implementation achieved a similar relationship in performance between the three considered algorithms, with JPS being the quickest, A* being the second most quick and Dijkstra with it's uninformed search being the slowest. There were certain situations in which this relationship was not as pronounced and also times in which the unguided Dijkstra beat the euclidean heurestic using A*, more on this in the testing document. JPS and A* shone especially in situations where there was a lot of open space. JPS was about an order of magnitude faster than the other algorithms.

## Possible improvements and additional functionalities
From my original idea, I did not have enough time to implement animation of the pathfinding process. I will try to work on this after the project as I feel that it is an important part of understanding intuitively the way in which these algorithms find the shortest path between nodes.
Allowing the user to load any maps with . and @ characters by merely loading them is also missing, right now there are 4 maps that are pre-loaded to the drop down menu supplied in the GUI. 

There is some recycled code which could be refactored into abstract classes, and some methods in the algorithm classes could be separated into for example a separate solution class which returns for example the final chararray or visited nodes and path length.

## Sources
https://gamedevelopment.tutsplus.com/tutorials/how-to-speed-up-a-pathfinding-with-the-jump-point-search-algorithm--gamedev-5818
https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html
https://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-aaai11.pdf
https://movingai.com/benchmarks/
