package gretig;

import gretig.graph.*;
import gretig.parser.InputData;
import gretig.parser.InputData2;

import java.io.IOException;
import java.util.*;

/**
 * Created by llion on 26/11/16.
 */
public class Hamilton {
    private Map<Node, ArrayList<Node>> combinatorialEmbedding;
    private ArrayList<Node> nodes;
    private HashMap<Set<Node>, TriangleEdge> coupleNodeTriangularEdge;
    private ArrayList<TriangleEdge> triangleEdgeArrayList;
    private HashSet<Triangle> triangles;
    private HashSet<Node> hamiltoncykel = new HashSet<>();
    private HashSet<TriangleEdge> triangleEdgeOnCycle;
    private ArrayList<Triangle> nnn = new ArrayList<>();
    private Triangle beginpunt;
    private HashSet<TriangleEdge> testTriangulation;
    private Stack<TriangleEdge> stackEdges;
    private HashSet<Node> nodeSet;

    public Hamilton(Graph graph) {
        triangleEdgeOnCycle = new HashSet<>();
        this.triangles = new HashSet<>();
        this.coupleNodeTriangularEdge = new HashMap<>();
        this.triangleEdgeArrayList = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.combinatorialEmbedding = new HashMap<>();
        for (Node n : graph.getNodes()) {
            nodes.add(n);
        }
        makeCombinatorialEmbedding();
        makeTriangularEdges();
        fillTriangles();
        initialization();
        test();
    }

    private void initialization() {
        Triangle tr = null;
        for (Triangle triangle : triangles) {
            tr = triangle;
            break;
        }
        beginpunt = tr;
        Set<Node> l1 = new HashSet<>();
        Set<Node> l2 = new HashSet<>();
        Set<Node> l3 = new HashSet<>();
        l1.add(tr.getN1());
        l1.add(tr.getN2());
        l2.add(tr.getN1());
        l2.add(tr.getN3());
        l3.add(tr.getN2());
        l3.add(tr.getN3());

        tr.getN1().getHamiltonNode().setPrevious(tr.getN3().getHamiltonNode());
        tr.getN1().getHamiltonNode().setNext(tr.getN2().getHamiltonNode());

        tr.getN2().getHamiltonNode().setPrevious(tr.getN1().getHamiltonNode());
        tr.getN2().getHamiltonNode().setNext(tr.getN3().getHamiltonNode());

        tr.getN3().getHamiltonNode().setPrevious(tr.getN2().getHamiltonNode());
        tr.getN3().getHamiltonNode().setNext(tr.getN1().getHamiltonNode());
        tr.setInCycle();

        hamiltoncykel.add(tr.getN1());
        hamiltoncykel.add(tr.getN3());
        hamiltoncykel.add(tr.getN2());



        triangleEdgeOnCycle.add(coupleNodeTriangularEdge.get(l1));
        triangleEdgeOnCycle.add(coupleNodeTriangularEdge.get(l2));
        triangleEdgeOnCycle.add(coupleNodeTriangularEdge.get(l3));
    }


    private void test(){
        testTriangulation = new HashSet<>();
        stackEdges = new Stack<>();

        nodeSet = new HashSet<>();

        Set<Node> l1 = new HashSet<>();
        Set<Node> l2 = new HashSet<>();
        Set<Node> l3 = new HashSet<>();
        nodeSet.add(beginpunt.getN1());
        nodeSet.add(beginpunt.getN2());
        nodeSet.add(beginpunt.getN3());

        l1.add(beginpunt.getN1());
        l1.add(beginpunt.getN2());
        l2.add(beginpunt.getN1());
        l2.add(beginpunt.getN3());
        l3.add(beginpunt.getN2());
        l3.add(beginpunt.getN3());
        stackEdges.push(coupleNodeTriangularEdge.get(l1));
        stackEdges.push(coupleNodeTriangularEdge.get(l2));
        stackEdges.push(coupleNodeTriangularEdge.get(l3));

        testTriangulation.add(coupleNodeTriangularEdge.get(l1));
        testTriangulation.add(coupleNodeTriangularEdge.get(l2));
        testTriangulation.add(coupleNodeTriangularEdge.get(l3));

        while (!stackEdges.isEmpty() && nodeSet.size() < nodes.size()){
            TriangleEdge tre = stackEdges.pop();
            List<Triangle> edgeTriangles = tre.getTriangles();
            addNewNodeToHamiltonianCycle(tre, edgeTriangles.get(0));
            addNewNodeToHamiltonianCycle(tre, edgeTriangles.get(1));
        }
        if (nodeSet.size() == nodes.size()){
            printCycle();
        } else{
            System.out.print("geen cykel gevonden.");
        }
    }


