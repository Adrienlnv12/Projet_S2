/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insa.leneve.projet_s2.interfa;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import fr.insa.leneve.projet_s2.Groupe;

/**
 *
 * @author cheraita
 */
public class MainFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Scene sc = new Scene(new MainPanel(stage,Groupe.groupeTest()),800,600);
        stage.setScene(sc);
        stage.setTitle("Modélisateur de Treillis");
        stage.show();
          
    }

    public static void main(String[] args) {
        launch();
    }

}