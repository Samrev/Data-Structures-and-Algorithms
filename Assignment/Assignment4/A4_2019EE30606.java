import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class A4_2019EE30606 {
    static Map<String, HashMap<String, Integer>> graph = new HashMap<>();
    static Vector<String> Vertices = new Vector<String>();
    static Vector<String[]> Edges = new Vector<String[]>();
    public static void main(String[] args) {
        String nodeFile = args[0];
        String edgeFile = args[1];
        String functionName = args[2];

        try {
            BufferedReader brNodes = new BufferedReader(new FileReader(nodeFile));
            String str;
            str = brNodes.readLine();
            while ((str = brNodes.readLine()) != null) {
                String[] data = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                data[0] = data[0].replace("\"", "");
                data[1] = data[1].replace("\"", "");
                graph.put(data[1], new HashMap<String, Integer>());
                Vertices.add(data[1]);
            }
            brNodes.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String str;
            BufferedReader brEdges = new BufferedReader(new FileReader(edgeFile));
            str = brEdges.readLine();
            while ((str = brEdges.readLine()) != null) {
                String[] data = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String source = data[0];
                String target = data[1];
                int weight = Integer.parseInt(data[2]);
                source = source.replace("\"", "");
                target = target.replace("\"", "");

                if (graph.get(source).get(target) != null) {
                    continue;
                }
                HashMap<String, Integer> listSource = graph.get(source); // 002
                listSource.put(target, weight);
                graph.put(source, listSource);

                HashMap<String, Integer> listTarget = graph.get(target);
                listTarget.put(source, weight);
                graph.put(target, listTarget);
                Edges.add(new String[] {source,target,Integer.toString(weight)});
            }
            brEdges.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // calling functions
         String[] possible_functions = new String[] { "average", "rank",
        "independent_storylines_dfs" };
         if (functionName.equals(possible_functions[0])) {
         average();
         } else if (functionName.equals(possible_functions[1])) {
         rank();
        // } else {
        // isdfs();
         }
    }

    public static void average() {
        float a = graph.size();
        float b = 0;
        for (Map.Entry<String, HashMap<String, Integer>> y : graph.entrySet()) {
            b = b + y.getValue().size();
        }
        if (a == 0) {
            System.out.println(0.00);
        } else {
            float avg = b / a;
            String final_ans = String.format("%.2f", avg);
            System.out.println(final_ans);
        }
    }

    // rank method

    public static void rank() {
        Vector <String[]> Edges = new Vector<String[]>();
        for(Map.Entry<String, HashMap<String,Integer>> y : graph.entrySet())
        {
            int totaldegree=0;
            for(String adj: Vertices)
            {
                if(y.getValue().get(adj)!=null)
                {
                    totaldegree+=y.getValue().get(adj);
                }
            }
            Edges.add(new String[] {y.getKey(),Integer.toString(totaldegree)});
            Edges = sort(Edges);
        }   
        
        for(int i=0;i<Edges.size()-1;i++)
        {
            String[] e = Edges.get(i);
            System.out.print(e[0]+",");
        }
        String[] e = Edges.get(Edges.size()-1);
        System.out.println(e[0]);
    }

    public static Vector<String[]> sort(Vector<String[]> Vertices)
    {
        if(Vertices.size()>1)
        {
            Vector<String[]> V0 = new Vector<String[]>();
            Vector<String[]> V1 = new Vector<String[]>();
            for(int i =0;i<Vertices.size()/2;i++)
            {
                V0.add(Vertices.get(i));
            }
             for(int i = Vertices.size()/2;i<Vertices.size();i++)
            {
                V1.add(Vertices.get(i));
            }
            V0=sort(V0);
            V1=sort(V1);
            return mergeSort(V0,V1);
        }
        return Vertices;
    }
    public static Vector<String[]> mergeSort(Vector<String[]> V1,Vector<String[]>V2)
    {
        int i= 0,j=0;
        Vector<String[]> Sorted = new Vector<String[]>();
        while(i<V1.size()&&j<V2.size())
        {
            String[] e1 = V1.get(i);
            String[] e2 = V2.get(j);
            if(Integer.parseInt(e1[1])>Integer.parseInt(e2[1]))
            {
                Sorted.add(V1.get(i));
                i++;
            }
            else if(Integer.parseInt(e1[1])==Integer.parseInt(e2[1]))
            {
                if(e1[0].compareTo(e2[0])>0)
                {
                    Sorted.add(V1.get(i));
                    i++;
                }
                else
                {
                    Sorted.add(V2.get(j));
                    j++;
                }
            }
            else
            {
                Sorted.add(V2.get(j));
                j++;
            }
        }
        while(i<V1.size())
        {
            Sorted.add(V1.get(i));
            i++;
        }
        while(j<V2.size())
        {
            Sorted.add(V2.get(j));
            j++;
        }
        return Sorted;
    }
    
    
            
    // independent_storylines_dfs

    public static void isdfs() {
        System.out.println("");
    }
}