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

public class ImenikController implements Initializable {
//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    private Label label;
    @FXML
    public TableView<Osoba> tableImenik;
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

    @FXML
    private Button podaciButton;

    @FXML
    private Button blokListaButton;

    @FXML
    private Button favoritiButton;

    @FXML
    private Button odjavaButton;

    @FXML
    private Label imeLabel;

    @FXML
    private Label prezimeLabel;

    @FXML
    private Label adresaLabel;

    @FXML
    private Label brojTelLabel;
//----------------------------------------------------------------------------------------------------------------------


    private Connection connection;
    private ObservableList<Osoba> list;
    private BazaPodataka bazaPodataka; //Varijabla kojom se spajamo na bazu podataka

    public static String ID; // SQL upit koji daje ID ćeliju tablice 'korisnik'
    //Podaci o odabranom kontaktu u tablici
    public static int ID1;
    public static String ime;
    public static String prezime;
    public static String brojTel;

    public int vecDodano = 0; //varijabla za provjeru da li je kontakt već dodan u favorite

    LoginController podaci = new LoginController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        bazaPodataka = new BazaPodataka();
        popuniTablicu();
    }

//Metode za kontroliranje akcija tipki ---------------------------------------------------------------------------------
    public void kontaktButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodajKontakt.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageKontakt = new Stage();
            stageKontakt.initModality(Modality.APPLICATION_MODAL);
            stageKontakt.setTitle("Dodavanje kontakta");
            stageKontakt.setScene(new Scene(root));
            stageKontakt.setResizable(false);
            stageKontakt.showAndWait();
            popuniTablicu();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void podaciButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("urediPodatke.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stagePodaci = new Stage();
            stagePodaci.initModality(Modality.APPLICATION_MODAL);
            stagePodaci.setTitle("Uređivanje podataka");
            stagePodaci.setScene(new Scene(root));
            stagePodaci.setResizable(false);
            stagePodaci.showAndWait();
            popuniTablicu();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void blokListaButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("blokLista.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageBlokLista = new Stage();
            stageBlokLista.initModality(Modality.APPLICATION_MODAL);
            stageBlokLista.setTitle("Blok Lista");
            stageBlokLista.setScene(new Scene(root));
            stageBlokLista.setResizable(false);
            stageBlokLista.showAndWait();
            popuniTablicu();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void favoritiButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favoriti.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageFavoriti = new Stage();
            stageFavoriti.initModality(Modality.APPLICATION_MODAL);
            stageFavoriti.setTitle("Favoriti");
            stageFavoriti.setScene(new Scene(root));
            stageFavoriti.setResizable(false);
            stageFavoriti.showAndWait();
            popuniTablicu();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void odjavaButtonOnAction(ActionEvent event){
        Stage stageImenik = (Stage) odjavaButton.getScene().getWindow();
        stageImenik.close();
    }
//----------------------------------------------------------------------------------------------------------------------



    public void popuniTablicu() {
        try {
            //Instanciranje liste
            list = FXCollections.observableArrayList();

            //Postavljanje upita za bazu podataka
            String query = "SELECT ID, Ime, Prezime, BrojTelefona " +
                            "FROM imenik " +
                            "WHERE IDKorisnika = (SELECT ID " +
                                                    "FROM korisnik " +
                                                    "WHERE KorisnickoIme = '" + podaci.username + "' " +
                                                    "AND Lozinka = '" +  podaci.password + "')";

            ID = "(SELECT ID " +
            "FROM korisnik " +
                    "WHERE KorisnickoIme = '" + podaci.username + "' " +
                    "AND Lozinka = '" +  podaci.password + "')";


            //Pokretanje upita i spremanje rezultata u ResultSet
            connection = bazaPodataka.getConnection();
            ResultSet set = connection.createStatement().executeQuery(query);

            //Ucitavanje podataka o korisniku-----------------------------------------

            String upit = "SELECT * FROM podaci WHERE IDKorisnika = " + ID;
            ResultSet set1 = connection.createStatement().executeQuery(upit);


            while(set1.next()){
               imeLabel.setText(set1.getString("Ime"));
                prezimeLabel.setText(set1.getString("Prezime"));
                brojTelLabel.setText(set1.getString("BrojTelefona"));
                adresaLabel.setText(set1.getString("Adresa"));
            }
            //-------------------------------------------------------------------------


            //Izvodi se petlja kroz dobiveni rezultat koji vezemo za nasu listu
            while (set.next()) {
                //Stvaramo objekt klase Osoba, dodajemo mu podatke i vezemo ga za listu
                Osoba osoba = new Osoba();
                osoba.setID(set.getInt("ID"));
                osoba.setIme(set.getString("Ime"));
                osoba.setPrezime(set.getString("Prezime"));
                osoba.setBrojTel(set.getString("BrojTelefona"));

                list.add(osoba);
            }
            //Postavljanje vrijednosti u stupce

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
                            final Button editButton = new Button("Uredi");
                            final Button deleteButton = new Button("Obriši");
                            final Button blockButton = new Button("Blokiraj");
                            final Button favoriteButton = new Button("★");

                            editButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                ID1 = p.getID();
                                ime = p.getIme();
                                prezime = p.getPrezime();
                                brojTel = p.getBrojTel();
                                    try {
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("urediKontakt.fxml"));
                                        Parent root = (Parent) fxmlLoader.load();
                                        Stage stageUredi = new Stage();
                                        stageUredi.initModality(Modality.APPLICATION_MODAL);
                                        stageUredi.setTitle("Uređivanje kontakta");
                                        stageUredi.setScene(new Scene(root));
                                        stageUredi.setResizable(false);
                                        stageUredi.showAndWait();
                                        popuniTablicu();
                                    } catch (Exception e) {
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
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("obrisiKontakt.fxml"));
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
                            blockButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                ID1 = p.getID();
                                ime = p.getIme();
                                prezime = p.getPrezime();
                                brojTel = p.getBrojTel();

                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("blokirajKontakt.fxml"));
                                    Parent root2 = (Parent) fxmlLoader.load();
                                    Stage stageBlokiraj = new Stage();
                                    stageBlokiraj.initModality(Modality.APPLICATION_MODAL);
                                    stageBlokiraj.setTitle("Blokiranje kontakta");
                                    stageBlokiraj.setScene(new Scene(root2));
                                    stageBlokiraj.setResizable(false);
                                    stageBlokiraj.showAndWait();
                                    popuniTablicu();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                            });


                            favoriteButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                ID1 = p.getID();
                                ime = p.getIme();
                                prezime = p.getPrezime();
                                brojTel = p.getBrojTel();
                                try{
                                    String favoriti="SELECT ID " +
                                            "FROM favoriti " +
                                            "WHERE IDImenika = '" +ID1+"'";
                                    ResultSet set2 = connection.createStatement().executeQuery(favoriti);
                                    if(set2.next()){
                                        vecDodano = 1;
                                    }else{
                                        vecDodano=0;
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                                if(vecDodano != 1) {
                                    try {
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favoritKontakt.fxml"));
                                        Parent root = (Parent) fxmlLoader.load();
                                        Stage stageFavorit = new Stage();
                                        stageFavorit.initModality(Modality.APPLICATION_MODAL);
                                        stageFavorit.setTitle("Dodavanje u favorite");
                                        stageFavorit.setScene(new Scene(root));
                                        stageFavorit.setResizable(false);
                                        stageFavorit.showAndWait();
                                        popuniTablicu();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        e.getCause();
                                    }
                                }
                                else{
                                    try {
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vecDodano.fxml"));
                                        Parent root = (Parent) fxmlLoader.load();
                                        Stage stageDodano = new Stage();
                                        stageDodano.initModality(Modality.APPLICATION_MODAL);
                                        stageDodano.setTitle("Dodavanje u favorite");
                                        stageDodano.setScene(new Scene(root));
                                        stageDodano.setResizable(false);
                                        stageDodano.showAndWait();
                                        popuniTablicu();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        e.getCause();
                                    }
                                }
                            });
                            HBox pane = new HBox(editButton, deleteButton, blockButton, favoriteButton);
                            setGraphic(pane);

                            setText(null);
                        };
                    };
                };
                //Vraćamo kreiranu ćeliju
                return cell;
            };

            editCol.setCellFactory(cellFactory);
            tableImenik.setItems(list);

        }catch (SQLException throwables){
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

}
