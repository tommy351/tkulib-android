package tw.tku.tkulib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
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

    private int threshold = 20;
    private int page = 0;
    private boolean isEnd = false;
    private boolean isLoading = false;
    private OnScrollToEndListener onScrollToEndListener;

    public interface OnScrollToEndListener {
        void onScrollToEnd(int page);
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

        View view = getChildAt(getChildCount() - 1);
        int diff = view.getBottom() - (getHeight() + getScrollY());

        if (onScrollToEndListener != null && !isEnd && !isLoading && diff > threshold) {
            onScrollToEndListener.onScrollToEnd(++page);
        }
    }
}
