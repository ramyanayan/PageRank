package com.example.java;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        DirectedGraph graph = new DirectedGraph();
        graph.add(node1);
        graph.add(node2);


        graph.addEdge(node1,node2);
        graph.addEdge(node1,node3);
        graph.addEdge(node2, node3);
        graph.addEdge(node3, node1);
        graph.addEdge(node4, node3);

        graph.instantiateCostEdge();
        System.out.println("The number of vertices is: " + graph.neighbourVertices.keySet().size());
        System.out.println("The number of edges is: " + graph.nr_edges);
        System.out.println("The current graph: " + graph);
        System.out.println("the indegree of node 1 is "+ node1.indegree);
        System.out.println("the Outdegree of node 1 is "+ node1.outdegree);
        System.out.println("the in-list of node 1 is "+ node1.inlist);
        System.out.println("the out-list of node 1 is "+ node1.outlist);


        graph.calculatePageRank();  /* calculate the page rank for each node in the graph iteratively */
        graph.printPageRank();  /* Print the final page rank values */

    }
}
