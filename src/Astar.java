import java.util.*;

/**
 * Created by Pavith Buddhima on 3/31/2017.
 * UOW no - w1608462
 * IIT no -2015238
 */
public class Astar {

    //this array list add nodes that need to visit
    ArrayList<Node> openList = new ArrayList<Node>();
    //this array list to add nodes that already visited, once node added to this node we never going to revisit that node again
    ArrayList<Node> closedList = new ArrayList<Node>();

    // this is an additional array list maintain for visualize the visiting nodes, to find the path we dont need to keep this array list
    ArrayList<Node> test = new ArrayList<Node>();

    //this variable for identify the current node on the grid
    Node current;
    //this node will be a parent node for the current node what mention above
    Node temp;
    //this start variable to initialize starting node of the grid, ( first point given user )
    Node start;
    //this end variable to initialize destination node of the grid, ( second point given user )
    Node end;
    //this 2d array  represent the  generated grid of nodes
    Node[][] nodeGrid;


//    int z;

    //to get the final cost of the shortest path
    double pathCost = 0;
    //distance that need to increment for diagonal move
    double diagonalDistance;
    //distance that need to increment for non-diagonal move
    double normalDistance;

    boolean isfound = false;
    boolean isManhattan = true;
    static String choice;

    /**
     * this ShortestPAth method return the shortest path of a given matrix and this method need six parameters first is matrix(grid) that
     * use to find the path, starting point i coordinates and j coordinates , end point i coordinates and j coordinates and finally
     * user selected method that use to calculate the distance of the shortest path
     **/
    public ArrayList<Node> ShortestPath(boolean[][] matrix, int si, int sj, int ei, int ej, String choices) {

/*create scanner object
         Scanner sc = new Scanner(System.in);*/

//initialize choice variable with user sekected method
        choice = choices;
//if user select Euclidean   set diagonal distance and normal distance for next node
        switch (choice) {
            case "1":
                diagonalDistance = 1.4;
                normalDistance = 1.0;
                break;

//if user select Manhattan set diagonal distance and normal distance for next node
            case "2":
                diagonalDistance = 2.0;
                normalDistance = 1.0;
                isManhattan = false;
                break;

//if user select Chebyshev set diagonal distance and normal distance for next node
            case "3":
                diagonalDistance = 1.0;
                normalDistance = 1.0;
                break;
            default:

                break;
        }


//to get the nano time of the algorithm
        long starttime = System.nanoTime();

//        initialize size variable with the size of the grid
        int size = matrix.length;

//initialise the start node of the matrix
        start = new Node(si, sj);

//        initialize the end node of the grid(matrix)
        end = new Node(ei, ej);


//           The grid that is used to store nodes , and initialize the size of the grid

        nodeGrid = new Node[size][size];


//         Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid
//          this for loops iterate through the every node of the grid and initialize them as blocked or not
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {


                nodeGrid[i][j] = new Node(i, j);
                if (matrix[i][j] == false) {
//                    node set as a blocked node because it's a unreachable square
                    nodeGrid[i][j].setBlocked(true);

                }

            }
        }


        // setting start node g cost . h cost and f cost to 0.
        // All other nodes will have infinity distance at the beginning
        start.setfValue(0);
        start.setgValue(0);
        start.sethValue(0);

        // a comparator object to deal with Priority Queue
        Comparator<Node> adjacencyComparator = (left, right) -> {
            if (left.getfValue() > (right.getfValue())) {
                return 1;
            }
            return -1;
        };

        // Queue to store visiting nodes
        Queue<Node> openList = new PriorityQueue<>(size, adjacencyComparator);


        openList.add(start);

