package coconuts;

public class CocoLaserObserver implements Observer {
    @Override
    public void update() {
        GameController.cocoLaserLabel.setText(
                "" + (1 + Integer.parseInt(GameController.cocoLaserLabel.getText())));
    }
}
