package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//Controller za prozor upozorenja
public class VecDodanoController {
//Elementi FXML datoteke -----------------------------------------------------------------------------------------------
    @FXML
    private Button uReduButton;
//----------------------------------------------------------------------------------------------------------------------

//Metode za kontrolu akcija tipki --------------------------------------------------------------------------------------
    public void uReduButtonOnAction(ActionEvent event){
        Stage stageURedu = (Stage) uReduButton.getScene().getWindow();
        stageURedu.close();
    }
//----------------------------------------------------------------------------------------------------------------------
}
