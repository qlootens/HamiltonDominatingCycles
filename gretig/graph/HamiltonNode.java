package gretig.graph;

/**
 * Created by llion on 26/11/16.
 */
public class HamiltonNode {
    private HamiltonNode previous;
    private HamiltonNode next;
    private final int identity;

    public HamiltonNode(int identity){
        this.identity = identity;
    }

    public int getHamiltonNode(){
        return identity;
    }

    public void setNext(HamiltonNode n) {
        this.next = n;
    }

    public HamiltonNode next() {
        return this.next;
    }


    public void setPrevious(HamiltonNode n) {
        this.previous = n;
    }

    public HamiltonNode previous() {
        return this.previous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HamiltonNode that = (HamiltonNode) o;

        return identity == that.identity;

    }

    @Override
    public int hashCode() {
        return identity;
    }



}
