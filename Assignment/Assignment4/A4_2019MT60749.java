import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;

public class A4_2019MT60749 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(args[0]));
        Scanner scan1 = new Scanner(new File(args[1]));
        Scanner scan2 = new Scanner(args[2]);
        Vector<String> v =new Vector();
	String[] s;
        boolean isheader = true;
        while(scan.hasNext())
        {
            s = scan.nextLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
	    if(isheader)
            {
                isheader = false;
                continue;
            }
            if(s[1].charAt(0)=='\"')
                s[1] =s[1].substring(1, s[1].length()-1);
	    v.add(s[1]);
        }
        scan.close();
        isheader = true;
        Vector<String[]> Edges = new Vector<String[]>();
        HashMap<String,Integer> IsPresent = new HashMap<String,Integer>();
        while(scan1.hasNext())
        {
            s = scan1.nextLine().split(",(?=([^\"]|\"[^\"]*\")*$)");
	    if(isheader)
            {
                isheader = false;
                continue;
            }
            if(s[0].charAt(0)=='\"')
                s[0] =s[0].substring(1, s[0].length()-1);
            if(s[1].charAt(0)=='\"')
                s[1] =s[1].substring(1, s[1].length()-1);
            if(IsPresent.containsKey(s[0]+s[1])||IsPresent.containsKey((s[1]+s[2])))
                continue;
	    else
            {
                Edges.add(s);
                IsPresent.put(s[0]+s[1],1);IsPresent.put(s[1]+s[0],1);
            }
        }
        Graph G = new Graph(v,Edges);
        String function = scan2.next();
        if(function.equals("average"))
            System.out.println(Average(G));
        if(function.equals("rank"))
            Rank(G);
        if(function.equals("independent_storylines_dfs"))
            ISD(G);
                
    }
    
    public static String Average(Graph G)
    {
        if(G.getOrder()!=0)
        {
            float avg=((float)2*G.getSize()/(float)G.getOrder());
            float roundoff = (float)((float)Math.round(avg*100.0)/100.0);
            return String.format("%.2f",roundoff);
        }
        else
            return "0.00";
    }
    public static void Rank(Graph G)
    {
        Vector<String[]> Nodes = new Vector<String[]>();
        if(G.getOrder()>=1)
        {
            for(String name: G.getNodes())
            {
                String[] e = new String[2];
                e[0]=name;
                double sum =0;
                for(Edge i:G.adjacent.get(name))
                {
                    sum= sum+i.getWeight();
                }
                e[1]=Double.toString(sum);
                Nodes.add(e);
                sum=0;
            }
            String[] temp;
            Nodes =sort(Nodes);
            for(int i=0;i<G.getOrder()-1;i++)
            {
                temp = Nodes.get(i);
                System.out.print(temp[0]+",");
            }
            temp = Nodes.get(G.getOrder()-1);
            System.out.print(temp[0]);
        }
    }
    public static Vector<String[]> sort(Vector<String[]> V)
    {
        int L = V.size();
        if(L<=1)
            return V;
        Vector<String[]> V1 = new Vector<String[]>();
        Vector<String[]> V2 = new Vector<String[]>();
        for(int i =0;i<L/2;i++)
        {
            V1.add(V.get(i));
        }
        for(int i = L/2;i<L;i++)
        {
            V2.add(V.get(i));
        }
        V1 = sort(V1);
        V2 = sort(V2);
        return mergeSort(V1,V2);
    }
    public static Vector<String[]> mergeSort(Vector<String[]> V1,Vector<String[]>V2)
    {
        int i= 0,j=0;
        Vector<String[]> S = new Vector<String[]>();
        while(i<V1.size()&&j<V2.size())
        {
            String[] s1 = V1.get(i);
            String[] s2 = V2.get(j);
            if(Double.parseDouble(s1[1])>Double.parseDouble(s2[1]))
            {
                S.add(V1.get(i));
                i++;
            }
            else if(Double.parseDouble(s1[1])==Double.parseDouble(s2[1]))
            {
                if(s1[0].compareTo(s2[0])>0)
                {
                    S.add(V1.get(i));
                    i++;
                }
                else
                {
                    S.add(V2.get(j));
                    j++;
                }
            }
            else
            {
                S.add(V2.get(j));
                j++;
            }
        }
        while(i<V1.size())
        {
            S.add(V1.get(i));
            i++;
        }
        while(j<V2.size())
        {
            S.add(V2.get(j));
            j++;
        }
        return S;
    }
    
    public static void ISD(Graph G)
    {
        Depth_First_Search components = new Depth_First_Search(G);
        Vector<Vector<String>> idfs = new Vector<Vector<String>>();
        Sorting sort = new Sorting();
        for(Vector<String> G1:components.getComponents())
        {
            Vector<String> nodes = G1;
            nodes = sort.sort1(nodes);
            idfs.add(nodes);
        }
        idfs = sort.sort2(idfs);
        for(int j=0;j<idfs.size();j++)
        {
            Vector<String> g = idfs.get(j);
            for(int i=0;i<g.size()-1;i++)
            {
                System.out.print(g.get(i)+",");
            }
            System.out.println(g.get(g.size()-1));
        }
    }
    

    
}
class Sorting
{
    public Vector<String> sort1(Vector<String> V)
    {
        int L = V.size();
        if(L<=1)
            return V;
        Vector<String> V1 = new Vector<String>();
        Vector<String> V2 = new Vector<String>();
        for(int i =0;i<L/2;i++)
        {
            V1.add(V.get(i));
        }
        for(int i = L/2;i<L;i++)
        {
            V2.add(V.get(i));
        }
        V1 = sort1(V1);
        V2 = sort1(V2);
        return mergeSort1(V1,V2);
    }
   
