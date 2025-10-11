package coconuts;

public interface Subject {
    public void attach(Observer o); // called by observer to subscribe to notifications

    public void detach(Observer o); // called by observer to un-subscribe

    public void notifyObservers();   // update all oObservers

    public boolean hasObserver(Observer o); // is given observer on the list of subjects?
}
