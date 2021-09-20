package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import sample.BazaPodataka;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegistracijaController {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Label registerLabel;

    @FXML
    public TextField korImeField;

    @FXML
    public TextField lozinkaField;

    @FXML
    public TextField imeField;

    @FXML
    public TextField prezimeField;

    @FXML
    public TextField brojTelField;

    @FXML
    public TextField adresaField;

    @FXML
    private Button registerButton;
//----------------------------------------------------------------------------------------------------------------------


    public static String username;
    public static String password;
    public static String IDKorisnika;

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void registerButtonOnAction(ActionEvent event){
        //Ako su sva polja popunjena, tek onda prelazimo na validaciju registracije
        if(korImeField.getText().isBlank() == false && lozinkaField.getText().isBlank() == false
                && imeField.getText().isBlank() == false && prezimeField.getText().isBlank() == false
                && brojTelField.getText().isBlank() == false && adresaField.getText().isBlank() == false) {
            if((lozinkaField.getText().length() < 6) == false) {
                validateRegistration();
            }else{
                registerLabel.setText("Lozinka mora imati minimalno 6 znakova!");
            }
        }else{
            registerLabel.setText("Molimo popunite sva polja!");
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    //Metoda za provjeru registracije
    public void validateRegistration(){
        BazaPodataka connectNow = new BazaPodataka();
        Connection connectDB = connectNow.getConnection();

        //Upit bazi podataka za provjeru postoji li vec korisnik sa istim korisnickim imenom
        String verfyRegister = "SELECT * FROM korisnik WHERE KorisnickoIme = '"+ korImeField.getText() + "'";

        //Upit bazi podataka za ubacivanje korisnika u listu korisnika
        String registerKorisnik = "INSERT INTO korisnik(KorisnickoIme, Lozinka, Uloga) " +
                "VALUES('"+ korImeField.getText() +"', '" + lozinkaField.getText() + "', 'Korisnik')";


        try{
            //Nakon sto utvrdimo da korisnik nije unutar tablice korisnika, dodajemo ga u tablicu
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verfyRegister);

            if(queryResult.next()){

                registerLabel.setText("Korisničko ime već postoji! Pokušajte ponovno!");

            } else {
                int queryResult1 = statement.executeUpdate(registerKorisnik);
                queryResult = statement.executeQuery(verfyRegister);
                while(queryResult.next()){
                    IDKorisnika = queryResult.getString("ID");
                }
                //Kreiramo upit bazi podataka za ubacivanje korisnikovih podataka u tablicu podataka
                String registerPodaci = "INSERT INTO podaci(Ime, Prezime, BrojTelefona, Adresa, IDKorisnika) " +
                        "VALUES('"+ imeField.getText() +"', '" + prezimeField.getText() + "', '" + brojTelField.getText() + "', '" + adresaField.getText() + "', '" + IDKorisnika + "')";
                int queryResult2 = statement.executeUpdate(registerPodaci);
                Stage stageRegister = (Stage) registerButton.getScene().getWindow();
                stageRegister.close();
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
