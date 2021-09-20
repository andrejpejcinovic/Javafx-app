package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParsePosition;
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
import javafx.util.converter.IntegerStringConverter;
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
import java.util.function.UnaryOperator;

public class UrediKontaktAdministratorController implements Initializable{

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Spinner<Integer> urediIDSpinner;

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

    ImenikAdministratorController podaci = new ImenikAdministratorController();

//Metode za kontrolu akcija tipki i spinnera ---------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb){
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
            valueFactory.setValue(1);
            urediIDSpinner.setValueFactory(valueFactory);
            urediIDSpinner.getEditor().setPromptText("ID");

            //Ogranicavamo vrijednosti spinnera na samo brojeve, takoder vrijednost ne smije biti 0
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                // NumberFormat procjenjuje pocetak teksta
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    // reject parsing the complete text failed
                    return null;
                }
            }
            return c;
        };


        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 1, filter);

        urediIDSpinner.getEditor().setTextFormatter(priceFormatter);
    }


    public void urediKontaktButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        //Ako su sva polja popunjena, tek onda prelazimo na uređivanje kontakta
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
                    "SET ID = '" + urediIDSpinner.getValue() + "', Ime = '" + urediImeField.getText() + "', Prezime = '" + urediPrezimeField.getText() + "', BrojTelefona = '" + urediBrojTelField.getText() + "' " +
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
