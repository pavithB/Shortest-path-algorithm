/**
 * Created bj Pavith Buddhima on 4/1/2017.
 * UOW no - w1608462
 * IIT no -2015238
 */
public class Node {

    //i coordinates for the node
    private int i;
    //j coordinates for the node
    private int j;

    // heuristic value for the node
    private double hValue;

    //this store the g cost for the node
    private double gValue;

    //f value is the sum of h value and g value for that node
    private double fValue = Integer.MAX_VALUE;

    //    double distance = Integer.MAX_VALUE;

    Node parent = null;

    boolean visited;

    boolean blocked;


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public double gethValue() {
        return hValue;
    }

    public void sethValue(double hValue) {
        this.hValue = hValue;
    }

    public double getgValue() {
        return gValue;
    }

    public void setgValue(double gValue) {
        this.gValue = gValue;
    }

    public double getfValue() {
        return fValue;
    }

    public void setfValue(double fValue) {
        this.fValue = fValue;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    public Node(int i, int j) {
        this.i = i;
        this.j = j;
    }
}


