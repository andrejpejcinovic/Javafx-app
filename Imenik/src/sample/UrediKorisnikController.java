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

public class UrediKorisnikController implements Initializable{

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private TextField urediKorImeField;

    @FXML
    private TextField urediLozinkuField;

    @FXML
    private ComboBox<String> urediUloguBox;

    @FXML
    private Label urediLabel;

    @FXML
    private Button urediKorisnikButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private BazaPodataka bazaPodataka;

    KorisniciAdministratorController podaci = new KorisniciAdministratorController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Elementi i prompt text za 'dropdown menu' Uloga
        urediUloguBox.getItems().add("Korisnik");
        urediUloguBox.getItems().add("Administrator");
        urediUloguBox.setPromptText("Uloga");
    }

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void urediKorisnikButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        //Ako su sva polja popunjena, tek onda pozivamo metodu urediOsobu()
        if(urediKorImeField.getText().isBlank() == false
                && urediLozinkuField.getText().isBlank() == false
                && (urediUloguBox.getValue() == null) == false) {
            urediOsobu();
            Stage stageUrediKontakt = (Stage) urediKorisnikButton.getScene().getWindow();
            stageUrediKontakt.close();
        }else{
            urediLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    private void urediOsobu(){
        try{
            //Upit bazi podataka za azuriranje podataka o korisniku u tablici korisnik
            String query = "UPDATE korisnik " +
                    "SET KorisnickoIme = '" + urediKorImeField.getText() + "', Lozinka = '" + urediLozinkuField.getText() + "', Uloga = '" + urediUloguBox.getValue() + "' " +
                    "WHERE ID = " + podaci.ID1;

            //Izvrsavanje upita
            connection = bazaPodataka.getConnection();
            int set = connection.createStatement().executeUpdate(query);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
