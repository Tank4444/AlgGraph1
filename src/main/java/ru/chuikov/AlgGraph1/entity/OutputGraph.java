package ru.chuikov.AlgGraph1.entity;


import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OutputGraph {
    public static class MyEdge extends DefaultWeightedEdge {
        @Override
        public String toString() {
            return String.valueOf(getWeight());
        }
    }

    public static ListenableGraph<String, MyEdge> buildGraph(MyGraph graph) {
        ListenableDirectedWeightedGraph<String, MyEdge> g =
                new ListenableDirectedWeightedGraph<String, MyEdge>(MyEdge.class);


        Map<String,HashMap<String,String>> map = graph.getVertexMap();
        MyEdge e;
        //Add Vertex
        for (Map.Entry<String,HashMap<String,String>> entry:map.entrySet())
        {
            g.addVertex(entry.getKey());
        }
        //Add Edge
        for (Map.Entry<String,HashMap<String,String>> entry:map.entrySet())
        {
            for(Map.Entry<String,String> ed: entry.getValue().entrySet())
            {
                e = g.addEdge(entry.getKey(), ed.getKey());
                g.setEdgeWeight(e, Integer.parseInt(ed.getValue()));
            }
        }
        /*
            String x1 = "x1";
            String x2 = "x2";
            String x3 = "x3";

            g.addVertex(x1);
            g.addVertex(x2);
            g.addVertex(x3);

            MyEdge e = g.addEdge(x1, x2);
            g.setEdgeWeight(e, 1);
            e = g.addEdge(x2, x3);
            g.setEdgeWeight(e, 2);

            e = g.addEdge(x3, x1);
            g.setEdgeWeight(e, 3);
        */


        return g;
    }

    public static Component constractGraph( MyGraph graph)
    {
        ListenableGraph<String, MyEdge> g = buildGraph(graph);
        JGraphXAdapter<String, MyEdge> graphAdapter = new JGraphXAdapter<String, MyEdge>(g);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        return new mxGraphComponent(graphAdapter);
    }

}
