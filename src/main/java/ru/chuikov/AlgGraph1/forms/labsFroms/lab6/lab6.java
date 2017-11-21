package ru.chuikov.AlgGraph1.forms.labsFroms.lab6;

import ru.chuikov.AlgGraph1.entity.MyGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class lab6 extends JDialog {
    private JPanel contentPane;
    private JButton buttonFind;
    private JButton buttonCancel;
    private MyGraph graph;
    private MyGraph outputGraph;
    private ArrayList<lab6Edge> edges = new ArrayList<lab6Edge>();
    private ArrayList<String> vertexs = new ArrayList<String>();
    private int[][] c;
    private  int n;
    int inf = 1000*1000*1000;

    private class lab6Edge{
        String vertex1;
        String vertex2;
        int flow;
        lab6Edge(String vertex1,String vertex2,int flow)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
            this.flow=flow;
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
            vertexs.add(entry.getKey());
        }

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                edges.add(new lab6Edge(entry.getKey(),ed.getKey(),Integer.parseInt(ed.getValue())));
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
                lab6Edge newEdge = new lab6Edge(edges.get(i).vertex1,edges.get(i).vertex2,edges.get(i).flow);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }
        n = vertexs.size();
        c = new int[n][n];
        for(lab6Edge edge:edges)
        {
            c[vertexs.indexOf(edge.vertex1)][vertexs.indexOf(edge.vertex2)] = edge.flow;
        }
        onFind();
    }

    private void onFind() {
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
