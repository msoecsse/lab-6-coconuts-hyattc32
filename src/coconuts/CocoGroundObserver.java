package coconuts;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CocoGroundObserver implements Observer {

    @Override
    public void update() {
        GameController.cocoHitGroundLabel.setText(String.valueOf(Integer.parseInt(GameController.cocoHitGroundLabel.getText()) + 1));
    }
}
