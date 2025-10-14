package coconuts;

// https://stackoverflow.com/questions/42443148/how-to-correctly-separate-view-from-model-in-javafx

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

// This class manages the game, including tracking all island objects and detecting when they hit
public class OhCoconutsGameManager {
    private final Collection<IslandObject> allObjects = new LinkedList<>();
    private final Collection<HittableIslandObject> hittableIslandSubjects = new LinkedList<>();
    private final Collection<IslandObject> scheduledForRemoval = new LinkedList<>();
    private final int height, width;
    private final int DROP_INTERVAL = 10;
    private final int MAX_TIME = 100;
    private Pane gamePane;
    private Crab theCrab;
    private Beach theBeach;
    /* game play */
    private int coconutsInFlight = 0;
    private int gameTick = 0;

    private List<Coconut> coconuts;
    private List<LaserBeam> lasers;

    private MediaPlayer mediaPlayer;
    private Media media;

    public OhCoconutsGameManager(int height, int width, Pane gamePane) {
        this.height = height;
        this.width = width;
        this.gamePane = gamePane;

        coconuts = new LinkedList<>();
        lasers = new LinkedList<>();

        this.theCrab = new Crab(this, height, width);
        registerObject(theCrab);
        gamePane.getChildren().add(theCrab.getImageView());

        this.theBeach = new Beach(this, height, width);
        registerObject(theBeach);
        if (theBeach.getImageView() != null)
            System.out.println("Unexpected image view for beach");


        String bgm = "images/CrabMusic.mp3";
        media = new Media(new File(bgm).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        theCrab.attach(new CocoCarbObserver());
    }

    private void registerObject(IslandObject object) {
        allObjects.add(object);
        if (object.isHittable()) {
            HittableIslandObject asHittable = (HittableIslandObject) object;
            hittableIslandSubjects.add(asHittable);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void coconutDestroyed() {
        coconutsInFlight -= 1;
    }

    public void tryDropCoconut() {
        if (gameTick % DROP_INTERVAL == 0 && theCrab != null) {
            coconutsInFlight += 1;
            Coconut c = new Coconut(this, (int) (Math.random() * width));
            registerObject(c);
            coconuts.add(c);
            c.attach(new CocoGroundObserver());
            gamePane.getChildren().add(c.getImageView());
        }
        gameTick++;
    }

    public Crab getCrab() {
        return theCrab;
    }

    public void killCrab() {
        theCrab = null;
    }

    public void advanceOneTick() {
        for (IslandObject o : allObjects) {
            o.step();
            o.display();
        }
        // see if objects hit; the hit itself is something you will add
        // you can't change the lists while processing them, so collect
        //   items to be removed in the first pass and remove them later
        scheduledForRemoval.clear();

        for(Coconut c : coconuts) {
            if(theCrab != null && theCrab.coconutHit(c.getX(), c.getY())) {
                theCrab.notifyObservers();
                gamePane.getChildren().remove(theCrab.getImageView());
                allObjects.remove(theCrab);
                killCrab();
                scheduleForDeletion(c);
            }

            for(LaserBeam b : lasers) {
                if(theCrab != null && b.coconutHit(c.getX(), c.getY())) {
                    //TODO properly call observer
                    //move this stuff into update method
                    scheduleForDeletion(c);
                    scheduleForDeletion(b);
                }
                if(b.getY() < 0){
                    scheduleForDeletion(b);
                }
            }

            if (c.getY() >= theCrab.getY()) {
                scheduleForDeletion(c);
                c.notifyObservers();
            }
        }

        // actually remove the objects as needed
        for (IslandObject thisObj : scheduledForRemoval) {
            allObjects.remove(thisObj);
            coconuts.remove(thisObj);
            lasers.remove(thisObj);
            if (thisObj instanceof HittableIslandObject) {
                hittableIslandSubjects.remove((HittableIslandObject) thisObj);
            }
            gamePane.getChildren().remove(thisObj.getImageView());
            thisObj.removeImage();
        }
        scheduledForRemoval.clear();
    }

    public void scheduleForDeletion(IslandObject islandObject) {
        scheduledForRemoval.add(islandObject);
    }

    public boolean done() {
        return coconutsInFlight == 0 && gameTick >= MAX_TIME;
    }

    //makes a laser of both sides of a crab
    //and adds them to the list of every object -Jacob
    public void makeLaser(){
        LaserBeam laser1 = new LaserBeam(this, theCrab.getY(), theCrab.getX());
        gamePane.getChildren().add(laser1.getImageView());
        LaserBeam laser2 = new LaserBeam(this, theCrab.getY(), theCrab.getX() + theCrab.width);
        gamePane.getChildren().add(laser2.getImageView());

        registerObject(laser1);
        registerObject(laser2);
        lasers.add(laser1);
        lasers.add(laser2);

    }

    public void resetGame() {
        gamePane.getChildren().clear();
        allObjects.clear();
    }
}
