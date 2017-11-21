package ru.chuikov.AlgGraph1.forms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;
import ru.chuikov.AlgGraph1.entity.MyGraph;
import ru.chuikov.AlgGraph1.entity.OutputGraph;
import ru.chuikov.AlgGraph1.entity.enums.variants;
import ru.chuikov.AlgGraph1.forms.frames.TestFrame;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab1.Lab1;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab2.lab2;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab3.lab3;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab4.lab4;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab5.lab5;
import ru.chuikov.AlgGraph1.forms.labsFroms.lab6.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainForm extends JDialog {
    private JPanel contentPane;
    private JMenuBar menuBar;

    private JPanel center;
    private JPanel bottom;
    private JComboBox variant;
    private JPanel workPane;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JTextField vertexAdd;
    private JButton vertexAddButton;
    private JPanel workGraph;
    private JTextField vertexDel;
    private JButton vertexDelButton;
    private JTextField edgeAdd1;
    private JTextField edgeAdd2;
    private JTextField edgeAddWeight;
    private JButton edgeAddButton;
    private JTextField edgeDel1;
    private JTextField edgeDel2;
    private JButton edgeDelButton;
    private JButton createGraphButton;
    private MyGraph myGraph;
    private File file;
    private File workfile;


    public MainForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);

        myGraph = new MyGraph();
        menuBar = new JMenuBar();
        workGraph.setSize(300,300);
        variant.setModel(new DefaultComboBoxModel(variants.values()));

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        vertexAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addVertex();
            }
        });

        edgeAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEdge();
            }
        });

        vertexDelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delVertex();
            }
        });

        edgeDelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delEdge();
            }
        });

        menuBar.add(fileMenu());
        setJMenuBar(menuBar);
        workGraph.add(OutputGraph.constractGraph(myGraph));
    }

    private void addVertex()
    {
        if (!myGraph.hasVertex(vertexAdd.getText())) myGraph.addVertex(vertexAdd.getText());
        vertexAdd.setText("");
        flash();
    }

    private void delVertex()
    {
        if (myGraph.hasVertex(vertexDel.getText())) myGraph.delVertex(vertexDel.getText());
        vertexDel.setText("");
        flash();
    }

    private void addEdge()
    {
       if(!myGraph.hasEdge(edgeAdd1.getText(),edgeAdd2.getText())) myGraph.addEdge(edgeAdd1.getText(),edgeAdd2.getText(),edgeAddWeight.getText());
       edgeAdd1.setText("");
       edgeAdd2.setText("");
       edgeAddWeight.setText("");
       flash();
    }

    private void delEdge()
    {
        if(myGraph.hasEdge(edgeDel1.getText(),edgeDel2.getText())) myGraph.delEdge(edgeDel1.getText(),edgeDel2.getText());
        edgeDel1.setText("");
        edgeDel2.setText("");
        flash();
    }
    private void onOK() {
        switch (variant.getSelectedIndex()){
            case 0:
                Lab1 lab1 = new Lab1(myGraph);
                lab1.pack();
                lab1.setSize(800,600);
                lab1.setVisible(true);
                break;
            case 1:
                lab2 lab2 = new lab2(myGraph);
                lab2.pack();
                lab2.setSize(800,600);
                lab2.setVisible(true);
                break;
            case 2:
                lab3 lab3 = new lab3(myGraph);
                lab3.pack();
                lab3.setSize(800,600);
                lab3.setVisible(true);
                break;
            case 3:
                lab4 lab4 = new lab4(myGraph);
                lab4.pack();
                lab4.setVisible(true);
                break;
            case 4:
                lab5 lab5 = new lab5(myGraph);
                lab5.pack();
                lab5.setVisible(true);
                break;
            case 5:
                lab6 lab6 = new lab6(myGraph);
                lab6.pack();
                lab6.setVisible(true);
                break;
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        MainForm dialog = new MainForm();
        dialog.pack();
        dialog.setSize(800,700);
        dialog.setVisible(true);
        System.exit(0);
    }

    private void flash()
    {
        workGraph.removeAll();
        workGraph.add(OutputGraph.constractGraph(myGraph));
        workGraph.revalidate();
        workGraph.repaint();
    }

    private JMenu fileMenu()
    {
        // Создание выпадающего меню
        final JMenu file = new JMenu("Файл");
        // Пункт меню "Открыть"
        JMenuItem open = new JMenuItem("Открыть");
        open.setAccelerator(KeyStroke.getKeyStroke('O',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open graph");
                int userSelection = fileChooser.showOpenDialog(getParent());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    workfile = fileChooser.getSelectedFile();
                    try {
                        myGraph= myGraph.loadFromFile(workfile);
                        flash();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        // Пункт меню из команды с выходом из программы
        JMenuItem exit = new JMenuItem("Выход");
        exit.setAccelerator(KeyStroke.getKeyStroke('E',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        // Create
        JMenuItem create = new JMenuItem("Создать");
        create.setAccelerator(KeyStroke.getKeyStroke('C',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        // Save
        JMenuItem save = new JMenuItem("Сохранить");
        save.setAccelerator(KeyStroke.getKeyStroke('S',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Graph");
                int userSelection = fileChooser.showSaveDialog(getParent());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    workfile = fileChooser.getSelectedFile();
                    myGraph.saveInFile(workfile);
                }
            }
        });
        // Delete
        JMenuItem del = new JMenuItem("Удалить");
        del.setAccelerator(KeyStroke.getKeyStroke('D',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        // Добавим в меню пункта open
        file.add(open);
        file.add(create);
        file.add(save);
        file.add(del);
        file.addSeparator();
        file.add(exit);


        return file;
    }


}
