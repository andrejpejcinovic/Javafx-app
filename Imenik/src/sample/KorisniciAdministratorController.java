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

public class KorisniciAdministratorController implements Initializable {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    private Label label;
    @FXML
    public TableView<Korisnik> tableKorisnik;
    @FXML
    private TableColumn<Korisnik, String> IDCol;
    @FXML
    private TableColumn<Korisnik, String> korisnickoImeCol;
    @FXML
    private TableColumn<Korisnik, String> lozinkaCol;
    @FXML
    private TableColumn<Korisnik, String> ulogaCol;
    @FXML
    private TableColumn editCol;

    @FXML
    private Button korisnikButton;

    @FXML
    private Button imenikButton;

    @FXML
    private Button blokListaButton;

    @FXML
    private Button podaciButton;

    @FXML
    private Button odjavaButton;
//----------------------------------------------------------------------------------------------------------------------


    private Connection connection;
    private ObservableList<Korisnik> list;
    private BazaPodataka bazaPodataka;


    public static int ID1; //ID kontakata iz tablice
    //Podaci o odabranom korisniku iz tablice korisnika
    public static String korisnickoIme;
    public static String lozinka;
    public static String uloga;

    LoginController podaci = new LoginController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        bazaPodataka = new BazaPodataka();
        popuniTablicu();
    }

//Metode za kontroliranje akcija tipki ---------------------------------------------------------------------------------
    public void korisnikButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dodajKorisnik.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageKorisnik = new Stage();
            stageKorisnik.initModality(Modality.APPLICATION_MODAL);
            stageKorisnik.setTitle("Dodavanje korisnika");
            stageKorisnik.setScene(new Scene(root));
            stageKorisnik.setResizable(false);
            stageKorisnik.showAndWait();
            popuniTablicu();
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void imenikButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imenikAdministrator.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageImenik = new Stage();
            stageImenik.initModality(Modality.APPLICATION_MODAL);
            stageImenik.setTitle("Imenik");
            stageImenik.setScene(new Scene(root));
            stageImenik.setResizable(false);
            stageImenik.showAndWait();
            popuniTablicu();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void blokListaButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("blokListaAdministrator.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("favoritiAdministrator.fxml"));
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

    public void podaciButtonOnAction(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("podaciAdministrator.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stagePodaci = new Stage();
            stagePodaci.initModality(Modality.APPLICATION_MODAL);
            stagePodaci.setTitle("Korisnički podaci");
            stagePodaci.setScene(new Scene(root));
            stagePodaci.setResizable(false);
            stagePodaci.showAndWait();
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
            String query = "SELECT * " +
                    "FROM korisnik";



            //Pokretanje upita i spremanje rezultata u ResultSet
            connection = bazaPodataka.getConnection();
            ResultSet set = connection.createStatement().executeQuery(query);


            //Izvodi se petlja kroz dobiveni rezultat koji vezemo za nasu listu
            while (set.next()) {
                //Stvaramo objekt klase Korisnik, dodajemo mu podatke i vezemo ga za listu
                Korisnik korisnik = new Korisnik();
                korisnik.setID(set.getInt("ID"));
                korisnik.setKorisnickoIme(set.getString("KorisnickoIme"));
                korisnik.setLozinka(set.getString("Lozinka"));
                korisnik.setUloga(set.getString("Uloga"));

                list.add(korisnik);
            }
            //Postavljanje vrijednosti u stupce

            IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
            korisnickoImeCol.setCellValueFactory(new PropertyValueFactory<>("korisnickoIme"));
            lozinkaCol.setCellValueFactory(new PropertyValueFactory<>("lozinka"));
            ulogaCol.setCellValueFactory(new PropertyValueFactory<>("uloga"));

            //Kreiranje tipki u svakom retku
            Callback<TableColumn<Korisnik, String>, TableCell<Korisnik,String>> cellFactory=(param) -> {
                final TableCell<Korisnik, String> cell=new TableCell<Korisnik, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        //Osiguravamo da se ćelija kreira samo na nepraznim redovima
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            final Button editButton = new Button("Uredi korisnika");
                            final Button deleteButton = new Button("Obriši korisnika");

                            editButton.setOnAction(event -> {
                                Korisnik k = getTableView().getItems().get(getIndex());
                                ID1 = k.getID();
                                korisnickoIme = k.getKorisnickoIme();
                                lozinka = k.getLozinka();
                                uloga = k.getUloga();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("urediKorisnik.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stageUredi = new Stage();
                                    stageUredi.initModality(Modality.APPLICATION_MODAL);
                                    stageUredi.setTitle("Uređivanje korisnika");
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
                                Korisnik k = getTableView().getItems().get(getIndex());
                                ID1 = k.getID();
                                korisnickoIme = k.getKorisnickoIme();
                                lozinka = k.getLozinka();
                                uloga = k.getUloga();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("obrisiKorisnik.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stageObrisi = new Stage();
                                    stageObrisi.initModality(Modality.APPLICATION_MODAL);
                                    stageObrisi.setTitle("Brisanje korisnika");
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

            tableKorisnik.setItems(list);

        }catch (SQLException throwables){
            throwables.printStackTrace();
            throwables.getCause();
        }
    }

}

