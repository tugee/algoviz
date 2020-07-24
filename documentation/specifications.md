# Algoviz - A pathfinding algorithm visualizer

## Introduction

I have chosen to create a pathfinding visualization tool as my project for the Data Structures and Algorithms laboratory course at the University of Helsinki. 
The program will visualize the way in which different pathfinding algorithms find the shortest path between two nodes in a grid. 
The visualization naturally requires a graphical user interface and in order to allow for more robust functionality, the user will be able to choose the location of the start and finish nodes and to draw obstacles between these nodes.  
The programme will be implemented in Java.

## Algorithms and data structures to be implemented
I will be implementing at least the A* and Dijkstas algorithm for this project. We will implement A* with a priority queue and Dijkstra's with a min-heap.  

## Why?
Usually pathfinding algorithms are introduced in a purely theoretical sense. While students might be able to solve programming assingments by following their course material, for practical applications and better understanding, it is important that one gains some intuition as to how the algorithms work. I believe visualization is one of the best ways to achieve this.

## Inputs 
The user will input the start and finish nodes into the grid in the graphical user interfaces. The user can also select which algorithm to visualize.

## Space and time complexities
The space and time complexities are equivalent as A* is a heuristically improved version of Dijkstra's algorithm, which guarantees no premium in performance in the worst-case scenarios.
Time complexity for both is: ![equation](https://latex.codecogs.com/gif.latex?O%28%28%5Cleft%20%7C%20E%20%5Cright%20%7C&plus;%5Cleft%20%7C%20V%20%5Cright%20%7C%29log%28%5Cleft%20%7C%20V%20%5Cright%20%7C%29%29)

Space complexity for both: ![equation](https://latex.codecogs.com/gif.latex?O%28%5Cleft%20%7C%20V%20%5Cright%20%7C%29)

Where E is the number of edges in the graph and V is the number of vertices.
## Sources
- https://en.wikipedia.org/wiki/A*_search_algorithm
- http://theory.stanford.edu/~amitp/GameProgramming/AStarComparison.html#dijkstras-algorithm-and-best-first-search
- https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
