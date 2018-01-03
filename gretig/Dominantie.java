package gretig;

import gretig.graph.Graph;
import gretig.graph.Node;
import gretig.parser.InputData;
import gretig.parser.InputData2;

import java.io.IOException;
import java.util.*;

/**
 * Created by quinten on 16/10/16.
 * Voor meer uitleg bij de onderstaande code, zie mijn verslag.
 */
public class Dominantie {

    private Set<Node> dominantSet;
    private Graph graph;
    private Set<Node> uncoveredNodes;
    ArrayList<Node> nodes;

    public Dominantie(Graph graph){
        this.graph = graph;
        dominantSet = new HashSet<>();
        uncoveredNodes = new HashSet<>();
        nodes = new ArrayList<>();
        for (Node n: graph.getNodes()){
            uncoveredNodes.add(n);
            nodes.add(n);

        }
    }

    /**
     * Voor meer informatie bij de werking van het algoritme, zie mijn verslag.
     * @return Dominerende verzameling van gretig.gretig.graph
     */
    public Set<Node> getDominantSet(){
        for (Node node: nodes){
            if (node.getNeighbors().size() == 1 && node.getStatus().equals("uncovered")){
                Node mp = node.getEdges().get(0).getNeighbor(node);
                mp.markConnected(uncoveredNodes);
                dominantSet.add(mp);
            }
        }

        Node tmp;
        while (! uncoveredNodes.isEmpty()){
            tmp = getMaximumOfUncoveredNeighbors(getNextUncoveredNode());
            tmp.markConnected(uncoveredNodes);
            dominantSet.add(tmp);
            tmp = null;
        }
        return dominantSet;
    }

    private Node getNextUncoveredNode(){
        for (Node un: uncoveredNodes){
            return un;
        }
        return null;
    }

    public void printNodes(){
        Set<Node> nodes = getDominantSet();
        for (Node nd: nodes){
            System.out.print(nd.getIdentity() + " ");
        }
        System.out.println();
    }

    private Node getMaximumOfUncoveredNeighbors(Node node){
        int max = node.getUncoveredNeighbores();
        int tmp;
        Node maxNode = node;
        for (Node n: node.getNeighbors()){
            tmp = n.getUncoveredNeighbores();
            if (max < tmp){
                max = tmp;
                maxNode = n;
            }
        }
        return maxNode;
    }

    public static void main(String[] args) throws IOException {
        InputData inputData2 = new InputData();
        for (Graph graph: inputData2.getGraphs()){
            new Dominantie(graph).printNodes();
        }
    }


}
