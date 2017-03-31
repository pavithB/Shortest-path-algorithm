import java.util.ArrayList;

/**
 * Created by Pavith Buddhima on 3/31/2017.
 */
public class Astar {

    static ArrayList<Node> openList = new ArrayList<Node>();
    static ArrayList<Node> closedList = new ArrayList<Node>();

    Node current ;
    Node temp;
    Node start ;
    Node end;
    Node[][] nodeGrid;



    public void ShortestPath(boolean[][] matrix, int si, int sj, int ei, int ej){


        int size = matrix.length;

        start = new Node(si, sj);
        end = new Node(ei, ej);

        // The grid that is used to store nodes

        nodeGrid = new Node[size][size];

        // Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {


                nodeGrid[i][j] = new Node(i, j);
                if (matrix[i][j] == false) {
                    nodeGrid[i][j].setBlocked(true);
                    //calculate the f value and set hvalue
                    int di = i - end.getI();
                    int dj = j - end.getJ();
                    double heuristic = 1 * (di+dj);

                    nodeGrid[i][j].sethValue(heuristic);


                }

            }
        }



    }



}
