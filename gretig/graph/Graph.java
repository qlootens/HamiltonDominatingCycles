package gretig.graph;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quinten on 19/10/16.
 */
public class Graph {
    private List<Node> nodes;
    private int amountNodes;
    private int amountEdges;

    public Graph(int amountNodes, int amountEdges, List<Node> nodes){
        this.amountEdges = amountEdges;
        this.amountNodes = amountNodes;
        this.nodes = nodes;
        for (Node n: this.nodes){
            n.updateNeighboresNode();
        }
    }

    public void printGraph(){
        for (Node n: nodes){
            System.out.println("Node" +n.getIdentity()+ " has " + n.getNeighbors().size() + " neighbores! " + n.getStatus() );
            n.printNeighborsIdentities();
            n.printEdgesList();
        }
    }

    public List<Node> getNodes(){
        return new ArrayList<>(nodes);
    }

}
