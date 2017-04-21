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


        Stopwatch timerFlow = new Stopwatch();

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

        // this comparator object for the open list Priority Queue
        //this will order the priority queue acceding order according to their f values
        Comparator<Node> adjacencyComparator = (left, right) -> {
            if (left.getfValue() > (right.getfValue())) {
                return 1;
            }
            return -1;
        };

        // Queue to store visiting nodes
        //in this queue first node will be the node that have least f value among other nodes on the queue
        Queue<Node> openList = new PriorityQueue<>(size, adjacencyComparator);

        //first add start node to the openList array
        openList.add(start);

        //this loop the code block while the openlist array list get empty it means each reachable node already visited
        while (openList.size() > 0) {

            //remove the head element (first element ) of the queue and assign that node to current variable
            Node current = openList.remove();

            //this variable to store neighbour nodes temporally
            Node temp;


            //TOP
            //if current node not in the top row check and add top neighbour nodes
            if (current.getI() - 1 >= 0) {

                // TOP - TOP
                //assign top value as the temp node (temporally neighbour)
                temp = nodeGrid[current.getI() - 1][current.getJ()];
                //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {

                    //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                    //assign current node is the parent of the temp node (neighbour)
                    //add temp node (neighbour) to the openlist queue
                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }

                if (isManhattan) {

                    // Top Left
                    //assign top left value as the temp node (temporally neighbour)
                    if (current.getJ() - 1 > 0) {
                        temp = nodeGrid[current.getI() - 1][current.getJ() - 1];
                        //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                        //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {

                            //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                            //assign current node is the parent of the temp node (neighbour)
                            //add temp node (neighbour) to the openlist queue
                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }

                    // Top Right
                    //assign top right value as the temp node (temporally neighbour)
                    if (current.getJ() + 1 < size) {
                        temp = nodeGrid[current.getI() - 1][current.getJ() + 1];
                        //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                        //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {

                            //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                            //assign current node is the parent of the temp node (neighbour)
                            //add temp node (neighbour) to the openlist queue
                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }
                }
            }

            // Left
            //assign left value as the temp node (temporally neighbour)
            if (current.getJ() - 1 >= 0) {
                temp = nodeGrid[current.getI()][current.getJ() - 1];
                //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {

                    //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                    //assign current node is the parent of the temp node (neighbour)
                    //add temp node (neighbour) to the openlist queue
                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }
            }

            // Right
            //assign right value as the temp node (temporally neighbour)
            if (current.getJ() + 1 < size) {
                temp = nodeGrid[current.getI()][current.getJ() + 1];
                //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {

                    //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                    //assign current node is the parent of the temp node (neighbour)
                    //add temp node (neighbour) to the openlist queue
                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }
            }
            // Down
            if (current.getI() + 1 < size) {

                // Down Down
                //assign down value as the temp node (temporally neighbour)
                temp = nodeGrid[current.getI() + 1][current.getJ()];
                //check that temp node is already visited , is it a blocked node or its old f value is less than new f value if it is skip that node
                //if it's not visited , not blocked or old f value not less than new f value then check and add that node to openlist
                if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + normalDistance) {


                    //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                    //assign current node is the parent of the temp node (neighbour)
                    //add temp node (neighbour) to the openlist queue
                    HVneighbor(temp, current, normalDistance);
                    temp.parent = current;
                    openList.add(temp);
                    test.add(temp);

                }


                if (isManhattan) {
                    // Down Left
                    //assign down left value as the temp node (temporally neighbour)
                    if (current.getJ() - 1 >= 0) {
                        temp = nodeGrid[current.getI() + 1][current.getJ() - 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                            //assign current node is the parent of the temp node (neighbour)
                            //add temp node (neighbour) to the openlist queue
                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }

                    // Down Right
                    //assign right value as the temp node (temporally neighbour)
                    if (current.getJ() + 1 < size) {
                        temp = nodeGrid[current.getI() + 1][current.getJ() + 1];
                        if (!temp.visited && !temp.blocked && temp.getfValue() > current.getfValue() + diagonalDistance) {


                            //call HVneighbor method and send temp node object, current node object and normal distance according to chosen distance calculation methodology
                            //assign current node is the parent of the temp node (neighbour)
                            //add temp node (neighbour) to the openlist queue
                            HVneighbor(temp, current, diagonalDistance);
                            temp.parent = current;
                            openList.add(temp);
                            test.add(temp);

                        }
                    }
                }
            }

            //mark current node as a visited node.visited nodes are not going to add again as neighbour node to the openList queue
            current.visited = true;

            //remove current node from the openList queue
            closedList.add(current);

            //check that destination node is found or not , if found break the while loop
            //if destination(finish) node isn't found yet continue the while loop
            if (current.getI() == end.getI() && current.getJ() == end.getJ()) {
                isfound = true;
                break;
            }
            //end of while loop
        }


//        if(!isfound){
//            System.out.println("No possible path");
////            closedList.clear();
//        }

        //create new array list that conatin the  return the shortest
        ArrayList<Node> path = new ArrayList<>();

        // first check that path is exists or not
        if (!(nodeGrid[end.getI()][end.getJ()].getfValue() == Integer.MAX_VALUE)) {

            //Trace back the path using parent nodes

            //add destination node to the path array list
            Node current = nodeGrid[end.getI()][end.getJ()];
            path.add(current);

            //calculate the cost of the shortest path using sum of g-values of path nodes
            pathCost = pathCost + current.getgValue();

            //arrange the path using parent nodes ,while current node became the starting node
            while (current.parent != null) {
                path.add(current.parent);
                current = current.parent;

            }

            //print out the cost
            System.out.println("******************************************************************************************************************");
            System.out.println("\n\t\t\t\tT O T A L _ C O S T : " + pathCost);
            System.out.println("\n******************************************************************************************************************");

            /*calculate the time from nano seconds that taken to get the shortest path
            long spentTime = (System.nanoTime() - starttime);
            StdOut.println("\t\t*time taken to evaluate the shortest path  = " + spentTime + "ns");*/




        } else {
            System.out.println("******************************************************************************************************************");
            System.out.println("\n\n:(\t\t\t\tN O _ P O S S I B L E _ P A T H \n\n");
            System.out.println("******************************************************************************************************************");
            StdOut.println("\t\t*time taken to evaluate = " + timerFlow.elapsedTime() + "ms");

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
