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

public class PodaciAdministratorController implements Initializable {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    private Label label;
    @FXML
    public TableView<Osoba> tablePodaciAdministrator;
    @FXML
    private TableColumn<Osoba, String> IDCol;
    @FXML
    private TableColumn<Osoba, String> imeCol;
    @FXML
    private TableColumn<Osoba, String> prezimeCol;
    @FXML
    private TableColumn<Osoba, String> brojTelCol;
    @FXML
    private TableColumn<Osoba, String> adresaCol;
    @FXML
    private TableColumn<Osoba, String> IDKorisnikaCol;
    @FXML
    private TableColumn editCol;
//----------------------------------------------------------------------------------------------------------------------


    private Connection connection;
    private ObservableList<Osoba> list;
    private BazaPodataka bazaPodataka;

    //Podaci o odabranom korisniku u tablici
    public static int IDPodaci;
    public static String imePodaci;
    public static String prezimePodaci;
    public static String brojTelPodaci;
    public static String adresaPodaci;
    public static int IDKorisnikaPodaci;

    LoginController podaciLogin = new LoginController();
    ImenikController podaciImenik = new ImenikController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        bazaPodataka = new BazaPodataka();
        popuniPodatke();
    }

    public void popuniPodatke() {
        try {
            //Instanciranje liste
            list = FXCollections.observableArrayList();

            //Postavljanje upita za bazu podataka
            String query = "SELECT ID, Ime, Prezime, BrojTelefona, Adresa, IDKorisnika " +
                    "FROM podaci " +
                    "ORDER BY IDKorisnika";


            //Pokretanje upita i spremanje rezultata u ResultSet
            connection = bazaPodataka.getConnection();
            ResultSet set = connection.createStatement().executeQuery(query);


            //Izvodi se petlja kroz dobiveni rezultat koji vezemo za nasu listu
            while (set.next()) {
                //Stvaramo objekt klase Osoba, dodajemo mu podatke i vezemo ga za listu
                Osoba osoba = new Osoba();
                osoba.setID(set.getInt("ID"));
                osoba.setIme(set.getString("Ime"));
                osoba.setPrezime(set.getString("Prezime"));
                osoba.setBrojTel(set.getString("BrojTelefona"));
                osoba.setAdresa(set.getString("Adresa"));
                osoba.setIdKorisnika(set.getInt("IDKorisnika"));

                list.add(osoba);
            }
            //Postavljanje vrijednosti u stupce

            IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            imeCol.setCellValueFactory(new PropertyValueFactory<>("ime"));
            prezimeCol.setCellValueFactory(new PropertyValueFactory<>("prezime"));
            brojTelCol.setCellValueFactory(new PropertyValueFactory<>("brojTel"));
            adresaCol.setCellValueFactory(new PropertyValueFactory<>("adresa"));
            IDKorisnikaCol.setCellValueFactory(new PropertyValueFactory<>("idKorisnika"));

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
                            final Button editButton = new Button("Uredi podatke");

                            editButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                IDPodaci = p.getID();
                                imePodaci = p.getIme();
                                prezimePodaci = p.getPrezime();
                                brojTelPodaci = p.getBrojTel();
                                adresaPodaci = p.getBrojTel();
                                IDKorisnikaPodaci = p.getIDKorisnika();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("urediPodatkeAdministrator.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stagePodaci = new Stage();
                                    stagePodaci.initModality(Modality.APPLICATION_MODAL);
                                    stagePodaci.setTitle("Uređivanje podataka");
                                    stagePodaci.setScene(new Scene(root));
                                    stagePodaci.setResizable(false);
                                    stagePodaci.showAndWait();
                                    popuniPodatke();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                            });
                            HBox pane = new HBox(editButton);
                            setGraphic(pane);

                            setText(null);
                        };
                    };
                };
                //Vraćamo kreiranu ćeliju
                return cell;
            };

            editCol.setCellFactory(cellFactory);

            tablePodaciAdministrator.setItems(list);

        }catch (SQLException throwables){
            throwables.printStackTrace();
            throwables.getCause();
        }
    }
}