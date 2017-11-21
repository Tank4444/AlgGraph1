package ru.chuikov.AlgGraph1.forms.labsFroms.lab1;

import ru.chuikov.AlgGraph1.entity.MyGraph;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lab1 extends JDialog {
    private JPanel contentPane;
    private JButton exitButton;
    private JPanel outputPanel;
    private JLabel result;
    List<String> vertexs = new ArrayList<String>();
    List<lab1Edge> edges = new ArrayList<lab1Edge>();
    List<String> visited = new ArrayList<String>();

    public class lab1Edge{
        String vertex1;
        String vertex2;

        public lab1Edge(String vertex1,String vertex2)
        {
            this.vertex1=vertex1;
            this.vertex2=vertex2;
        }

        public lab1Edge getReversEdge(){
            return new lab1Edge(vertex2,vertex1);
        }

        String getString(){
            return this.vertex1+" - "+this.vertex2;
        }
    }

    public Lab1(MyGraph myGraph) {
        setContentPane(contentPane);
        setModal(true);

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            vertexs.add(entry.getKey());
        }

        for (Map.Entry<String,HashMap<String,String>> entry:myGraph.getVertexMap().entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                edges.add(new lab1Edge(entry.getKey(),ed.getKey()));
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
                lab1Edge newEdge = new lab1Edge(edges.get(i).vertex1,edges.get(i).vertex2);
                edges.remove(edges.get(i));
                edges.add(i,newEdge);
                edges.remove(edges.get(index));
            }
        }

        exitButton.addActionListener(new ActionListener() {
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

        result.setText("<html>");
        for (int i=0;i<edges.size();i++)
        {
            if (isBridge(edges.get(i))){
                result.setText(result.getText()+edges.get(i).getString()+"<br>");
                //bridge.add(edges.get(i));
            }
        }
        result.setText(result.getText()+"</html>");

    }

    private boolean isBridge(lab1Edge edge)
    {
        List<lab1Edge> workedge = new ArrayList<lab1Edge>(edges);
        workedge.remove(edge);
        visited.clear();
        dfs(edge.vertex1,workedge);
        return !visited.contains(edge.vertex2);
    }

    void dfs(String vertex, List<lab1Edge> workEdge)
    {
        visited.add(vertex);
        List<lab1Edge> list = new ArrayList<lab1Edge>();
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

    private void onExit() {
        // add your code here if necessary
        dispose();
    }
}
