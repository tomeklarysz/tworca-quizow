package Uzytkownik;

public class Uzytkownik {
    private boolean stanZalogowania;
    private String login;
    private String haslo;

    public Uzytkownik(String login, String haslo){
        this.login = login;
        this.haslo = haslo;
    }

    public void uzytkownikZalogowany() {
        stanZalogowania = true;
    }

    public void uzytkownikNieZalogowany() {
        stanZalogowania = true;
    }

    public boolean getStanZalogowania() {
        return stanZalogowania;
    }

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }
}
