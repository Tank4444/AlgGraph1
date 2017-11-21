package ru.chuikov.AlgGraph1.forms.labsFroms.lab6;

import ru.chuikov.AlgGraph1.entity.MyGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class lab6 extends JDialog {
    private JPanel contentPane;
    private JButton buttonFind;
    private JButton buttonCancel;
    private MyGraph graph;
    private MyGraph outputGraph;
    private ArrayList<lab6Edge> edges = new ArrayList<lab6Edge>();
    private ArrayList<lab6Vertex> vertexs = new ArrayList<lab6Vertex>();
    private ArrayList<lab6Vertex> workVertexes = new ArrayList<lab6Vertex>();
    private class lab6Edge{
        String vertex1;
        String vertex2;
        int max,flow;

        public lab6Edge(String vertex1, String vertex2, int max, int flow) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.max = max;
            this.flow = flow;
        }
    }
    private class lab6Vertex{
        String vertex;
        int e,h;

        public lab6Vertex(String vertex, int e, int h) {
            this.vertex = vertex;
            this.e = e;
            this.h = h;
        }
    }

    public lab6(MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonFind);

        buttonFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFind();
            }
        });

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

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            vertexs.add(new lab6Vertex(entry.getKey(),0,0));
        }

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                edges.add(new lab6Edge(entry.getKey(),ed.getKey(),Integer.parseInt(ed.getValue()),0));
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
                lab6Edge newEdge = new lab6Edge(edges.get(i).vertex1,edges.get(i).vertex2,edges.get(i).max, edges.get(i).flow);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }

        onFind();
    }

    private void onFind() {
        /*
            int[][] f = new int[n][n];
            for (int i=1; i<n; i++)
            {
                f[0][i] = c[0][i];
                f[i][0] = -c[0][i];
            }

            int[] h = new int[n];
            h[0] = n;

            int[] e = new int[n];
            for (int i=1; i<n; i++)
                e[i] = f[0][i];

            for ( ; ; )
            {
                int i;
                for (i=1; i<n-1; i++)
                    if (e[i] > 0)
                        break;
                if (i == n-1)
                    break;

                int j;
                for (j=0; j<n; j++)
                    if (c[i][j]-f[i][j] > 0 && h[i]==h[j]+1)
                        break;
                if (j < n)
                    push (i, j, f, e, c);
                else
                    lift (i, h, f, c);
            }

            int flow = 0;
            for (int i=0; i<n; i++)
                if (c[0][i]>0)
                    flow += f[0][i];





        String out="<html>";
        out=out+"Максимальный поток = "+max(flow,0);
        out=out+"</html>";
        JOptionPane.showMessageDialog(null,out);
        */
        //istok do
        //stok kuda

        for(int i=1;i<vertexs.size()-1;i++)
        {
            workVertexes.add(vertexs.get(i));
        }


    }

    private void push(lab6Vertex vertex)
    {
        if(vertex.e!=0)
        {
            ArrayList<lab6Edge> smeshEdges = findStockEdges(vertex);
            ArrayList<lab6Vertex> smeshVertex = findStokVertex(vertex);
            for(lab6Edge edge:smeshEdges)
            {
                if(edge.max==edge.flow)
            }
        }
    }

    private getVertexWhisMinH(ArrayList<>)

    private int exit(ArrayList<lab6Vertex> vertexs)
    {
        int e=0;
        for (lab6Vertex vertex:vertexs)
        {
            e+=vertex.e;
        }
        return e;
    }
    private lab6Vertex findVertex(String vert)
    {
        lab6Vertex res=null;
        for(lab6Vertex vertex:vertexs)if(vertex.vertex.equals(vert))res =vertex;
        return res;
    }

    private ArrayList<lab6Edge> findIstokEdges(lab6Vertex vertex)
    {
        ArrayList<lab6Edge> result = new ArrayList<lab6Edge>();
        for(lab6Edge edge:edges)
        {
            if(edge.vertex1.equals(vertex.vertex)) result.add(edge);
        }
        return result;
    }
    private ArrayList<lab6Edge> findStockEdges(lab6Vertex vertex)
    {
        ArrayList<lab6Edge> result = new ArrayList<lab6Edge>();
        for(lab6Edge edge:edges)
        {
            if(edge.vertex2.equals(vertex.vertex)) result.add(edge);
        }
        return result;
    }

    private ArrayList<lab6Vertex> findIstokVertex(lab6Vertex vertex)
    {
        ArrayList<lab6Edge> find = findIstokEdges(vertex);
        ArrayList<lab6Vertex> result = new ArrayList<lab6Vertex>();
       for(lab6Edge edge:find)
       {
           for(lab6Vertex vertex1:vertexs)if(vertex1.vertex.equals(edge.vertex2))result.add(vertex1);
       }
       for(int i=0;i<result.size();i++)
       {
           for(int j=i+1;j<result.size();j++)
           {
               if(result.get(i).vertex.equals(result.get(j).vertex))
               {
                   result.remove(j);
                   j--;
               }
           }
       }
        return result;
    }
    private ArrayList<lab6Vertex> findStokVertex(lab6Vertex vertex)
    {
        ArrayList<lab6Edge> find = findStockEdges(vertex);
        ArrayList<lab6Vertex> result = new ArrayList<lab6Vertex>();
        for(lab6Edge edge:find)
        {
            for(lab6Vertex vertex1:vertexs)if(vertex1.vertex.equals(edge.vertex1))result.add(vertex1);
        }
        for(int i=0;i<result.size();i++)
        {
            for(int j=i+1;j<result.size();j++)
            {
                if(result.get(i).vertex.equals(result.get(j).vertex))
                {
                    result.remove(j);
                    j--;
                }
            }
        }
        return result;
    }

    void push (int u, int v, int[][] f, int[] e, int[][] c)
    {
        int d = min(e[u], c[u][v] - f[u][v]);
        f[u][v] += d;
        f[v][u] = - f[u][v];
        e[u] -= d;
        e[v] += d;
    }

    void lift (int u, int[] h, int[][] f, int[][] c)
    {
        int d = inf;

        for (int i = 0; i < f.length; i++)
            if (c[u][i]-f[u][i] > 0)
                d = min (d, h[i]);
        if (d == inf)
            return;
        h[u] = d + 1;
    }

    private int max(int a,int b)
    {
        if(a>=b)return a;
        else return b;
    }
    private int min(int a,int b)
    {
        if(a<=b)return a;
        else return b;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
