package tw.tku.tkulib.event;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class MainScrollEvent {
    private int state;

    public MainScrollEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
