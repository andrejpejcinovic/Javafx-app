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


public class BlokirajKontaktController {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Button odustaniButton;

    @FXML
    private Button blokirajKontaktButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private sample.BazaPodataka bazaPodataka;

    ImenikController podaciImenik = new ImenikController();

//Metode za kontroliranje akcija tipki ---------------------------------------------------------------------------------
    public void blokirajKontaktButtonOnAction(ActionEvent event){
        bazaPodataka = new sample.BazaPodataka();
        blokirajOsobu();

        Stage stageBlokirajKontakt = (Stage) blokirajKontaktButton.getScene().getWindow();
        stageBlokirajKontakt.close();
    }

    public void odustaniButtonOnAction(ActionEvent event){
        Stage stageBlokirajKontakt = (Stage) odustaniButton.getScene().getWindow();
        stageBlokirajKontakt.close();
    }
//----------------------------------------------------------------------------------------------------------------------

//Metoda za blokiranje osobe, osobu koju blokiramo prebacujemo iz imenika u blok listu
    private void blokirajOsobu(){
        try{

            String query1 = "INSERT INTO blok_lista(Ime, Prezime, BrojTelefona, IDImenika, IDKorisnika) " +
                    "VALUES('" + podaciImenik.ime + "', '" + podaciImenik.prezime +"', '" + podaciImenik.brojTel +"', " + podaciImenik.ID1 + ", " + podaciImenik.ID + ")";


            String query2 = "DELETE FROM Imenik " +
                    "WHERE ID = " + podaciImenik.ID1;

            connection = bazaPodataka.getConnection();
            int set1 = connection.createStatement().executeUpdate(query1);
            int set2 = connection.createStatement().executeUpdate(query2);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
