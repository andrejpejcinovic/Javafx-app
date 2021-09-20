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

public class FavoritiAdministratorController implements Initializable {

//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    private Label label;
    @FXML
    public TableView<Osoba> tableFavoritiAdministrator;
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
//----------------------------------------------------------------------------------------------------------------------


    private Connection connection;
    private ObservableList<Osoba> list;
    private BazaPodataka bazaPodataka;

    //Podaci o odabranom kontaktu u tablici favorita
    public static int IDFavoriti;
    public static String imeFavoriti;
    public static String prezimeFavoriti;
    public static String brojTelFavoriti;

    LoginController podaciLogin = new LoginController();
    ImenikController podaciImenik = new ImenikController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        bazaPodataka = new BazaPodataka();
        popuniFavorite();
    }

    public void popuniFavorite() {
        try {
            //Instanciranje liste
            list = FXCollections.observableArrayList();

            //Postavljanje upita za bazu podataka
            String query = "SELECT ID, Ime, Prezime, BrojTelefona, IDKorisnika " +
                    "FROM favoriti";


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
                osoba.setIdKorisnika(set.getInt("IDKorisnika"));

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
                            final Button deleteButton = new Button("Obriši iz favorita");

                            deleteButton.setOnAction(event -> {
                                Osoba p = getTableView().getItems().get(getIndex());
                                IDFavoriti = p.getID();
                                imeFavoriti = p.getIme();
                                prezimeFavoriti = p.getPrezime();
                                brojTelFavoriti = p.getBrojTel();
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("obrisiFavoritAdministrator.fxml"));
                                    Parent root = (Parent) fxmlLoader.load();
                                    Stage stageObrisi = new Stage();
                                    stageObrisi.initModality(Modality.APPLICATION_MODAL);
                                    stageObrisi.setTitle("Brisanje favorita");
                                    stageObrisi.setScene(new Scene(root));
                                    stageObrisi.setResizable(false);
                                    stageObrisi.showAndWait();
                                    popuniFavorite();
                                }catch (Exception e){
                                    e.printStackTrace();
                                    e.getCause();
                                }
                            });
                            HBox pane = new HBox(deleteButton);
                            setGraphic(pane);

                            setText(null);
                        };
                    };
                };
                //Vraćamo kreiranu ćeliju
                return cell;
            };

            editCol.setCellFactory(cellFactory);

            tableFavoritiAdministrator.setItems(list);

        }catch (SQLException throwables){
            throwables.printStackTrace();
            throwables.getCause();
        }
    }
}