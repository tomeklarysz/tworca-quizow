package Uzytkownik;

public class Uzytkownik {
    private boolean stanZalogowania;
    private String login;
    private String haslo;
    private Integer liczbaPunktow = 0;

    public Uzytkownik(String login, String haslo){
        this.login = login;
        this.haslo = haslo;
    }

    public void Zaloguj() {
        stanZalogowania = true;
    }

    public void Wyloguj() {
        stanZalogowania = false;
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

    public void ZwiekszLiczbePunktow() {
        liczbaPunktow++;
    }

    public void WyzerujLiczbePunktow() { liczbaPunktow = 0;}

    public Integer getLiczbaPunktow() {
        return liczbaPunktow;
    }
}
