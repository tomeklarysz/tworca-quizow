package Okno;

import com.example.tworcaquizow.DodanieNazwyQuizuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ZmianaOkna {

    public FXMLLoader załadujOkno(String path) {
        try {
            // Załaduj nowe okno
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root = fxmlLoader.load();

            // Utwórz nową scenę i okno
            Stage newStage = new Stage();
            newStage.setTitle("Twórca quizów");
            newStage.setScene(new Scene(root, 800,600));
            newStage.show();

            return fxmlLoader;

        } catch (IOException e) {
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
        return null;
    }
}
