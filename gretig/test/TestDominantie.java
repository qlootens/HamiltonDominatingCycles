package gretig.test;

import gretig.graph.Graph;
import gretig.graph.Node;
import gretig.Dominantie;
import gretig.parser.InputData2;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by quinten on 29/10/16.
 */
public class TestDominantie {

    String[] testLijst = {
            "gretig/voorbeelden/graaf1.sec",
            "gretig/voorbeelden/graaf2.sec",
            "gretig/voorbeelden/graaf3.sec",
            "gretig/voorbeelden/graaf4.sec",
            "gretig/voorbeelden/graaf5.sec",
            "gretig/voorbeelden/graaf6.sec",
            "gretig/voorbeelden/graaf7.sec",
            "gretig/voorbeelden/graaf8.sec",
            "gretig/voorbeelden/triang1.sec",
            "gretig/voorbeelden/triang2.sec"
    };


    /**
     * @param adres String met het adres waar het bestand van de graaf staat in SIMPLE EDGE CODE [.sec files]
     * @return True: Als de bekomen verzameling een dominerende verzameling is van de Graaf
     * False: Indien niet zo
     * @throws IOException
     */
    public boolean testDominanteVerzameling(String adres) throws IOException {
        InputData2 graaf1 = new InputData2(adres);
        InputData2 graaf2 = new InputData2(adres);
        Graph gr1 = graaf1.getGraphs().get(0);
        Dominantie dominantie = new Dominantie(graaf2.getGraphs().get(0));
        Set<Node> dominanteverzameling = dominantie.getDominantSet();
        List<Node> nodes = gr1.getNodes();
        Set<Node> fillNodes = new HashSet<>();

        for (Node node : nodes) {
            if (dominanteverzameling.contains(node)) {
                for (Node node1 : node.getNeighbors()) {
                    fillNodes.add(node1);
                }
                fillNodes.add(node);
            }
        }
        System.out.println(adres + " " + dominantie.getDominantSet().size());
        return nodes.size() == fillNodes.size();
    }

    @Test
    public void testDominantie() throws IOException {
        for (int i = 0; i < testLijst.length; i++) {
            assertEquals("geen dominantieverzameling", Boolean.toString(true), Boolean.toString(testDominanteVerzameling(testLijst[i])));

        }
    }


}
