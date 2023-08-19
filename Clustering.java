import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;
import java.util.Collections;

public class Clustering 
{
    private static double xMax, yMax;
    private static ArrayList<double[]> points;
    private double[][] clusterPoints;
    private ArrayList<double[]>[] temp;

    public Clustering(int k)
    {
        //points = new ArrayList<double[]>();
        clusterPoints = new double[k][2];
        //xMax = yMax = -1.0;
        temp = new ArrayList[k];
        for (int i = 0; i < k; i++)
        {
            ArrayList<double[]> temp2 = new ArrayList<double[]>();
            temp[i] = temp2;
        }
    }

    public static void main(String[] args)
    {
        Clustering[] temp2 = new Clustering[10000];
        temp2[0] = new Clustering(Integer.parseInt(args[0]));
        temp2[0].extractPoints();
        temp2[0].calculateMax();
        if (args.length > 0)
        {
            for (int i =0; i < temp2.length; i++)
            {
                temp2[i] = new Clustering(Integer.parseInt(args[0]));
                temp2[i].run();
            }
        }
        else
        {
            for (int i =0; i < temp2.length; i++)
            {
                temp2[i] = new Clustering(2);
                temp2[i].run();
            }
        }

        double min = temp2[0].calculateVariation();
        int ind = 0;
        for (int j = 1; j < temp2.length; j++)
        {
            double var = temp2[j].calculateVariation();
            if (var < min)
            {
                min = var;
                ind = j;
            }
        }

        Clustering ultimate = temp2[ind];
        // for (int i = 0; i < ultimate.temp.length; i ++)
        // {
        //     System.out.println("Cluster Point: " + ultimate.clusterPoints[i][0] + " " + ultimate.clusterPoints[i][1]);
        //     for (double[] arr : ultimate.temp[i])
        //     {
        //         System.out.println(arr[0] + " " + arr[1]);
        //     }
        // }

        StdDraw.setCanvasSize((int)ultimate.xMax+10, (int)ultimate.yMax+10);
		StdDraw.setXscale(0, ultimate.xMax+10);
		StdDraw.setYscale(0, ultimate.yMax+10);
        StdDraw.enableDoubleBuffering();


        // System.out.println("\n\nVariation: " + ultimate.calculateVariation());
        for (int i = 0; i < ultimate.temp.length; i++)
        {
            Collections.shuffle(ultimate.temp[i]);
            AdjacencyMatrix m = new AdjacencyMatrix(ultimate.temp[i]);
            m.run();
            ListNode<Integer> first = m.getFirst();
            ListNode<Integer> temp5 = first;
            if (temp5 != null)
            {
                while(temp5.getNext() != null)
                {
                    StdDraw.line(ultimate.temp[i].get(temp5.getValue())[0], ultimate.temp[i].get(temp5.getValue())[1], 
                    ultimate.temp[i].get(temp5.getNext().getValue())[0], ultimate.temp[i].get(temp5.getNext().getValue())[1]);
                    temp5 = temp5.getNext();
                }
                StdDraw.line(ultimate.temp[i].get(temp5.getValue())[0], ultimate.temp[i].get(temp5.getValue())[1], 
            ultimate.temp[i].get(first.getValue())[0], ultimate.temp[i].get(first.getValue())[1]);
            }

            /**
             * prints out order of points (shortest path)
             */
            ListNode<Integer> temp10 = first;
            while (temp10!=null)
            {
                System.out.println(ultimate.temp[i].get(temp10.getValue())[0] + " " + ultimate.temp[i].get(temp10.getValue())[1]);
                temp10 = temp10.getNext();  
            }
        }

        StdDraw.setFont(new Font("serif", 1, 13));
        for (int i = 0; i < ultimate.temp.length; i ++)
        {
            StdDraw.setPenColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
            for (double[] arr : ultimate.temp[i])
            {
                StdDraw.filledCircle(arr[0], arr[1],4);
                StdDraw.text(arr[0], arr[1], "" + arr[0] + "," + arr[1]);
            }
        }
        StdDraw.show();
        System.out.println(ultimate.calculateVariation());
    }

    public void run()
    {
        //extractPoints();
        //calculateMax();
        determineClusters();
        seperate();
        // for (int i = 0; i < temp.length; i ++)
        // {
        //     System.out.println("Cluster Point: " + clusterPoints[i][0] + " " + clusterPoints[i][1]);
        //     for (double[] arr : temp[i])
        //     {
        //         System.out.println(arr[0] + " " + arr[1]);
        //     }
        // }
    }

    public void extractPoints()
    {
        points = new ArrayList<double[]>();
        String file = "p50.txt";
        Scanner inFile = OpenFile.openToRead(file);

        while(inFile.hasNext())
		{
			double x = inFile.nextDouble();
			double y = inFile.nextDouble();

            double[] temp = new double[2];
            temp[0] = x;
            temp[1] = y;

            points.add(temp);
		}
    }

    public void calculateMax()
    {
        for (int i = 0; i < points.size(); i++)
        {
            if (points.get(i)[0] > xMax) xMax = points.get(i)[0];
            if (points.get(i)[1] > yMax) yMax = points.get(i)[1];
        }
    }

    public void determineClusters()
    {
        for (int i = 0; i < clusterPoints.length; i++)
        {
            double[] temp = {Math.random()*xMax, Math.random()*yMax};
            while (isMatching(temp))
            {
                temp[0] = Math.random()*xMax;
                temp[1] = Math.random()*yMax;
            }
            clusterPoints[i] = temp;
        }
    }

    private boolean isMatching(double[] arr)
    {
        for (int i = 0; i < clusterPoints.length; i++)
        {
            if (clusterPoints[i][0] == arr[0] && clusterPoints[i][1] == arr[1])
            {
                return true;
            }
        }
        return false;
    }

    public void seperate()
    {
        for (int i = 0; i < points.size(); i++)
        {
            double max = Double.MAX_VALUE;
            int ind = -1;
            for (int j = 0; j < clusterPoints.length; j++)
            {
                double dist = Math.sqrt(Math.pow(points.get(i)[0] - clusterPoints[j][0], 2) + Math.pow(points.get(i)[1] - clusterPoints[j][1], 2));
                if  (dist < max)
                {
                    max = dist;
                    ind = j;
                }
            }
            temp[ind].add(points.get(i));
        }
    }

    public double calculateVariation()
    {
        double val = 0.0;
        for (int i = 0; i < temp.length; i ++)
        {
            for (double[] arr : temp[i])
            {
                val += Math.sqrt(Math.pow(arr[0] - clusterPoints[i][0], 2) + Math.pow(arr[1] - clusterPoints[i][1], 2));
                //System.out.println(arr[0] + " " + arr[1]);
            }
        }
        return val;
    }
}