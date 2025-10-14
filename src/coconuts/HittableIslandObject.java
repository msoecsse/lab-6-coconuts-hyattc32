package coconuts;

import javafx.scene.image.Image;

import java.util.Collection;
import java.util.LinkedList;

// Represents island objects which can be hit
// This is a domain class; do not introduce JavaFX or other GUI components here
public abstract class HittableIslandObject extends IslandObject implements Subject {
    public HittableIslandObject(OhCoconutsGameManager game, int x, int y, int width, Image image) {
        super(game, x, y, width, image);
    }

    public boolean isHittable() {
        return true;
    }

    protected Collection<Observer> observers = new LinkedList<Observer>();

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public boolean hasObserver(Observer o) {
        return observers.contains(o);
    }
}
