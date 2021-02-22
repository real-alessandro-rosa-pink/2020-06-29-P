/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenza;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Partita;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Partita> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Partita> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	txtResult.appendText("Coppie con connessione massima:\n\n");
    	for(Adiacenza arco : this.model.connessioneMassima()) {
    		txtResult.appendText(arco.toString()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer mese = cmbMese.getValue();
    	
    	if( mese == null ) {
    		txtResult.appendText("ERRORE : Devi selezionare un mese");
    		return ;
    	}
    	
    	String timePlayedString = txtMinuti.getText();
    	try {
    		Integer timePlayedInteger;
    		timePlayedInteger = Integer.parseInt(timePlayedString);
    		txtResult.appendText(this.model.creaGrafo(timePlayedInteger, mese));
    	} catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : formato numerico non valido");
    		return ;
    	}
    	
    	this.btnConnessioneMassima.setDisable(false);
    	this.cmbM1.setDisable(false);
    	this.cmbM2.setDisable(false);
    	this.btnCollegamento.setDisable(false);
    	this.cmbM1.getItems().addAll(this.model.getPartiteGrafo());
    	this.cmbM2.getItems().addAll(this.model.getPartiteGrafo());
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	txtResult.appendText("RICORSIONE : Cammino aciclico di peso massimo\n");
    	
    	Partita p1 = this.cmbM1.getValue();
    	Partita p2 = this.cmbM2.getValue();
    	if( p1 == null || p2 == null ) {
    		txtResult.appendText("ERRORE : devi specificare sia il vertice di partenza che di destinazione");
    		return ;
    	}
    	
    	List<Partita> percorsoPesoMassimo = new ArrayList<>();
    	percorsoPesoMassimo = this.model.trovaPercorsoPesoMassimo(p1, p2);
    	
    	for(Partita p : percorsoPesoMassimo) {
    		txtResult.appendText(p.toString()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMese.getItems().addAll(this.model.getMesi());
    	this.btnConnessioneMassima.setDisable(true);
    	this.cmbM1.setDisable(true);
    	this.cmbM2.setDisable(true);
    	this.btnCollegamento.setDisable(true);
    }
    
    
}
