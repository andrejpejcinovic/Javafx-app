package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Klasa osoba sa svim atributima kontakata i njihovim getterima i setterima
public class Osoba {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty ime = new SimpleStringProperty();
    private final StringProperty prezime = new SimpleStringProperty();
    private final StringProperty brojTel = new SimpleStringProperty();
    private final StringProperty adresa = new SimpleStringProperty();
    private final IntegerProperty idKorisnika = new SimpleIntegerProperty();

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
    public String getIme(){
        return ime.get();
    }

    public void setIme(String value){
        ime.set(value);
    }

    public StringProperty imeProperty(){
        return ime;
    }
//------------------------------------------------------------
    public String getPrezime(){
        return prezime.get();
    }

    public void setPrezime(String value){
        prezime.set(value);
    }

    public StringProperty prezimeProperty(){
        return prezime;
    }
//------------------------------------------------------------
    public String getBrojTel(){
        return brojTel.get();
    }

    public void setBrojTel(String value){
        brojTel.set(value);
    }

    public StringProperty brojTelProperty(){
        return brojTel;
    }
//------------------------------------------------------------
    public String getAdresa(){
        return adresa.get();
    }

    public void setAdresa(String value){
        adresa.set(value);
    }

    public StringProperty adresaProperty(){
        return adresa;
    }
    //------------------------------------------------------------
    public int getIDKorisnika(){
    return idKorisnika.get();
}

    public void setIdKorisnika(int value){
        idKorisnika.set(value);
    }

    public IntegerProperty idKorisnikaProperty(){
        return idKorisnika;
    }
//------------------------------------------------------------

}
