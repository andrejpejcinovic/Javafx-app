package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

public class ImenikAdministratorController implements Initializable {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    private Label label;
    @FXML
    public TableView<Osoba> tableAdministratorImenik;
    @FXML
    private TableColumn<Osoba, String> IDKorisnikaCol;
    @FXML
    private TableColumn<Osoba, String> IDCol;
    @FXML
    private TableColumn<Osoba, String> imeCol;
    @FXML
    private TableColumn<Osoba, String> prezimeCol;
    @FXML
    private TableColumn<Osoba, String> brojTelCol;
    @FXML
    private TableColumn editCol;

    @FXML
    private Button kontaktButton;
//----------------------------------------------------------------------------------------------------------------------

    private Connection connection;
    private ObservableList<Osoba> list;
    private BazaPodataka bazaPodataka;

    public static int ID1; //ID kontakata iz tablice
    //Podaci o odabranom kontaktu u tablici
    public static String ime;
    public static String prezime;
    public static String brojTel;

    LoginController podaci = new LoginController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        bazaPodataka = new BazaPodataka();
        popuniTablicu();
    }


    public void popuniTablicu() {
        try {
            //Instanciranje liste
            list = FXCollections.observableArrayList();

            //Postavljanje upita za bazu podataka
            String query = "SELECT ID, Ime, Prezime, BrojTelefona, IDKorisnika " +
                    "FROM imenik ORDER BY IDKorisnika";


            //Pokretanje upita i spremanje rezultata u ResultSet
            connection = bazaPodataka.getConnection();
            ResultSet set = connection.createStatement().executeQuery(query);



            //Izvodi se petlja kroz dobiveni rezultat koji vezemo za nasu listu
            while (set.next()) {
                //Stvaramo objekt klase Osoba, dodajemo mu podatke i vezemo ga za listu
                Osoba osoba = new Osoba();
                osoba.setIdKorisnika(set.getInt("IDKorisnika"));
                osoba.setID(set.getInt("ID"));
                osoba.setIme(set.getString("Ime"));
                osoba.setPrezime(set.getString("Prezime"));
                osoba.setBrojTel(set.getString("BrojTelefona"));

                list.add(osoba);
            }
            //Postavljanje vrijednosti u stupce

            IDKorisnikaCol.setCellValueFactory(new PropertyValueFactory<>("idKorisnika"));
            IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            imeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
            prezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
            brojTelCol.setCellValueFactory(new PropertyValueFactory<>("brojTel"));

            //Kreiranje tipki u svakom retku
            Callback<TableColumn<Osoba, String>, TableCell<Osoba,String>> cellFactory=(param) -> {
                final TableCell<Osoba, String> cell=new TableCell<Osoba, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //Osiguravamo da se ćelija kreira samo na nepraznim redovima
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            final Button editButton = new Button("Uredi kontakt");
                            final Button deleteButton = new Button("Obriši kontakt");

                            editButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                ID1 = p.getID();
                                ime = p.getIme();
                                prezime = p.getPrezime();
                                brojTel = p.getBrojTel();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("urediKontaktAdministrator.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stageUredi = new Stage();
                                    stageUredi.initModality(Modality.APPLICATION_MODAL);
                                    stageUredi.setTitle("Uređivanje kontakta");
                                    stageUredi.setScene(new Scene(root));
                                    stageUredi.setResizable(false);
                                    stageUredi.showAndWait();
                                    popuniTablicu();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                            });
                            deleteButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                ID1 = p.getID();
                                ime = p.getIme();
                                prezime = p.getPrezime();
                                brojTel = p.getBrojTel();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("obrisiKontaktAdministrator.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stageObrisi = new Stage();
                                    stageObrisi.initModality(Modality.APPLICATION_MODAL);
                                    stageObrisi.setTitle("Brisanje kontakta");
                                    stageObrisi.setScene(new Scene(root));
                                    stageObrisi.setResizable(false);
                                    stageObrisi.showAndWait();
                                    popuniTablicu();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                            });

                            HBox pane = new HBox(editButton, deleteButton);
                            setGraphic(pane);

                            setText(null);
                        };
                    };
                };
                //Vraćamo kreiranu ćeliju
                return cell;
            };

            editCol.setCellFactory(cellFactory);
            tableAdministratorImenik.setItems(list);

        }catch (SQLException throwables){
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

}
