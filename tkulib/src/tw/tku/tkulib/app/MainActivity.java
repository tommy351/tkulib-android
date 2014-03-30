package tw.tku.tkulib.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.SearchView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import tw.tku.tkulib.R;
import tw.tku.tkulib.event.MainScrollEvent;
import tw.tku.tkulib.util.L;
import tw.tku.tkulib.widget.InfiniteScrollListener;
import tw.tku.tkulib.widget.ObservableScrollView;

public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener,
        ObservableScrollView.OnScrollToEndListener {

    public static final String EXTRA_KEYWORD = "keyword";

    @InjectView(R.id.search)
    SearchView searchView;

    @InjectView(R.id.scroll_view)
    ObservableScrollView scrollView;

    @InjectView(R.id.overflow)
    View overflowBtn;

    private String lastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        searchView.setOnQueryTextListener(this);
        showMainFragment();

        if (savedInstanceState != null) {
            String keyword = savedInstanceState.getString(EXTRA_KEYWORD, "");

            searchView.setQuery(keyword, true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(EXTRA_KEYWORD, searchView.getQuery().toString());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchView.clearFocus();

        if (s != lastQuery) {
            search(s);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s == "") {
            showMainFragment();
        }

        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showPopupMenu();
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void search(String keyword) {
        lastQuery = keyword;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        args.putString(SearchFragment.EXTRA_KEYWORD, keyword);

        scrollView.setEnd(false);
        scrollView.setLoading(true);
        scrollView.setPage(0);

        fragment.setArguments(args);
        ft.replace(R.id.container, fragment, SearchFragment.TAG);
        ft.commit();
    }

    private void showMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new MainFragment();

        scrollView.setEnd(true);
        ft.replace(R.id.container, fragment, MainFragment.TAG);
        ft.commit();
    }

    @Override
    public void onScrollToEnd(int page) {
        EventBus.getDefault().post(new MainScrollEvent(page));
    }

    @OnClick(R.id.overflow)
    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, overflowBtn);

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.show();
    }

    private PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_advanced_search:
                    openAdvancedSearch();
                    return true;

                case R.id.menu_settings:
                    openSettings();
                    return true;
            }

            return false;
        }
    };

    public ObservableScrollView getScrollView() {
        return scrollView;
    }

    private void openAdvancedSearch() {
        //
    }

    private void openSettings() {
        //
    }
}
