package coconuts;

public interface Observer {
    public void update(); // called by Subject to let the Observer know that new data is available
}
