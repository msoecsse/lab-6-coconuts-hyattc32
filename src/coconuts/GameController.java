package coconuts;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

// JavaFX Controller class for the game - generally, JavaFX elements (other than Image) should be here
public class GameController {

    /**
     * Time between calls to step() (ms)
     */
    private static final double MILLISECONDS_PER_STEP = 1000.0 / 30;
    private Timeline coconutTimeline;
    private boolean started = false;

    @FXML
    private Pane gamePane;
    @FXML
    private Pane theBeach;
    @FXML
    public VBox skyAndSand;
    private OhCoconutsGameManager theGame;
    @FXML
    public Label cocoOnGroundLabel;
    @FXML
    private Label cocoDestroyedLabel;
    @FXML
    private Label shotsLabel;
    @FXML
    private Label shotText;

    public static Label cocoLaserLabel;
    public static Label cocoHitGroundLabel;
    public static Label shotsLeft;
    public static Label shotsText;

    @FXML
    public void initialize() {
        cocoLaserLabel = cocoDestroyedLabel;
        cocoHitGroundLabel = cocoOnGroundLabel;
        shotsLeft = shotsLabel;
        shotsText = shotText;

        theGame = new OhCoconutsGameManager((int) (gamePane.getPrefHeight() - theBeach.getPrefHeight()),
                (int) (gamePane.getPrefWidth()), gamePane);

        gamePane.setFocusTraversable(true);

        coconutTimeline = new Timeline(new KeyFrame(Duration.millis(MILLISECONDS_PER_STEP), (e) -> {
            theGame.tryDropCoconut();
            if(!theGame.isCrabDead()) {
                theGame.advanceOneTick();
            }
            if (theGame.done())
                coconutTimeline.pause();
        }));
        coconutTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT && !theGame.isCrabDead() && coconutTimeline.getStatus() == Timeline.Status.RUNNING) {
            if(keyEvent.isShiftDown()){
                theGame.getCrab().crawl(30);
            } else {
                theGame.getCrab().crawl(10);
            }

        } else if (keyEvent.getCode() == KeyCode.LEFT && !theGame.isCrabDead() && coconutTimeline.getStatus() == Timeline.Status.RUNNING) {
            if(keyEvent.isShiftDown()){
                theGame.getCrab().crawl(-30);
            } else {
                theGame.getCrab().crawl(-10);
            }
        } else if (keyEvent.getCode() == KeyCode.SPACE && !theGame.isCrabDead()) {
            if (!started) {
                coconutTimeline.play();
                started = true;
            } else {
                coconutTimeline.pause();
                started = false;
            }
        } else if (keyEvent.getCode() == KeyCode.UP && !theGame.isCrabDead() && coconutTimeline.getStatus() == Timeline.Status.RUNNING) {
            theGame.makeLaser();
        }

        if(keyEvent.getCode() == KeyCode.R) {
            theGame.resetGame();
            theGame = null;
            coconutTimeline.pause();
            coconutTimeline = null;
            cocoHitGroundLabel.setText("0");
            cocoLaserLabel.setText("0");
            shotsLeft.setText("5");
            initialize();
        }

    }
}
