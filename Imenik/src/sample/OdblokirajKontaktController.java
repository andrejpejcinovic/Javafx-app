package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;

public class OdblokirajKontaktController {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Button odustaniButton;

    @FXML
    private Button odblokirajKontaktButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private sample.BazaPodataka bazaPodataka;

    ImenikController podaciImenik = new ImenikController();
    BlokListaController podaciBlokLista = new BlokListaController();

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void odblokirajKontaktButtonOnAction(ActionEvent event){
        bazaPodataka = new sample.BazaPodataka();
        odblokirajOsobu();

        Stage stageOdblokirajKontakt = (Stage) odblokirajKontaktButton.getScene().getWindow();
        stageOdblokirajKontakt.close();
    }

    public void odustaniButtonOnAction(ActionEvent event){
        Stage stageOdblokirajKontakt = (Stage) odustaniButton.getScene().getWindow();
        stageOdblokirajKontakt.close();
    }
//----------------------------------------------------------------------------------------------------------------------

    //Metoda za prebacivanje osobe natrag u imenik
    private void odblokirajOsobu(){
        try{

            //Upit bazi podataka za ubacivanje kontakta iz blok liste natrag u imenik
            String query1 = "INSERT INTO imenik(Ime, Prezime, BrojTelefona, IDKorisnika) " +
                    "VALUES('" + podaciBlokLista.imeBlok + "', '" + podaciBlokLista.prezimeBlok +"', '" + podaciBlokLista.brojTelBlok +"', " + podaciImenik.ID + ")";

            //Upit bazi podataka za brisanje kontakta iz blok liste
            String query2 = "DELETE FROM blok_lista " +
                    "WHERE ID = " + podaciBlokLista.IDBlok;

            //Izvrsavanje upita
            connection = bazaPodataka.getConnection();
            int set1 = connection.createStatement().executeUpdate(query1);

            int set2 = connection.createStatement().executeUpdate(query2);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
