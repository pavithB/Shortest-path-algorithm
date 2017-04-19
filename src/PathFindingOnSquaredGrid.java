import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*************************************************************************
 *  Original Author: Dr E Kapetanios
 *  Last update: 22-02-2017
 *
 *  Edited by: Pavith Buddhima on 3/31/2017.
 *  S.G.V.P.B.Gunasekara
 *  UOW no - w1608462
 *  IIT no -2015238
 *
 *************************************************************************/


public class PathFindingOnSquaredGrid {


    static  int nValue = 10;
    static final double ratio = 0.8;
//    static final boolean textCordinates = true;

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top


    static boolean[][] randomlyGenMatrix;

    static String choice;




    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) return true;
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which) {
                    if(nValue>=15 && nValue<22){
                        StdDraw.setFont(new Font("TimesRoman", Font.BOLD , 8));
                        StdDraw.text(j, N - i - 1, "(" + i + "," + j + ")");
                    }
                    if (nValue<15) {
                        StdDraw.setFont(new Font("TimesRoman", Font.BOLD , 10));
                        StdDraw.text(j, N - i - 1, "(" + i + "," + j + ")");
                    }
                    StdDraw.square(j, N - i - 1, .5);

                } else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);

//        StdDraw.line(10-x2-1,y2,10-x1-1,y1);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.filledCircle(j, N - i - 1, .5);
                        StdDraw.setPenColor(StdDraw.BLACK);
                    } else StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }


    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void showfinal(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);

