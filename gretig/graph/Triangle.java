package gretig.graph;

import java.util.Arrays;

/**
 * Created by llion on 25/11/16.
 */
public class Triangle {
    private int vertixA = 0;
    private int vertixB = 0;
    private int vertixC = 0;

    private Node n1;
    private Node n2;
    private Node n3;

    public boolean isInCycle() {
        return isInCycle;
    }

    public void setInCycle() {
        isInCycle = true;
    }

    private boolean isInCycle;

    private Node[] l;



    public Triangle(Node vertixA, Node vertixB, Node vertixC) {
        this.vertixA = getMax(vertixA.getIdentity(), vertixB.getIdentity(), vertixC.getIdentity());
        this.vertixC = getMin(vertixA.getIdentity(), vertixB.getIdentity(), vertixC.getIdentity());
        getMiddle(vertixA.getIdentity(), vertixB.getIdentity(), vertixC.getIdentity());
        Node[] list = {vertixA, vertixB, vertixC};
        determineN1(list);
        determineN2(list);
        determineN3(list);
        l = new Node[]{n1, n2, n3};
        isInCycle = false;
    }

    private int getMax(int a, int b, int c) {
        return Math.max(Math.max(a, b), c);
    }

    private int getMin(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private void determineN1(Node[] lijst){
        for (Node n: lijst){
            if (n.getIdentity() == this.getVertixA()){
                n1 = n;
            }
        }
    }

    private void determineN2(Node[] lijst){
        for (Node n: lijst){
            if (n.getIdentity() == this.getVertixB()){
                n2 = n;
            }
        }
    }

    private void determineN3(Node[] lijst){
        for (Node n: lijst){
            if (n.getIdentity() == this.getVertixC()){
                n3 = n;
            }
        }
    }

    private void getMiddle(int a, int b, int c){
        int[] l = {a, b, c};
        for (int aL : l) {
            if (!(aL == vertixA || aL == vertixC)) {
                this.vertixB = aL;
            }
        }
    }

    public void printTriangle(){
        System.out.println(vertixA + " " + vertixB + " " + vertixC);
    }

    public int getVertixA(){
        return vertixA;
    }

    public int getVertixB(){
        return vertixB;
    }

    public int getVertixC(){
        return vertixC;
    }

    public Node getN1(){
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public Node getN3() {
        return n3;
    }

    public Node getThirdNode(Node n1, Node n2){
        for (Node n: l){
            if (!(n.equals(n1)) && !(n.equals(n2))){
                return n;
            }
        }
        throw new IllegalArgumentException("Ligt niet in de lijst");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triangle triangle = (Triangle) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(l, triangle.l);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(l);
    }
}
