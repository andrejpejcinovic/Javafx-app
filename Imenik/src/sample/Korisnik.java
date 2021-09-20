package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Klasa korisnik sa svim atributima korisnika i njihovim getterima i setterima
public class Korisnik {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty korisnickoIme = new SimpleStringProperty();
    private final StringProperty lozinka = new SimpleStringProperty();
    private final StringProperty uloga = new SimpleStringProperty();

    public int getID(){
        return id.get();
    }

    public void setID(int value){
        id.set(value);
    }

    public IntegerProperty idProperty(){
        return id;
    }
    //------------------------------------------------------------
    public String getKorisnickoIme(){
        return korisnickoIme.get();
    }

    public void setKorisnickoIme(String value){
        korisnickoIme.set(value);
    }

    public StringProperty korisnickoImeProperty(){
        return korisnickoIme;
    }
    //------------------------------------------------------------
    public String getLozinka(){
        return lozinka.get();
    }

    public void setLozinka(String value){
        lozinka.set(value);
    }

    public StringProperty lozinkaProperty(){
        return lozinka;
    }
    //------------------------------------------------------------
    public String getUloga(){
        return uloga.get();
    }

    public void setUloga(String value){
        uloga.set(value);
    }

    public StringProperty ulogaProperty(){
        return uloga;
    }
//------------------------------------------------------------

}