    /**
     * Functie die op basis van de parameters een nieuwe knoop zal toevoegen en
     * @param @edge De boog die we willen vervangen door de 2 nieuwe bogen van de driehoek.
     * @param @triangle Driehoek die aan één van de twee kanten van de boog ligt.
     */
    private void addNewNodeToHamiltonianCycle(TriangleEdge edge, Triangle triangle){
        Node third = triangle.getThirdNode(edge.getNodesList().get(0), edge.getNodesList().get(1));

        if (!nodeSet.contains(third)) {
            Set<Node> l1 = new HashSet<>();
            Set<Node> l2 = new HashSet<>();
            Set<Node> l3 = new HashSet<>();
            l1.add(triangle.getN1());
            l1.add(triangle.getN2());
            l2.add(triangle.getN1());
            l2.add(triangle.getN3());
            l3.add(triangle.getN2());
            l3.add(triangle.getN3());
            TriangleEdge[] triangleEdges = {coupleNodeTriangularEdge.get(l1), coupleNodeTriangularEdge.get(l2), coupleNodeTriangularEdge.get(l3)};
            for (TriangleEdge tre: triangleEdges){
                if (!testTriangulation.contains(tre)){
                    stackEdges.push(tre);
                }
            }

            nodeSet.add(third);

            Node n1 = edge.getNodesList().get(0);
            Node n2 = edge.getNodesList().get(1);

            if (n1.getHamiltonNode().next().equals(n2.getHamiltonNode())){
                third.getHamiltonNode().setPrevious(n1.getHamiltonNode());
                third.getHamiltonNode().setNext(n2.getHamiltonNode());

                n1.getHamiltonNode().setNext(third.getHamiltonNode());
                n2.getHamiltonNode().setPrevious(third.getHamiltonNode());

            } else{
                third.getHamiltonNode().setPrevious(n2.getHamiltonNode());
                third.getHamiltonNode().setNext(n1.getHamiltonNode());

                n2.getHamiltonNode().setNext(third.getHamiltonNode());
                n1.getHamiltonNode().setPrevious(third.getHamiltonNode());

            }
        }


    }


    public HashSet<Node> getHamiltoncykel() {
        return hamiltoncykel;
    }

    /**
     * Vult de Map<Node, Node[]> combinatorialEmbedding in kader van het principe van Combinatoriale Embedding.
     * <p>
     * De complexiteit van dit algoritme is linear vanwillen deze reden:
     * Een vlakke triangulatie heeft 3*n - 6 bogen. Deze zullen we dus max 2 keer overlopen.
     * Bijgevolg hebben we dus (6*n - 12) wat de complexiteit van n levert.
     */
    private void makeCombinatorialEmbedding() {
        for (Node n : this.nodes) {
            ArrayList<Node> neighbors = new ArrayList<>();
            for (Edge e : n.getEdges()) {
                neighbors.add(e.getNeighbor(n));
            }
            combinatorialEmbedding.put(n, neighbors);
        }
    }

    /**
     * Functie die TriangularEdges aanmaakt. Elke TriangularEdge krijgt een uniek identity mee.
     */
    private void makeTriangularEdges() {
        int id = 1;
        for (Node n : this.nodes) {
            ArrayList<Node> l = combinatorialEmbedding.get(n);
            for (Node q : l) {
                HashSet<Node> s = new HashSet<>();
                s.add(q);
                s.add(n);
                if (coupleNodeTriangularEdge.get(s) == null) {
                    TriangleEdge triangleEdge = new TriangleEdge(id);
                    triangleEdge.addNeighbores(n);
                    triangleEdge.addNeighbores(q);
                    coupleNodeTriangularEdge.put(s, triangleEdge);
                    id++;
                    triangleEdgeArrayList.add(triangleEdge);
                }
            }
        }
    }

