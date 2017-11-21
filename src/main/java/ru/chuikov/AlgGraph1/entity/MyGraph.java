package ru.chuikov.AlgGraph1.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class MyGraph {
    private HashMap<String, HashMap<String,String>> vertexMap = new HashMap<String, HashMap<String, String>>();

    public void addVertex(String vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new HashMap<String, String>());
        }
    }

    public void delVertex(String vertexName){
        if (hasVertex(vertexName)){
            vertexMap.remove(vertexName);
            for (Iterator<HashMap.Entry<String,HashMap<String,String>>> it= vertexMap.entrySet().iterator(); it.hasNext();)
            {
                Map.Entry<String,HashMap<String,String>> entry = it.next();
                for (Iterator<HashMap.Entry<String,String>> initer = entry.getValue().entrySet().iterator();initer.hasNext(); )
                {
                    Map.Entry<String,String> inentry = initer.next();
                    if (inentry.getKey().equals(vertexName)) initer.remove();
                }
            }
        }
    }
    public boolean hasVertex(String vertexName) {
        return vertexMap.containsKey(vertexName);
    }

    public boolean hasEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) return false;
        HashMap<String,String> edges = vertexMap.get(vertexName1);
        return edges.containsKey(vertexName2);
    }
    public void addEdge(String vertexName1, String vertexName2, String weight) {
        if (!hasVertex(vertexName1)) addVertex(vertexName1);
        if (!hasVertex(vertexName2)) addVertex(vertexName2);
        HashMap<String,String> edges1 = vertexMap.get(vertexName1);
        edges1.put(vertexName2,weight);
    }

    public void delEdge(String edgeName1,String edgeName2)
    {
        if (hasEdge(edgeName1,edgeName2))
        {
            for (Iterator<HashMap.Entry<String,HashMap<String,String>>> it= vertexMap.entrySet().iterator(); it.hasNext();)
            {
                Map.Entry<String,HashMap<String,String>> entry = it.next();
                if (entry.getKey().equals(edgeName1))
                {
                    for (Iterator<HashMap.Entry<String,String>> initer = entry.getValue().entrySet().iterator();initer.hasNext(); )
                    {
                        Map.Entry<String,String> inentry = initer.next();
                        if (inentry.getKey().equals(edgeName2)) initer.remove();
                    }
                    break;
                }
            }
        }

    }

    public MyGraph loadFromFile(File file) throws IOException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();
        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        return (MyGraph) mapper.readValue(sb.toString(),MyGraph.class);

    }

    public void saveInFile(File file)
    {
        //work with file
        try {
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            ObjectMapper mapper = new ObjectMapper();
            writer.print(mapper.writeValueAsString(this));
            writer.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (JsonProcessingException e2) {
            e2.printStackTrace();
        }


    }

    public void clear()
    {
        vertexMap.clear();
    }

    public Map<String, HashMap<String,String>> getVertexMap() {
        return vertexMap;
    }

    public MyGraph() {
    }

    public MyGraph(HashMap<String, HashMap<String,String>> vertexMap) {
        this.vertexMap = vertexMap;
    }

    public void setVertexMap(HashMap<String, HashMap<String,String>> vertexMap) {
        this.vertexMap = vertexMap;
    }

    public List<String> getAllVertex()
    {
        List<String> result = new ArrayList<String>();
        for (Map.Entry<String,HashMap<String,String>> entry:vertexMap.entrySet())
        {
            result.add(entry.getKey());
        }
        return result;
    }
    public void addAllVertex(List<String> list){
        for (String el:list) {
            this.addVertex(el);
        }
    }


}
