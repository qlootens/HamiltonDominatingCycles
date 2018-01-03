package gretig.parser;

import gretig.graph.Edge;
import gretig.graph.Graph;
import gretig.graph.Node;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by quinten on 28/10/16.
 */
public class InputData {
    private byte[] content;
    private List<Graph> graphs;
    private int amountNodes;
    private int amountEdges;

    public InputData() throws IOException {
        graphs = new ArrayList<>();
        inputByte();
        while (makeGraphs() != 0) {
            makeGraphs();
        }
    }

    private void inputByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[16384];

        int bytesRead;
        while ((bytesRead = System.in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        byte[] bytes = baos.toByteArray();

        content = Arrays.copyOfRange(bytes, 7, bytes.length);
    }

    private int translateLittleIndian(byte[] numbers, int amount) {
        int outputGetal = 0;
        double macht = 0;
        for (int i = 0; i < amount; i++) {
            outputGetal += (numbers[i] & 0xff) * (int) Math.pow(256.0, macht);
            macht++;
        }
        return outputGetal;
    }

    private void addNeighborEdge(Edge edge) {
        if (edge.getNodes().size() == 2) {
            edge.getNodes().get(0).addNeighbor(edge.getNodes().get(1));
            edge.getNodes().get(1).addNeighbor(edge.getNodes().get(0));
        }
    }

    private int makeGraphs() {
        ArrayList<Node> nodes = new ArrayList<>();
        int amountBytesTranslator = content[0];
        amountNodes = translateLittleIndian(makeList(content, 1, 1 + amountBytesTranslator), amountBytesTranslator);
        amountEdges = translateLittleIndian(makeList(content, 1 + amountBytesTranslator,
                1 + (2 * amountBytesTranslator)), amountBytesTranslator);
		if(amountEdges > 500000){
			return 0;
}
        Edge[] edges = new Edge[amountEdges];
        for (int m = 0; m < edges.length; m++) {
            edges[m] = new Edge(m + 1);
        }
        content = makeList(content, 1 + (2 * amountBytesTranslator), content.length);
        int index = 0;
        int tmp = 0;
        int identityNode = 1;
        Node node = new Node(identityNode);
        nodes.add(node);
        try {
            while (nodes.size() != amountNodes) {
                tmp = translateLittleIndian(makeList(content, index, index + amountBytesTranslator), amountBytesTranslator);
                index += amountBytesTranslator;
                if (tmp != 0) {

                    node.addEdge(edges[tmp - 1]);
                    edges[tmp - 1].addNode(node);
                    addNeighborEdge(edges[tmp - 1]);
                } else {
                    identityNode++;
                    node = new Node(identityNode);
                    nodes.add(node);
                }
            }
        } catch (Exception e) {
            return 0;
        }
        tmp = translateLittleIndian(makeList(content, index, index + amountBytesTranslator), amountBytesTranslator);
        index += amountBytesTranslator;
        while (tmp != 0) {
            node.addEdge(edges[tmp - 1]);
            edges[tmp - 1].addNode(node);
            addNeighborEdge(edges[tmp - 1]);
            tmp = translateLittleIndian(makeList(content, index, index + amountBytesTranslator), amountBytesTranslator);
            index += amountBytesTranslator;
        }

        graphs.add(new Graph(amountNodes, amountEdges, nodes));

        if (index != content.length) {
            content = makeList(content, index, content.length);
            return 1;
        }

        return 0;
    }

    public List<Graph> getGraphs() {
        return this.graphs;
    }


    /**
     * Functie die een deellijst maakt met end exclusief
     *
     * @param content byte[] lijst waar een deellijst van gemaakt wordt.
     * @param start start index
     * @param end end index
     * @return byte[] deellijst van content
     */
    private byte[] makeList(byte[] content, int start, int end) {
        byte[] output = new byte[end - start];
        for (int i = 0; i < output.length; i++) {
            output[i] = content[start + i];
        }
        return output;
    }


}
