package gretig.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by llion on 25/11/16.
 */
public class TriangleEdge {
    private final int identity;
    private List<Triangle> triangles;
    private Set<Node> neighbores;
    private List<Node> nodesList;

    public List<Node> getNodesList() {
        return nodesList;
    }

    public TriangleEdge(int identity) {
        triangles = new ArrayList<>();
        neighbores = new HashSet<>();
        nodesList = new ArrayList<>();
        this.identity = identity;


    }

    public void removeTriangle(Triangle t) {
        this.triangles.remove(t);
    }

    public void addTriangle(Triangle t) {
        this.triangles.add(t);
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public void addNeighbores(Node n) {
        neighbores.add(n);
        nodesList.add(n);
    }

    public boolean hastriangles() {
        if (triangles.isEmpty()) {
            return false;
        } else if (triangles.size() == 1) {
            return !triangles.get(0).isInCycle();
        } else {
            return !triangles.get(0).isInCycle() || !triangles.get(1).isInCycle();

        }
    }

    public Node getNeighboure(Node n) {
        for (Node e : neighbores) {
            if (!e.equals(n)) {
                return e;
            }
        }
        return null;
    }

    public Triangle getRemaininTriangle() {
        for (Triangle tr : triangles) {
            if (!tr.isInCycle()) {
                return tr;
            }
        }
throw new IllegalArgumentException();    }

    public void printNeighbours() {
        for (Node n : neighbores) {
            System.out.print(n.getIdentity() + ", ");
        }
        System.out.println();
    }

    public Set<Node> getNeighbores() {
        return neighbores;
    }

    public int identity() {

        return identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriangleEdge that = (TriangleEdge) o;

        return identity == that.identity();

    }

    @Override
    public int hashCode() {
        return identity;
    }
}
