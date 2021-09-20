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

public class UrediKontaktController {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private TextField urediImeField;

    @FXML
    private TextField urediPrezimeField;

    @FXML
    private TextField urediBrojTelField;

    @FXML
    private Label urediLabel;

    @FXML
    private Button urediKontaktButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private BazaPodataka bazaPodataka;

    ImenikController podaci = new ImenikController();

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void urediKontaktButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        if(urediImeField.getText().isBlank() == false && urediPrezimeField.getText().isBlank() == false
                && urediBrojTelField.getText().isBlank() == false) {
            urediOsobu();
            Stage stageUrediKontakt = (Stage) urediKontaktButton.getScene().getWindow();
            stageUrediKontakt.close();
        }else{
            urediLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    private void urediOsobu(){
        try{
            //Upit za azuriranje podataka kontakta u imeniku
            String query = "UPDATE imenik " +
                            "SET Ime = '" + urediImeField.getText() + "', Prezime = '" + urediPrezimeField.getText() + "', BrojTelefona = '" + urediBrojTelField.getText() + "' " +
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