        while (openList.size() > 0) {


            Node current = openList.remove();
            Node temp;


            // Top
            if (current.getI() - 1 >= 0) {

                // Top Top
                temp = nodeGrid[current.getI() - 1][current.getJ()];
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {


                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }

                if (isManhattan) {

                    // Top Left
                    if (current.getJ() - 1 > 0) {
                        temp = nodeGrid[current.getI() - 1][current.getJ() - 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }

                    // Top Right
                    if (current.getJ() + 1 < size) {
                        temp = nodeGrid[current.getI() - 1][current.getJ() + 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }
                }
            }

            // Left
            if (current.getJ() - 1 >= 0) {
                temp = nodeGrid[current.getI()][current.getJ() - 1];
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {


                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }
            }

            // Right
            if (current.getJ() + 1 < size) {
                temp = nodeGrid[current.getI()][current.getJ() + 1];
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {


                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }
            }
            // Down
            if (current.getI() + 1 < size) {

                // Down Down
                temp = nodeGrid[current.getI() + 1][current.getJ()];
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {


                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }


                if (isManhattan) {
                    // Down Left
                    if (current.getJ() - 1 >= 0) {
                        temp = nodeGrid[current.getI() + 1][current.getJ() - 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }

                    // Down Right
                    if (current.getJ() + 1 < size) {
                        temp = nodeGrid[current.getI() + 1][current.getJ() + 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }
                }
            }

            current.visited = true;
            closedList.add(current);
            if (current.getI() == end.getI() && current.getJ() == end.getJ()) {
                isfound = true;
                break;
            }
        }


//        if(!isfound){
//            System.out.println("No possible path");
////            closedList.clear();
//        }

        ArrayList<Node> path = new ArrayList<>();

        // Checking if a path exists
        if (!(nodeGrid[end.getI()][end.getJ()].getfValue() == Integer.MAX_VALUE)) {
            //Trace back the path
            Node current = nodeGrid[end.getI()][end.getJ()];
            path.add(current);
            pathCost = pathCost + current.getgValue();

            while (current.parent != null) {
                path.add(current.parent);
                current = current.parent;

            }

            System.out.println("******************************************************************************************************************");
            System.out.println("\n\t\t\t\tT O T A L _ C O S T : " + pathCost);
            System.out.println("\n******************************************************************************************************************");

            long spentTime = (System.nanoTime() - starttime);
            StdOut.println("\t\t*time taken to evaluate the shortest path  = " + spentTime + "ns");
        } else {
            System.out.println("******************************************************************************************************************");
            System.out.println("\n\n:(\t\t\t\tN O _ P O S S I B L E _ P A T H \n\n");
            System.out.println("******************************************************************************************************************");
//            StdOut.println("\t\t*time taken to evaluate = " + timerFlow.elapsedTime() + "ms");

        }

//        StdDraw.setPenColor(Color.black);
//        StdDraw.text(end.x, 10-end.y-1, "cost="+(int)fcost);

        return path;


    }

    public ArrayList<Node> testing() {
        return test;

    }


    public void HVneighbor(Node temp, Node current, double distance) {


        double tempg = current.getgValue() + distance;

        temp.setgValue(tempg);

        double di, dj, temph = 0;

        if (choice.equals("1")) {

            temph = (Math.sqrt(Math.pow((temp.getI() - end.getI()), 2) + Math.pow((temp.getJ() - end.getJ()), 2)));


        } else if (choice.equals("2")) {


            di = Math.abs(temp.getI() - end.getI());
            dj = Math.abs(temp.getJ() - end.getJ());
            temph = 1 * (di + dj);


        } else if (choice.equals("3")) {

            temph = Math.max(Math.abs(temp.getI() - end.getI()), Math.abs(temp.getJ() - end.getJ()));


        }


        temp.sethValue(temph);
        temp.setfValue(tempg + temph);


    }


//        public void Diagonalneighbor(Node temp , Node current){
//
//            double tempg = current.getgValue() + diagonalDistance;
//
//            temp.setgValue(tempg);
//
//            double di,dj,temph=0 ;
//
//            if(choice.equals("1")) {
//
//
//                temph =  (Math.sqrt( Math.pow((temp.getI() - end.getI()) , 2) + Math.pow((temp.getJ() - end.getJ()) , 2)) );
//
//
//
//
//            }else if(choice.equals("2")){
//
//
//                di = Math.abs(temp.getI() - end.getI());
//                dj = Math.abs(temp.getJ() - end.getJ());
//                temph = 1 * (di+dj);
//
//
//            }else if(choice.equals("3")){
//
//                temph = Math.max ( Math.abs(temp.getI() - end.getI()) , Math.abs(temp.getJ() - end.getJ()));
//
//
//            }
//
//            temp.sethValue(temph);
//            temp.setfValue(tempg + temph);
//
//
//
//        }


}
