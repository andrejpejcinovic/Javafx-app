package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sample.BazaPodataka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.LoginController;
import java.sql.Connection;
import java.sql.DriverManager;

public class DodajKontaktController /*implements Initializable*/{

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private TextField dodajImeField;

    @FXML
    private TextField dodajPrezimeField;

    @FXML
    private TextField dodajBrojTelField;

    @FXML
    private Label dodajLabel;

    @FXML
    private Button dodajKontaktButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private BazaPodataka bazaPodataka;

    ImenikController podaci = new ImenikController();

//Metode za kontroliranje akcija tipki ---------------------------------------------------------------------------------
    public void dodajKontaktButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        if(dodajImeField.getText().isBlank() == false && dodajPrezimeField.getText().isBlank() == false
                && dodajBrojTelField.getText().isBlank() == false) {
            dodajOsobu();
            Stage stageDodajKontakt = (Stage) dodajKontaktButton.getScene().getWindow();
            stageDodajKontakt.close();
        }else{
            dodajLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------

//Metoda za dodavanje kontakta u imenik
    private void dodajOsobu(){
        try{
            //Upit bazi podataka za ubacivanje podatka u tablicu imenik
            String query = "INSERT INTO imenik(Ime, Prezime, BrojTelefona, IDKorisnika) " +
                            "VALUES('"+ dodajImeField.getText() +"', '" + dodajPrezimeField.getText() + "', '" + dodajBrojTelField.getText()+"', " + podaci.ID + ")";

            //Izvrsavanje upita
            connection = bazaPodataka.getConnection();
            int set = connection.createStatement().executeUpdate(query);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
