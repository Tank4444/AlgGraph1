package ru.chuikov.AlgGraph1.forms.frames;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TestFrame extends JFrame {
    private JPanel base;
    private JButton jb;
    private JButton exit;

    public TestFrame()
    {
        super("TestFrame");
        base=new JPanel();
        jb=new JButton();
        exit = new JButton();

        jb.setText("Hello");
        exit.setText("exit");
        base.setLayout(new FlowLayout());
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });



        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            Object v1 = graph.insertVertex(parent, null, "Habra", 20, 20, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "Habr", 240, 150, 80, 30);
            graph.insertEdge(parent, null, "Дуга", v1, v2);
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        base.add(graphComponent);
        base.add(jb);
        base.add(exit);
        getContentPane().add(base);
    }

}