    public static Vector<String> mergeSort1(Vector<String> V1,Vector<String>V2)
    {
        int i= 0,j=0;
        Vector<String> S = new Vector<String>();
        while(i<V1.size()&&j<V2.size())
        {
            if(V1.get(i).compareTo(V2.get(j))>=0)
            {
                S.add(V1.get(i));
                i++;
            }
            else{
                S.add(V2.get(j));
                j++;
            }
        }
        while(i<V1.size())
        {
            S.add(V1.get(i));
            i++;
        }
        while(j<V2.size())
        {
            S.add(V2.get(j));
            j++;
        }
        return S;
    }
    
    public Vector<Vector<String>> sort2(Vector<Vector<String>> V)
    {
        int L = V.size();
        if(L<=1)
            return V;
        Vector<Vector<String>> V1 = new Vector<Vector<String>>();
        Vector<Vector<String>> V2 = new Vector<Vector<String>>();
        for(int i =0;i<L/2;i++)
        {
            V1.add(V.get(i));
        }
        for(int i = L/2;i<L;i++)
        {
            V2.add(V.get(i));
        }
        V1 = sort2(V1);
        V2 = sort2(V2);
        return mergeSort2(V1,V2);
    }
   
    public static Vector<Vector<String>> mergeSort2(Vector<Vector<String>> V1,Vector<Vector<String>>V2)
    {
        int i= 0,j=0;
        Vector<Vector<String>> S = new Vector<Vector<String>>();
        while(i<V1.size()&&j<V2.size())
        {
            Vector<String> s1 = V1.get(i);
            Vector<String> s2 = V2.get(j);
            if(s1.size()>s2.size())
            {
                S.add(V1.get(i));
                i++;
            }
            else if (s1.size()==s2.size())
            {
                if(s1.get(0).compareTo(s2.get(0))>0)
                {
                    S.add(V1.get(i));
                    i++;
                }
                else
                {
                    S.add(V2.get(j));
                    j++;
                }
            }
            else
            {
                S.add(V2.get(j));
                j++;
            }
        }
        while(i<V1.size())
        {
            S.add(V1.get(i));
            i++;
        }
        while(j<V2.size())
        {
            S.add(V2.get(j));
            j++;
        }
        return S;
    }
}
class Graph{
    private static int Order=0; // number of vertices
    private static int Size=0;
    private static Vector<String> Nodes;
    private static Vector<String[]> Edges;
    public static HashMap<String,LinkedList<Edge>> adjacent; //vertices
    public Graph(Vector<String> V,Vector<String[]> E)
    {
        this.Order = V.size();
        this.Nodes = V;
        this.Edges = E;
        this.Size = E.size();
        this.adjacent = new HashMap<String,LinkedList<Edge>>();
        for(String v:V)
        {
            this.adjacent.put(v, new LinkedList<Edge>());
        }
        for(String[] e:E)
        {
            if(adjacent.containsKey(e[0])&&adjacent.containsKey(e[1]))
            {
                adjacent.get(e[0]).add(new Edge(new Node(e[1]),Double.parseDouble(e[2])));
                adjacent.get(e[1]).add(new Edge(new Node(e[0]),Double.parseDouble(e[2])));
            }
        }
    }
    public static int getOrder()
    {
        return Order;
    }
    public static Vector<String> getNodes()
    {
        return Nodes;
    }
    public static Vector<String[]> getEdges()
    {
        return Edges;
    }
    public static int getSize()
    {
        return Size;
    }
    
}
class Depth_First_Search
{
    private static Vector<Vector<String>> Components = new Vector<Vector<String>>();
    public Depth_First_Search(Graph G)
    {
        this.Components = ConnectedComponents(G);
    }
    public static Vector<Vector<String>> ConnectedComponents(Graph G)
    {
        Vector<Vector<String>> Components = new Vector<Vector<String>>(); int N = G.getOrder();
        HashMap<String,Integer>label = new HashMap<String,Integer>();
        Vector<String> Nodes = G.getNodes();
        for(int i=0;i<N;i++)
        {
            label.put(Nodes.get(i),0);
        }
        int component =1;
        int k =0;
        while(k<G.getOrder())
        {
            if(label.get(Nodes.get(k))==0)
            {
                Vector<String> V= new Vector<String>();
                DFS(Nodes.get(k),label,component,G,V);
                Components.add(V);
                component+=1;
            }
            k+=1;
        }
        return Components;
    }
    public static void DFS(String s, HashMap<String,Integer> label,int component,Graph G, Vector<String> V)
    {
        label.replace(s,component);
        V.add(s);
        for(Edge e : G.adjacent.get(s))
        {
            if(label.get(e.getNeighbour())==0)
            {
                DFS(e.getNeighbour(),label,component,G,V);
            }
        }
    }
    public Vector<Vector<String>> getComponents()
    {
        return Components;
    }
}
class Node{
    private String name;
    public Node(String name)
    {
        this.name =name;
    }
    public String getName()
    {
        return name;
    }
}
class Edge
{
    private String Neighbour;
    private double  weight;
    public Edge(Node n,double weight)
    {
        this.weight = weight;
        this.Neighbour = n.getName();
    }
    public String getNeighbour()
    {
        return Neighbour;
    }
    public double getWeight()
    {
        return weight;
    }
}