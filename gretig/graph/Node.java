package gretig.graph;

import java.util.*;

/**
 * Created by quinten on 18/10/16.
 */
public class Node {

    private final int identity;
    private List<Edge> edges;
    private Set<Node> neighbors;
    private String status;
    private Set<Node> uncoveredNeighbores;
    private boolean inHamiltonCycle;
    private HamiltonNode hamiltonNode;
    public Map<Node, TriangleEdge> getNeigb() {
        return neigb;
    }

    private Map<Node, TriangleEdge> neigb;

    public Node(int identity) {
        this.identity = identity;
        edges = new ArrayList<>();
        neighbors = new HashSet<>();
        status = "uncovered";
        uncoveredNeighbores = new HashSet<>();
        inHamiltonCycle = false;
        neigb = new HashMap<>();
        this.hamiltonNode = new HamiltonNode(identity);
    }

    public HamiltonNode getHamiltonNode(){
        return hamiltonNode;
    }

    public int getIdentity() {
        return identity;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Calculates the amount of uncovered neighbors, including itself.
     *
     * @return integer
     */
    public int getUncoveredNeighbores() {
        int amount = Objects.equals(status, "uncovered") ? 1 : 0;

        amount += uncoveredNeighbores.size();

        return amount;
    }

    public void addEdge(Edge neighbor) {
        edges.add(neighbor);
    }

    public Edge getEdgeWithNeighbour(Node n){
        for (Edge e: edges){
            if (e.getNeighboreEdge(n).equals(e)){
                return e;
            }
        }
        return null;
    }

    public void updateNeighboresNode() {
        if (neighbors.contains(this)) {
            neighbors.remove(this);
        }
        if (uncoveredNeighbores.contains(this)){
            uncoveredNeighbores.remove(this);
        }
    }

    public void addNeighbourTriangleEdge(Node neighbour, TriangleEdge te){
        if (neighbors.contains(neighbour)){

        }
    }


    public TriangleEdge getTriangleEdgeNeighbour(Node n){
        return neigb.get(n);
    }


    public void deleteEdge(Edge neighbor) {
        edges.remove(neighbor);
    }

    public void setInHamiltonCycle(){
        inHamiltonCycle = true;
    }

    public boolean isInHamiltonCycle(){
        return inHamiltonCycle;
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
        uncoveredNeighbores.add(neighbor);
    }

    public void deleteNeighbor(Node node){
        neighbors.remove(node);
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public void markConnected(){
        this.status = "connected";
        for (Node n: neighbors){
            if (! (Objects.equals(n.getStatus(), "connected"))){
                n.changeStatus("covered");
            }
        }
    }

    public void markConnected(Set<Node> list){
        if (status.equals("uncovered")){
            list.remove(this);
        }
        changeStatus("connected");
        for (Node n: neighbors){
            if (n.getStatus().equals("uncovered")){
                list.remove(n);
            }
            if (! (Objects.equals(n.getStatus(), "connected"))){
                n.changeStatus("covered");
            }
            n.deleteNeighbor(this);
        }
    }

    public int calculateGrade(){
        return neighbors.size();
    }

    public void deleteUncoveredNode(Node node){
        uncoveredNeighbores.remove(node);
    }


    /**
     * @param status Only take "covered", "uncovered", "connected"
     */
    public void changeStatus(String status) {
        if (this.status.equals("uncovered")){
            for (Node n: neighbors){
                n.deleteUncoveredNode(this);
            }
        }
        this.status = status;
    }

    @Override
    public Node clone() {
        Node clone = new Node(identity);
        for (Edge edge : this.edges) {
            clone.addEdge(edge.clone());
        }
        for (Node neighbor : this.neighbors) {
            clone.addNeighbor(neighbor);
        }
        clone.changeStatus(status);
        return clone;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Node) {
            Node o = (Node) other;
            return o.getIdentity() == this.getIdentity();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getIdentity();
    }

    public void printEdgesList() {
        System.out.println("Bogenlijst van Node " + identity + ":");
        for (Edge e : edges) {
            System.out.println("    " + e.getIdentity());
        }
    }

    public void printNeighborsIdentities() {
        System.out.println("Buren van Node " + identity + ": en het heeft zoveel uncovered nodes: " + getUncoveredNeighbores());
        for (Node n : neighbors) {
            System.out.println(n.getIdentity());
        }
    }

}
