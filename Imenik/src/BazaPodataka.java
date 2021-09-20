package sample;

import java.sql.Connection;
import java.sql.DriverManager;

//Klasa za povezivanje na bazu podataka
public class BazaPodataka {
    public Connection bazaLink;

    public Connection getConnection(){
        //Podaci o bazi podataka:
        String bazaIme = "baza_javafx";
        String bazaKorisnik = "root";
        String bazaLozinka = "";
        String url = "jdbc:mysql://localhost/" + bazaIme;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            bazaLink = DriverManager.getConnection(url, bazaKorisnik, bazaLozinka);
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return bazaLink;
    }
}
