package gretig.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by quinten on 19/10/16.
 */
public class Edge {
    private int identity;
    private List<Node> nodes;

    public Edge(int identity) {
        this.identity = identity;
        nodes = new ArrayList<>();
    }

    public int getIdentity() {
        return identity;
    }

    public void addNode(Node node) {
        if (nodes.size() > 2) {
            throw new IllegalArgumentException("An edge can only has two nodes!");
        }
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void printNodes() {
        for (Node neigh : nodes) {
            System.out.println(neigh.getIdentity());
        }
    }



    public Node getNeighbor(Node node) {

        if (nodes.contains(node)) {
            for (Node node1 : this.nodes) {
                if (!node1.equals(node)) {
                    return node1;
                }
            }
        } else {
            throw new IllegalArgumentException("The node has no Neighbor!");
        }
        return null;
    }

    public Edge getNeighboreEdge(Node node) {

        if (nodes.contains(node)) {
            for (Node node1 : this.nodes) {
                if (!node1.equals(node)) {
                    return this;
                }
            }
        } else {
            throw new IllegalArgumentException("The node has no Neighbor!");
        }
        return null;
    }


    @Override
    public Edge clone() {
        Edge clone = new Edge(identity);
        for (Node node : this.nodes) {
            clone.addNode(node.clone());
        }
        return clone;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge o = (Edge) other;
            return o.getIdentity() == this.getIdentity();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getIdentity();
    }

}
