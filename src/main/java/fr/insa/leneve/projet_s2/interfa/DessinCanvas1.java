/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.leneve.projet_s2.interfa;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import fr.insa.leneve.projet_s2.Figure;
import fr.insa.leneve.projet_s2.Groupe;
import fr.insa.leneve.projet_s2.Segment;
/**
 *
 * @author cheraita
 */
public class DessinCanvas1 extends Pane {
    
    private MainPanel main;
    
    private Canvas realCanvas;
    private RectangleHV asRect;
    
    public DessinCanvas1(MainPanel main){
        this.main=main;
        this.realCanvas = new Canvas(this.getWidth(),this.getHeight());
        this.asRect = new RectangleHV(0, 0, this.getWidth(), this.getHeight());
        this.getChildren().add(this.realCanvas);
        this.realCanvas.heightProperty().bind(this.heightProperty());
        this.realCanvas.heightProperty().addListener((o) -> {
             this.redrawAll();
             System.out.println("w = "+this.realCanvas.getWidth()+" ; h = "+this.realCanvas.getHeight());
        
        });
        this.realCanvas.widthProperty().bind(this.widthProperty());
        this.realCanvas.widthProperty().addListener((o) -> {
            this.redrawAll();
        });
        this.realCanvas.setOnMouseClicked((t) -> {
            System.out.println("clic");
            Controleur control = this.main.getControleur();
            control.clicDansZoneDessin(t);
        });
        this.realCanvas.setOnMouseMoved((t) -> {
            this.main.getControleur().mouseMovedDansZoneDessin(t);
        });
            this.redrawAll();
        
        
        
    }
    
     public void concatenateTransform(Transform trans) {
        Transform oldTrans = this.realCanvas.getGraphicsContext2D().getTransform();
        Transform newTrans = oldTrans.createConcatenation(trans);
        this.setTransform(newTrans);
    }

    public void setTransform(Transform trans) {
        this.realCanvas.getGraphicsContext2D().setTransform(new Affine(trans));
    }

    public Transform getTransform() {
        return this.realCanvas.getGraphicsContext2D().getTransform();
    }
    
    public void redrawAll() {
        GraphicsContext context = this.realCanvas.getGraphicsContext2D();
        context.setTransform(new Affine());
        context.clearRect(0, 0, this.realCanvas.getWidth(), this.realCanvas.getHeight());
        this.asRect.setxMax(this.realCanvas.getWidth());
        this.asRect.setyMax(this.realCanvas.getHeight());
        Transform curTrans = this.main.getZoneModelVue().fitTransform(this.asRect);
        this.setTransform(curTrans);
        Groupe model = this.main.getModel();
        model.dessine(context);
        List<Figure> select = this.main.getControleur().getSelection();
        if (!select.isEmpty()) {
            for (Figure f : select) {
                f.dessineSelection(context);
            }
        }
        Segment enCOurs = this.main.getControleur().getSegmentEnCoursDeCreation();
        if (enCOurs != null) {
            context.setLineDashes(1, 1);
            enCOurs.dessine(context);
            context.setLineDashes(null);
        }
    }
    /**
     * @return the realCanvas
     */
    public Canvas getRealCanvas() {
        return realCanvas;
    }
        
}
