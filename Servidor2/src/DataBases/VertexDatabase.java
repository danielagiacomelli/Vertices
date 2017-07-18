/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBases;

/**
 *
 * @author dcosta
 */
import Models.DadosVertex;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VertexDatabase {

    private RecordManager recman;
    private HTree hashtable;
    private FastIterator iter;

    public VertexDatabase() throws IOException {
        recman = RecordManagerFactory.createRecordManager("vertexDB", new Properties());
        long recid = recman.getNamedObject("vertex");
        if (recid != 0) {
            System.out.println("Loading DB Vertex...");
            hashtable = HTree.load(recman, recid);
        } else {
            System.out.println("Creating new DB Vertex...");
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject("vertex", hashtable.getRecid());
        }
    }

    public synchronized void insertVertex(DadosVertex data) throws IOException {
        String id = Integer.toString(data.getIdVertex());
        hashtable.put(id, data);
        recman.commit();
    }

    public synchronized DadosVertex searchVertex(int idVertex) throws IOException {
        String id = Integer.toString(idVertex);
        return (DadosVertex) hashtable.get(id);
    }

    public synchronized void removeVertex(int idVertex) throws IOException {
        String id = Integer.toString(idVertex);
        hashtable.remove(id);
        recman.commit();
    }

    public ArrayList<String> ShowDataVertex() throws IOException {
        iter = hashtable.keys();
        ArrayList<String> list = new ArrayList<String>();

        String id = (String) iter.next();
        
        while (id != null) {
            list.add(id);
            id = (String) iter.next();
        }
        return list;
    }
}
