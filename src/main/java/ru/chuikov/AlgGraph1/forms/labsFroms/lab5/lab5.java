package ru.chuikov.AlgGraph1.forms.labsFroms.lab5;

import ru.chuikov.AlgGraph1.entity.MyGraph;
import ru.chuikov.AlgGraph1.entity.OutputGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lab5 extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JPanel output;
    private ArrayList<lab5Edge> edges = new ArrayList<lab5Edge>();
    private ArrayList<String> vertexs = new ArrayList<String>();
    private ArrayList<String> colors=new ArrayList<String>();
    private int nextColor=1;
    private MyGraph graph;
    private MyGraph outputGraph;

    private class lab5Edge{
        String vertex1;
        String vertex2;
        String color;
        boolean visited=false;
        lab5Edge(String vertex1,String vertex2,String color)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
            this.color=color;
        }
        lab5Edge(String vertex1,String vertex2)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
            this.color="";
        }
    }
    public lab5(MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
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
                edges.add(new lab5Edge(entry.getKey(),ed.getKey()));
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
                lab5Edge newEdge = new lab5Edge(edges.get(i).vertex1,edges.get(i).vertex2);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }
        //work here
        ArrayList<lab5Edge> smesh= new ArrayList<lab5Edge>();
        for(lab5Edge edge:edges)
        {
            ArrayList<String> avalableColors=new ArrayList<String>(colors);
            smesh.clear();
            for(lab5Edge srav:edges)
            {
                if((srav.vertex1.equals(edge.vertex1))||(srav.vertex2.equals(edge.vertex1)))
                {
                    smesh.add(new lab5Edge(srav.vertex1,srav.vertex2,srav.color));
                }
                if((srav.vertex1.equals(edge.vertex2))||(srav.vertex2.equals(edge.vertex2)))
                {
                    smesh.add(new lab5Edge(srav.vertex1,srav.vertex2,srav.color));
                }
            }
            for(int i=0;i<smesh.size();i++)
            {
                lab5Edge del=smesh.get(i);
                if((del.vertex1.equals(edge.vertex1))&&(del.vertex2.equals(edge.vertex2)))
                {
                    smesh.remove(i);
                    i--;
                }else if((del.vertex1.equals(edge.vertex2))&&(del.vertex2.equals(edge.vertex1)))
                {
                    smesh.remove(i);
                    i--;
                }
            }


            for(lab5Edge sm:smesh)
            {
                if (avalableColors.contains(sm.color))avalableColors.remove(sm.color);
            }
            if(avalableColors.isEmpty())
            {
                String newColor=String.valueOf(nextColor);
                addNewColor();
                edge.color=newColor;
            }else
            {
                edge.color=avalableColors.get(0);
            }

        }

        for (lab5Edge edge: edges)
        {
            outputGraph.addEdge(edge.vertex1,edge.vertex2,String.valueOf(edge.color));
        }
        flush();

        String out="<html>";
        out=out+"Хроматический индекс = "+colors.size();
        out=out+"</html>";
        JOptionPane.showMessageDialog(null,out);
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

    private void addNewColor()
    {
       colors.add(String.valueOf(nextColor));
       nextColor++;
    }
    private void flush()
    {
        output.removeAll();
        output.add(OutputGraph.constractGraph(outputGraph));
        output.revalidate();
        output.repaint();
    }
    ArrayList<lab5Edge> clearListOfEdge( ArrayList<lab5Edge> list)
    {
        for(lab5Edge edge:list)edge.visited=false;
        return list;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
