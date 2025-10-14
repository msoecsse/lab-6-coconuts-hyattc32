package coconuts;

import javafx.scene.image.Image;

// Represents the object that shoots down coconuts but can be hit by coconuts. Killing the
//   crab ends the game
// This is a domain class; other than Image, do not introduce JavaFX or other GUI components here
public class Crab extends HittableIslandObject {
    private static final int WIDTH = 50; // assumption: height and width are the same
    private static final Image crabImage = new Image("file:images/crab-1.png");

    public Crab(OhCoconutsGameManager game, int skyHeight, int islandWidth) {
        super(game, islandWidth / 2, skyHeight, WIDTH, crabImage);
    }

    @Override
    public void step() {
        // do nothing
    }

    // Captures the crab crawling sideways
    public void crawl(int offset) {
        x += offset;
        if (x < 0){
            x = 0;
        }
        if(x >= containingGame.getWidth() - WIDTH){
            x = containingGame.getWidth() - WIDTH;
        }
        display();
    }

    // TODO
    /**
     * Create a way to find when the crab gets hit by a coconut.
     * Have it update its observers when it gets hit.
     * End the game when this happens.
     * Reset button needed for ^
     * Use HitEvent?
     */
}
