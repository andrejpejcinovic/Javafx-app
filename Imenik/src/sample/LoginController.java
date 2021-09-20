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

public class LoginController {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Label loginMessageLabel;

    @FXML
    public TextField usernameTextField;

    @FXML
    public PasswordField enterPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;
//----------------------------------------------------------------------------------------------------------------------

    //Pohranjeni podaci korisnika za buduću upotrebu
    public static String username;
    public static String password;
    public static String uloga;

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void loginButtonOnAction(ActionEvent event){
        //Provjerava se jesu li unesena oba polja za prijavu
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            username = usernameTextField.getText();
            password = enterPasswordField.getText();
            validateLogin();
        }
        else{
            loginMessageLabel.setText("Unesite E-Mail i lozinku!");
        }
    }

    public void registerButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registracija.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageRegistracija = new Stage();
            stageRegistracija.initModality(Modality.APPLICATION_MODAL);
            stageRegistracija.setTitle("Registracija");
            stageRegistracija.setScene(new Scene(root));
            stageRegistracija.setResizable(false);
            stageRegistracija.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
//----------------------------------------------------------------------------------------------------------------------

    //Metoda za provjeru tocnosti login forme
    public void validateLogin(){
        BazaPodataka connectNow = new BazaPodataka();
        Connection connectDB = connectNow.getConnection();

        //Upit za bazu podataka, provjerava se postoji li u bazi korisnik sa danim korisnickim imenom i lozinkom
        String verfyLogin = "SELECT * FROM korisnik WHERE KorisnickoIme = '"+ usernameTextField.getText() + "' AND Lozinka = '" + enterPasswordField.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verfyLogin);

                //Ako je upit vratio vrijednost, onda na osnovu uloge korisnika preusmjeravamo na predodređeni FXML
                if(queryResult.next()){
                    loginMessageLabel.setText("Uspješna prijava!");
                    Stage stageLogin = (Stage) loginButton.getScene().getWindow();
                    stageLogin.close();
                    uloga = queryResult.getString("Uloga");
                    if(uloga.equals("Korisnik")) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imenik.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stageImenik = new Stage();
                            stageImenik.initModality(Modality.APPLICATION_MODAL);
                            stageImenik.setTitle("Imenik");
                            stageImenik.setScene(new Scene(root));
                            stageImenik.setResizable(false);
                            stageImenik.show();
                            stageImenik.onCloseRequestProperty().setValue(e -> Platform.exit());
                        } catch (Exception e) {
                            e.printStackTrace();
                            e.getCause();
                        }
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("korisniciAdministrator.fxml"));
                            Parent root = (Parent) fxmlLoader.load();
                            Stage stageKorisnici = new Stage();
                            stageKorisnici.initModality(Modality.APPLICATION_MODAL);
                            stageKorisnici.setTitle("Lista korisnika");
                            stageKorisnici.setScene(new Scene(root));
                            stageKorisnici.setResizable(false);
                            stageKorisnici.show();
                            stageKorisnici.onCloseRequestProperty().setValue(e -> Platform.exit());
                        } catch (Exception e) {
                            e.printStackTrace();
                            e.getCause();
                        }
                    }
                } else {
                    loginMessageLabel.setText("Neuspješna prijava! Pokušajte ponovo!");
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
