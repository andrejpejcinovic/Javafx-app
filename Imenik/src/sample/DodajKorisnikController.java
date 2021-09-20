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

public class DodajKorisnikController implements Initializable{

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private TextField dodajEmailField;

    @FXML
    private TextField dodajLozinkaField;

    @FXML
    private ComboBox<String> dodajUlogaBox;

    @FXML
    private Label dodajLabel;

    @FXML
    private Button dodajKorisnikButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private BazaPodataka bazaPodataka;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Elementi i prompt text za 'dropdown menu' Uloga
        dodajUlogaBox.getItems().add("Korisnik");
        dodajUlogaBox.getItems().add("Administrator");
        dodajUlogaBox.setPromptText("Uloga");
    }

//Metode za kontroliranje akcija tipki ---------------------------------------------------------------------------------
    public void dodajKorisnikButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        if(dodajEmailField.getText().isBlank() == false && dodajLozinkaField.getText().isBlank() == false
                && (dodajUlogaBox.getValue() == null) == false) {
            dodajKorisnika();
            Stage stageDodajKorisnik = (Stage) dodajKorisnikButton.getScene().getWindow();
            stageDodajKorisnik.close();
        }else{
            dodajLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    //Metoda za dodavanje korisnika u tablicu korisnika
    private void dodajKorisnika(){
        try{
            //Upit bazi podataka za dodavanje korisnika sa unesenim korisnickim imenom, lozinkom, i ulogom
            String query = "INSERT INTO korisnik(KorisnickoIme, Lozinka, Uloga) " +
                    "VALUES('"+ dodajEmailField.getText() +"', '" + dodajLozinkaField.getText() + "', '" + dodajUlogaBox.getValue()+"')";

            //Izvrsavanje upita
            connection = bazaPodataka.getConnection();
            int set = connection.createStatement().executeUpdate(query);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
