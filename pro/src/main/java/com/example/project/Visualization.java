package com.example.project;

import com.example.project.Level2.GraphRepresentation.OurGraph;
import com.example.project.Level2.GraphRepresentation.User;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.MultiNode;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewPanel;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;

import java.io.IOException;


/*   /download.jpg  */

public class Visualization {


    public void visualize(Stage stage,String fileXML) throws IOException {
        System.setProperty("org.graphstream.ui", "javafx");

        Graph graph = new MultiGraph("Graph");
        graph.setAttribute("ui.stylesheet", "url('style.css')");
        OurGraph ourGraph = new OurGraph(fileXML);

        for(User u : ourGraph.users){
            MultiNode user = (MultiNode) graph.getNode(u.getId()+"");
            if(user == null){
                user = (MultiNode) graph.addNode(u.getId() + "");
                user.setAttribute("ui.label", u.getId() + "");
                for(Integer f : u.getFollowers()){
                    MultiNode follower = (MultiNode) graph.getNode(f.toString());
                    if(follower == null){
                        follower = (MultiNode) graph.addNode(f.toString());
                        follower.setAttribute("ui.label", f.toString());
                    }
                    Edge e = graph.addEdge(u.getId() + " " + f,user,follower,true);
                }
            }else{
                for(Integer f : u.getFollowers()){
                    MultiNode follower = (MultiNode) graph.getNode(f.toString());
                    if(follower == null){
                        follower = (MultiNode) graph.addNode(f.toString());
                        follower.setAttribute("ui.label", f.toString());
                    }
                    Edge e = graph.addEdge(u.getId() + " " + f,user,follower,true);
                }
        }
        }


        /* These 3 lines show correctly a graph, ON A SEPARATE WINDOW */
        Viewer viewer;
        viewer = graph.display(true);
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);


        // Those below both do not work. I tried many vestions of gs-ui-javafx but ...
        //https://github.com/graphstream/gs-ui-javafx/blob/master/src-test/org/graphstream/ui/viewer_fx/test/AllFxTest.java
        FxViewer fxviewer = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        FxGraphRenderer renderer = new FxGraphRenderer();
        FxDefaultView view = (FxDefaultView) fxviewer.addView(FxViewer.DEFAULT_VIEW_ID, renderer);

        /* https://github.com/graphstream/gs-ui-javafx/blob/master/src-test/org/graphstream/ui/viewer_fx/test/AllFxTest.java */
        FxViewer v = new FxViewer(graph, FxViewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        v.enableAutoLayout();
        FxViewPanel panel;
        panel = (FxViewPanel)v.addDefaultView(false, new FxGraphRenderer());
        Scene scene2 = new Scene(panel, 800, 600);
        stage.setScene(scene2);
        stage.show();

    }
}
