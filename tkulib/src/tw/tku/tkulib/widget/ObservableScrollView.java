package tw.tku.tkulib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ScrollView;

import tw.tku.tkulib.util.L;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class ObservableScrollView extends ScrollView {
    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int threshold = 2;
    private int page = 0;
    private boolean isEnd = false;
    private boolean isLoading = false;
    private OnScrollToEndListener onScrollToEndListener;
    private OnScrollStateChangedListener onScrollStateChangedListener;

    public static final int SCROLL_STATE_FLING = AbsListView.OnScrollListener.SCROLL_STATE_FLING;
    public static final int SCROLL_STATE_IDLE = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    public static final int SCROLL_STATE_TOUCH_SCROLL = AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

    public interface OnScrollToEndListener {
        void onScrollToEnd(int page);
    }

    public interface OnScrollStateChangedListener {
        void onScrollStateChanged(int state);
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setOnScrollToEndListener(OnScrollToEndListener onScrollToEndListener) {
        this.onScrollToEndListener = onScrollToEndListener;
    }

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener onScrollStateChangedListener) {
        this.onScrollStateChangedListener = onScrollStateChangedListener;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        //L.d("%d, %d, %d, %d", x, y, oldx, oldy);
    }
}
