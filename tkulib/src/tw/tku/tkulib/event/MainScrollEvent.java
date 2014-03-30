package tw.tku.tkulib.event;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class MainScrollEvent {
    private int page;

    public MainScrollEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