//        StdDraw.line(10-x2-1,y2,10-x1-1,y1);

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.filledCircle(j, N - i - 1, .5);
                        StdDraw.setPenColor(StdDraw.BLACK);
                    }

    }


    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }


    // test client
    public static void main(String[] args) {


        while (true) {
            //start of the main method
            System.out.println("******************************************************************************************************************");
            System.out.println("\n\n\t\t\t\t\t\t\t\t T H E _S H O R T E S T _ P A T H \n\n");
            System.out.println("******************************************************************************************************************");

//get user value to set the size of the grid and set it
            Scanner in = new Scanner(System.in);
            System.out.println("\nE N T E R _ T H E _ S I Z E _ O F _ T H E _ G R I D :");
            nValue = in.nextInt();

            // boolean[][] open = StdArrayIO.readBoolean2D();

            // The following will generate a 10x10 squared grid with relatively few obstacles in it
            // The lower the second parameter, the more obstacles (black cells) are generated
            randomlyGenMatrix = random(nValue, ratio);

            StdArrayIO.print(randomlyGenMatrix);
            show(randomlyGenMatrix, true);

//        System.out.println();
//        System.out.println("The system percolates: " + percolates(randomlyGenMatrix));
//
//        System.out.println();
//        System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
//        System.out.println();

            // Reading the coordinates for points A and B on the input squared grid.

            // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
            // Start the clock ticking in order to capture the time being spent on inputting the coordinates
            // You should position this command accordingly in order to perform the algorithmic analysis

//this part to get cordinates for start node and end note , to that we use user inputs ,
            boolean validCordinates = false;
            int Ai = 0, Aj = 0, Bi = 0, Bj = 0;

            int inputCount = 0;
//this while loop continue loop until usr enter valid cordinants , and thos nodes cant be a black nodes
            do {
                if (inputCount != 0) {
                    System.out.println("\n\t\tP L E A S E _ E N T E R _ V A L I D _ C O R D I N A T E S\n");
                }

                inputCount++;

//get cordinanets for i and j axix for starting point and end point
                System.out.println("\nEnter i for    S T A R T I N G _ P O I N T :");
                Ai = in.nextInt();
                if (Ai >= nValue) {
//                    if user enter less number thatn the grid size
                    System.out.println("\n\tplease R E - E N T E R coordinates below " + nValue);
                    continue;
                }

                System.out.println("Enter j for    S T A R T I N G _ P O I N T :");
                Aj = in.nextInt();
                if (Aj >= nValue) {
                    System.out.println("\n\tplease R E - E N T E R coordinates below " + nValue);
                    continue;
                }

                System.out.println("Enter i for    E N D _ P O I N T  :");
                Bi = in.nextInt();
                if (Bi >= nValue) {
                    System.out.println("\n\tplease R E - E N T E R coordinates below " + nValue);
                    continue;
                }

                System.out.println("Enter j for    E N D _ P O I N T :");
                Bj = in.nextInt();
                if (Bj >= nValue) {
                    System.out.println("\n\tplease R E - E N T E R coordinates below " + nValue);
                    continue;
                } else {
                    validCordinates = true;
                }

            }
            while ((validCordinates == false) || randomlyGenMatrix[Ai][Aj] == false || randomlyGenMatrix[Bi][Bj] == false);

            inputCount = 0;

            String choices = "" ;
            System.out.println("******************************************************************************************************************");
            System.out.println("\n\n\t\tC H O O S E _ A _ M E T R I C _ F O R _ C A L C U L A T E _ T H E _ D I S T A N C E: \n\n\t\t\t\t\t1)Euclidean distance \n\n\t\t\t\t\t2)Manhattan distance\n\n\t\t\t\t\t3)Chebyshev distance\n");
            choice = in.next();
            switch (choice) {
                case "1":
                    choices="1";
                    break;
                case "2":
                    choices="2";
                    break;
                case "3":
                    choices="3";

                    break;
                default:

                    break;
            }

            // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
            // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
            // You should position this command accordingly in order to perform the algorithmic analysis


            // System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
            // System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");

            show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

//create a object of astar class
            Astar testing = new Astar();

//create path array and make retunr the paths
            Stopwatch timerFlow = new Stopwatch();
            ArrayList<Node> path = testing.ShortestPath(randomlyGenMatrix, Ai, Aj, Bi, Bj,choices);
//        ArrayList<Node> path = new Astar().ShortestPath(randomlyGenMatrix, Ai, Aj, Bi, Bj);

            ArrayList<Node> testarray = testing.testing();

            StdDraw.setPenColor(Color.green);

            int i = 0;
            int prex = 0;
            int prey = 0;

            Collections.reverse(path);

            for (Node dd : testarray) {

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                StdDraw.filledSquare(dd.getJ(), nValue - dd.getI() - 1, .5);
            }
            StdDraw.setPenColor(Color.RED);
            for (Node node : path) {

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i == 0) {
                    i++;
                    prey = node.getJ();
                    prex = node.getI();
                } else {

//                StdDraw.filledSquare(node.y, 10 - node.x - 1, .5);

                    StdDraw.setPenRadius(0.01);
                    StdDraw.line(prey, nValue - prex - 1, node.getJ(), nValue - node.getI() - 1);

                    prey = node.getJ();
                    prex = node.getI();

                }
            }
            StdOut.println("\t\t*time taken to evaluate the shortest path  = " + timerFlow.elapsedTime() + "ms");
            showfinal(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

            System.out.println("\n\n\t\tP R E S S _ 0 _ T O _ E X I T");

            System.out.println("\n\n\t\tP R E S S _ A N Y _ (E X C E P T _ 0) _ T O _ C O N T I N U E");

            String commnd = in.next();
            StdDraw.clear();
            if (commnd.equals("0")) {
                System.exit(0);
            }

        }

/**        this for find shortest path using dijkstar algorithm + visualization
 *
 *            Dijkstra testing = new Dijkstra();

 ArrayList<Dijkstra.Node> path = testing.distance(randomlyGenMatrix, Ai, Aj, Bi, Bj);
 //        ArrayList<Node> path = new Astar().ShortestPath(randomlyGenMatrix, Ai, Aj, Bi, Bj);
 StdOut.println("time to find the shortest path  = " + timerFlow.elapsedTime());
 ArrayList<Dijkstra.Node> testarray = testing.testing();
 StdDraw.setPenColor(Color.BLUE);

 int i = 0;
 int prex = 0;
 int prey = 0;

 Collections.reverse(path);

 for (Dijkstra.Node dd : testarray) {

 try {
 TimeUnit.MILLISECONDS.sleep(10);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

 StdDraw.filledSquare(dd.getJ(), nValue - dd.getI() - 1, .5);
 }
 StdDraw.setPenColor(Color.RED);
 for (Dijkstra.Node node : path) {

 try {
 TimeUnit.MILLISECONDS.sleep(100);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

 if (i == 0) {
 i++;
 prey = node.getJ();
 prex = node.getI();
 } else {

 //                StdDraw.filledSquare(node.y, 10 - node.x - 1, .5);

 StdDraw.setPenRadius(0.01);
 StdDraw.line(prey, nValue - prex - 1, node.getJ(), nValue - node.getI() - 1);

 prey = node.getJ();
 prex = node.getI();

 }
 }

 System.out.println("\n\n\tpress 0 to exit ");

 System.out.println("\n\n\tpress 0 natuwa wena ekk to continue ");

 String commnd = in.next();
 StdDraw.clear();
 if (commnd.equals("0")){
 System.exit(0);
 }

 */

    }

}