    public HashSet<Node> getNodeSet() {
        return nodeSet;
    }

    /**
     * Vult de Set triangles met de driehoeken van de Graaf.
     */
    private void fillTriangles() {
        for (Node n : nodes) {
            for (Node l : combinatorialEmbedding.get(n)) {
                Node next2node = gentNextNodeInCombinatorialEmbedding(n, l);
                if (combinatorialEmbedding.get(next2node).contains(n)) {
                    Triangle triangle = new Triangle(n, l, next2node);
                    if (!triangles.contains(triangle)) {
                        triangles.add(triangle);
                        nnn.add(triangle);
                        HashSet<Node> s1 = new HashSet<>();
                        HashSet<Node> s2 = new HashSet<>();
                        HashSet<Node> s3 = new HashSet<>();
                        s1.add(n);
                        s1.add(l);
                        s2.add(n);
                        s2.add(next2node);
                        s3.add(next2node);
                        s3.add(l);
                        coupleNodeTriangularEdge.get(s1).addTriangle(triangle);
                        coupleNodeTriangularEdge.get(s2).addTriangle(triangle);
                        coupleNodeTriangularEdge.get(s3).addTriangle(triangle);
                    }
                }
            }
        }
    }

    /**
     * Functie om de volgende Node terug te geven die volgt op m en n.
     * Deze Node is de Node die de volgende in combinatorische inbedding van @n ligt.
     *
     * @param @n Node
     * @param @m Node
     * @return Node
     */
    private Node gentNextNodeInCombinatorialEmbedding(Node n, Node m) {
        return combinatorialEmbedding.get(m).
                get(getNextIndexToTheRight(
                        combinatorialEmbedding.get(m).indexOf(n),
                        combinatorialEmbedding.get(m)));
    }

    /**
     * Stel je hebt Node met identity 3 en je wil de volgende node weten uit zijn comblijst. Dan neem je de eerste uit
     * zijn comblist, neem je die Node met die identity bvb 4, en ga je in die Node zijn lijst kijken en zijn index van uw Node3 + 1 doen.
     *
     * @param @indexCurrent van
     * @param @combArray
     * @return index van de volgende Node
     */
    private int getNextIndexToTheRight(int indexCurrent, ArrayList<Node> combArray) {
        int indexNextNode;
        if ((indexCurrent + 1) == combArray.size()) {
            indexNextNode = 0;
        } else {
            indexNextNode = indexCurrent + 1;
        }
        return indexNextNode;
    }

    public HashSet<Triangle> getTriangles() {
        return triangles;
    }

    public void printCombinatorialEmbedding() {
        for (Node n : nodes) {
            System.out.println("NODE: " + n.getIdentity());
            for (Node t : combinatorialEmbedding.get(n)) {
                System.out.print(t.getIdentity() + ", ");
            }
            System.out.println();

        }
    }

    private void printTriangles() {
        nnn.forEach(Triangle::printTriangle);
    }

    private void printTriangles2() {
        for (Triangle tr : triangles) {
            System.out.println(tr.getN1() == null);
        }
    }

    /**
     * Print de hamilton cycle uit naar standard.out.
     */
    public void printCycle(){
        HamiltonNode n = nodes.get(0).getHamiltonNode();
        int maxNodes = nodes.size();
        int i = 0;
        while(i != maxNodes){
            System.out.print(n.getHamiltonNode() + " ");
            n = n.next();
            i++;
        }
    }

    public void printC2(){
        for (Node n: hamiltoncykel){
            System.out.print(n.getIdentity() + " ");
        }

    }

    public static void main(String[] args) throws IOException {
        Hamilton test = null;
        InputData inputData = new InputData();
        for (Graph graph : inputData.getGraphs()) {
            test = new Hamilton(graph);

        }
        //System.out.println("Resultaat " + aantal + " " + inputData.getGraphs().size());

    }


}

