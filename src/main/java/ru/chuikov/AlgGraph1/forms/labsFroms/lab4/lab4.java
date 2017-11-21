package ru.chuikov.AlgGraph1.forms.labsFroms.lab4;

import ru.chuikov.AlgGraph1.entity.MyGraph;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab3.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lab4 extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JLabel out;
    List<String> vertexs = new ArrayList<String>();
    List<lab4Edge> edges = new ArrayList<lab4Edge>();
    List<String> visited = new ArrayList<String>();
    private MyGraph outputGraph;
    private MyGraph graph;

    public class lab4Edge{
        String vertex1;
        String vertex2;

        public lab4Edge(String vertex1,String vertex2)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
        }

        public lab4Edge getReversEdge(){
            return new lab4Edge(vertex2,vertex1);
        }

        String getString(){
            return this.vertex1+" - "+this.vertex2;
        }
    }

    public lab4(MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

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
                edges.add(new lab4Edge(entry.getKey(),ed.getKey()));
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
                lab4Edge newEdge = new lab4Edge(edges.get(i).vertex1,edges.get(i).vertex2);
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

        /*

        //work
        //1. Найти массив доступных вершин из каждой вершины
        //2. Сравнить их и удалить повторяющиеся
        //3. Вывод

        //1
        ArrayList<ArrayList<String>> obsh= new ArrayList<ArrayList<String>>();
        for(String ver:vertexs)
        {
            obsh.add(findSmesh(ver));
        }
        //2
        for(int i=0;i<obsh.size();i++)
        {
            ArrayList<String> leg=obsh.get(i);
            for(int j=i+1;j<obsh.size();j++)
            {
                if(srav(obsh.get(i),obsh.get(j)))
                {
                    obsh.remove(j);
                    j--;
                }
            }
        }

        String string= "<html>Число независимости "+obsh.size()+"<br>";
        for(ArrayList<String> arr:obsh)
        {

            for(String s:arr)
            {
                string=string+" "+s;
            }
            string=string+"<br>";
        }
        string=string+"</html>";
        out.setText(string);
        */

        ArrayList<ArrayList<String>> indepenents= new ArrayList<ArrayList<String>>();
        ArrayList<String> stek = new ArrayList<String>();
        ArrayList<String> indep= new ArrayList<String>();
        for(String root:vertexs)
        {
            stek.clear();
            indep.clear();
            visited.clear();
            stek.add(root);
            while(stek.size()>0)
            {
                System.out.println("stek is no empty");
                if(!visited.contains(stek.get(0)))
                {
                    System.out.println("visited");
                    visited.add(stek.get(0));
                    ArrayList<String> smesh=new ArrayList<String>();
                    for (lab4Edge ed : edges)
                    {
                        if (ed.vertex1.equals(stek.get(0)))
                        {
                            if(!visited.contains(ed.vertex2)) stek.add(ed.vertex2);
                            smesh.add(ed.vertex2);
                        }else if (ed.vertex2.equals(stek.get(0))) {
                            if(!visited.contains(ed.vertex1)) stek.add(ed.vertex1);
                            smesh.add(ed.vertex1);
                        }
                    }

                    boolean flag=true;
                    for(String check:smesh)
                    {
                        if(indep.contains(check))
                        {
                            flag=false;
                            break;
                        }
                    }
                    if(flag)
                    {
                        indep.add(new String(stek.get(0)));
                    }
                    stek.remove(0);
                } else stek.remove(0);
            }
            indepenents.add(new ArrayList<String>(indep));

        }
        //del Doubicate
        for(int i=0;i<indepenents.size();i++)
        {
            for(int j=i+1;j<indepenents.size();j++)
            {
                ArrayList<String> listJ=indepenents.get(j);
                ArrayList<String> listI=indepenents.get(i);
                if(listI.size()==listJ.size())
                {
                    boolean check=true;
                    for(String ch:listI)
                    {
                        if(!listJ.contains(ch))
                        {
                            check=true;
                            break;
                        }else check=false;
                    }
                    if(check==false){
                        indepenents.remove(j);
                        j--;
                    }

                }

                /*
                boolean check=true;
                for(String sch:listI)
                {
                    if(!listJ.contains(sch))
                    {
                        //check=false;
                        break;
                    }
                    if(check)
                    {
                        indepenents.remove(j);
                        j--;
                    }
                }
                */
            }
        }

        //find chislo

        int max=indepenents.get(0).size();
        for(ArrayList<String> arrayList:indepenents)
        {
            if(max<arrayList.size())max=arrayList.size();
        }

        String out="<html>";
        for(ArrayList<String> list:indepenents)
        {
            out=out+list+"<br>";
        }
        out=out+"Число независимости = "+max;
        out=out+"</html>";
        JOptionPane.showMessageDialog(null,out);

    }
    boolean srav(ArrayList<String> fist, ArrayList<String> sec)
    {
        boolean res=true;
        for(String sr:fist)
        {
            if(!sec.contains(sr)) return false;
        }
        return res;
    }

    ArrayList<String> findSmesh(String originalVertex)
    {
        visited.clear();
        dfs(originalVertex);
        return new ArrayList<String>(visited);
    }

    void dfs(String vertex) {
        if (!visited.contains(vertex))
        {
            visited.add(vertex);
            for (lab4Edge edge : edges) {
                if ((edge.vertex1.equals(vertex)) || (edge.vertex2.equals(vertex))) {
                    if (edge.vertex1.equals(vertex)) dfs(edge.vertex2);
                    else dfs(edge.vertex1);
                }
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
