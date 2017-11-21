package ru.chuikov.AlgGraph1.forms.labsFroms.lab2;

import ru.chuikov.AlgGraph1.entity.MyGraph;
import ru.chuikov.AlgGraph1.entity.OutputGraph;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab1.Lab1;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class lab2 extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JPanel output;
    List<String> vertexs = new ArrayList<String>();
    List<lab2Edge> edges = new ArrayList<lab2Edge>();
    List<String> visited = new ArrayList<String>();
    private MyGraph graph;
    private MyGraph outputGraph;
    private List<lab2Edge> addedVertex = new ArrayList<lab2Edge>();

    public class lab2Edge{
        String vertex1;
        String vertex2;
        int weight;

        public lab2Edge(String vertex1,String vertex2,int weight)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
            this.weight=weight;
        }

        public lab2Edge getReversEdge(){
            return new lab2Edge(vertex2,vertex1,weight);
        }

        String getString(){
            return this.vertex1+" - "+this.vertex2+" : "+this.weight;
        }

    }

    public class CustomComparator implements Comparator<lab2Edge> {
        public int compare(lab2Edge o1, lab2Edge o2) {
            return o1.weight-o2.weight;
        }
    }

    public lab2(final MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);
        graph=myGraph;
        outputGraph= new MyGraph();
        clear();

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            vertexs.add(entry.getKey());
        }
        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                edges.add(new lab2Edge(entry.getKey(),ed.getKey(),Integer.parseInt(ed.getValue())));
            }
        }
        //Удаляем повторяющиеся рёбра
        for(int i = 0;i<edges.size();i++)
        {

            boolean check=false;
            int index=i;
            for (int j = i+1; j<edges.size() ; j++)
            {
                if((edges.get(i).vertex2.equals(edges.get(j).vertex1))&&(edges.get(i).vertex1.equals(edges.get(j).vertex2)))
                {
                  check=true;
                  index=j;
                  break;
                }
            }
            if(check){
                lab2Edge newEdge = new lab2Edge(edges.get(i).vertex1,edges.get(i).vertex2, (int)(edges.get(i).weight+edges.get(index).weight)/2);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //Work
        Collections.sort(edges,new CustomComparator());
        for (lab2Edge edge:edges)
        {
            visited.clear();
            if (!hasCircle(edge))
            {
                addedVertex.add(edge);
            }
            //outputGraph
        }
        for (lab2Edge edge:addedVertex)
        {
            outputGraph.addEdge(edge.vertex1,edge.vertex2,String.valueOf(edge.weight));
        }
        flush();
    }

    boolean hasCircle(lab2Edge ed)
    {
        List<lab2Edge> workEdge = new ArrayList<lab2Edge>(addedVertex);
        dfs(ed.vertex1,workEdge);
        return visited.contains(ed.vertex2);
    }

    void dfs(String vertex, List<lab2Edge> workEdge)
    {
        visited.add(vertex);
        List<lab2Edge> list = new ArrayList<lab2Edge>();
        for (int i = 0;i<workEdge.size();i++)
        {
            if((workEdge.get(i).vertex1.equals(vertex))||(workEdge.get(i).vertex2.equals(vertex))) list.add(workEdge.get(i));
        }
        for (int i = 0 ; i < list.size(); i++){
            if(list.get(i).vertex1.equals(vertex)){
                if (!visited.contains(list.get(i).vertex2)) dfs(list.get(i).vertex2,workEdge);
            }else {
                if (!visited.contains(list.get(i).vertex1)) dfs(list.get(i).vertex1,workEdge);
            }
        }
    }

    private void clear()
    {
        outputGraph.clear();
        outputGraph.addAllVertex(graph.getAllVertex());

        output.removeAll();
        output.add(OutputGraph.constractGraph(outputGraph));
        output.revalidate();
        output.repaint();

    }
    private void flush()
    {
        output.removeAll();
        output.add(OutputGraph.constractGraph(outputGraph));
        output.revalidate();
        output.repaint();
    }
    private void onExit() {
        // add your code here if necessary
        dispose();
    }

}
