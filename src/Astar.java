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
    Node temp;
    Node start;
    Node end;
    Node[][] nodeGrid;


//    int z;

    double pathCost = 0;
    double diagonalDistance;
    double normalDistance;

    boolean isfound = false;
    boolean isManhattan = true;
    static String choice;


    public ArrayList<Node> ShortestPath(boolean[][] matrix, int si, int sj, int ei, int ej,String choices) {


         Scanner sc = new Scanner(System.in);
        choice = choices;

        switch (choice) {
            case "1":
                diagonalDistance = 1.4;
                normalDistance = 1.0;
                break;
            case "2":
                diagonalDistance = 2.0;
                normalDistance = 1.0;
                isManhattan = false;
                break;
            case "3":
                diagonalDistance = 1.0;
                normalDistance = 1.0;
                break;
            default:

                break;
        }




        long starttime = System.nanoTime();
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
//                    int di = i - end.getI();
//                    int dj = j - end.getJ();
//                    double heuristic = 1 * (di+dj);
//
//                    nodeGrid[i][j].sethValue(heuristic);


                }

            }
        }


        // setting start distance to 0.
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


//        int distart = Math.abs(si - ei);
//        int djstart = Math.abs(sj - ej);
//
//        double temphstart = 1 * (distart+djstart);
//
//        temp.sethValue(temphstart);
//        temp.setfValue(temphstart);
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
            if (current.getJ() - 1 > 0) {
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

            long spentTime = (System.nanoTime() - starttime) ;
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
