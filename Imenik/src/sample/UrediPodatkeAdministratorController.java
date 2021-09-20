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

public class UrediPodatkeAdministratorController implements Initializable{

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Spinner<Integer> podaciIDSpinner;

    @FXML
    private TextField podaciImeField;

    @FXML
    private TextField podaciPrezimeField;

    @FXML
    private TextField podaciAdresaField;

    @FXML
    private TextField podaciBrojTelField;

    @FXML
    private Label urediLabel;

    @FXML
    private Button urediPodatkeButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private BazaPodataka bazaPodataka;

    PodaciAdministratorController podaci = new PodaciAdministratorController();

//Metode za kontrolu akcija tipki i spinnera ---------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb){
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(1);
        podaciIDSpinner.setValueFactory(valueFactory);
        podaciIDSpinner.getEditor().setPromptText("ID");

        //Ogranicavamo vrijednosti spinnera na samo brojeve, takoder vrijednost ne smije biti 0
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                // NumberFormat procjenjuje početak teksta
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

        podaciIDSpinner.getEditor().setTextFormatter(priceFormatter);
    }


    public void urediPodatkeButtonOnAction(ActionEvent event){
        bazaPodataka = new BazaPodataka();
        //Ako su sva polja popunjena, tek onda prelazimo na uredivanje podataka
        if(podaciImeField.getText().isBlank() == false && podaciPrezimeField.getText().isBlank() == false
                && podaciAdresaField.getText().isBlank() == false && podaciBrojTelField.getText().isBlank() == false) {
            urediPodatke();
            Stage stageUrediPodatke = (Stage) urediPodatkeButton.getScene().getWindow();
            stageUrediPodatke.close();
        }else{
            urediLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------


    private void urediPodatke(){
        try{
            //Upit bazi podataka za azuriranje podataka o korisniku u tablici 'podaci'
            String query = "UPDATE podaci " +
                    "SET ID = '" + podaciIDSpinner.getValue() + "', Ime = '" + podaciImeField.getText() + "', Prezime = '" + podaciPrezimeField.getText() + "', Adresa = '" + podaciAdresaField.getText() + "', BrojTelefona = '" + podaciBrojTelField.getText() + "' " +
                    "WHERE IDKorisnika = " + podaci.IDKorisnikaPodaci;

            //Izvrsavanje upita
            connection = bazaPodataka.getConnection();
            int set = connection.createStatement().executeUpdate(query);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
