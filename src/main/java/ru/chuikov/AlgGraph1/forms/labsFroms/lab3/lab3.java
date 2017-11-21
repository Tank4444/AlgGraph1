package ru.chuikov.AlgGraph1.forms.labsFroms.lab3;

import ru.chuikov.AlgGraph1.entity.MyGraph;
import ru.chuikov.AlgGraph1.entity.OutputGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class lab3 extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JPanel output;
    List<String> vertexs = new ArrayList<String>();
    List<lab3Edge> edges = new ArrayList<lab3Edge>();
    List<String> visited = new ArrayList<String>();
    List<lab3Edge> path = new ArrayList<lab3Edge>();
    private MyGraph outputGraph;
    private MyGraph graph;



    public class lab3Edge{
        String vertex1;
        String vertex2;

        public lab3Edge(String vertex1,String vertex2)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
        }

        public lab3Edge getReversEdge(){
            return new lab3Edge(vertex2,vertex1);
        }

        String getString(){
            return this.vertex1+" - "+this.vertex2;
        }
    }

    public lab3(MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);
        outputGraph = new MyGraph();
        graph = new MyGraph();


        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            vertexs.add(entry.getKey());
        }

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                edges.add(new lab3Edge(entry.getKey(),ed.getKey()));
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
                lab3Edge newEdge = new lab3Edge(edges.get(i).vertex1,edges.get(i).vertex2);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //work
        boolean check=true;
        for (String ver:vertexs)
        {
            ArrayList<lab3Edge> smech=new ArrayList<lab3Edge>();
            for(lab3Edge edge:edges)
            {
                if ((edge.vertex1.equals(ver))||(edge.vertex2.equals(ver)))
                {
                    smech.add(new lab3Edge(edge.vertex1,edge.vertex2));
                }
            }
            if(smech.size()<2){
                check=false;
                break;
            }
        }
        if (check)
        {
            visited.clear();
            ArrayList<String> path=new ArrayList<String>();
            boolean finded=false;
            dfs(vertexs.get(0),path);


        }else {
            JOptionPane.showMessageDialog(null,"нету гамильтонова цикла");
        }
        

    }

    private void dfs(String ver,ArrayList<String> pa)
    {
        if((visited.size()==vertexs.size())&&(ver.equals(vertexs.get(0))))
        {
            String str="";

            for (String vert:pa){
                str=str+","+vert;
            }
            str=str+","+ver;
            JOptionPane.showMessageDialog(null,str);
            dispose();
        }
        if(!visited.contains(ver))
        {
            visited.add(ver);
            pa.add(ver);

            ArrayList<lab3Edge> smech=new ArrayList<lab3Edge>();
            for(lab3Edge edge:edges)
            {
                if ((edge.vertex1.equals(ver))||(edge.vertex2.equals(ver)))
                {
                    smech.add(new lab3Edge(edge.vertex1,edge.vertex2));
                }
            }

            for(lab3Edge edge:smech)
            {
                if(edge.vertex1.equals(ver))dfs(edge.vertex2,pa);
                else dfs(edge.vertex1,pa);
            }
        }
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
