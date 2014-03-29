package tw.tku.tkulib.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
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
import tw.tku.tkulib.R;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
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
            // TODO handle empty string
        }

        return true;
    }

    private void search(String keyword) {
        lastQuery = keyword;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        args.putString(SearchFragment.EXTRA_KEYWORD, keyword);

        fragment.setArguments(args);
        ft.replace(R.id.container, fragment, SearchFragment.TAG);
        ft.commit();
    }

    @Override
    public void onScrollToEnd(int page) {

    }

    @OnClick(R.id.overflow)
    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, overflowBtn);

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.show();
    }
}
