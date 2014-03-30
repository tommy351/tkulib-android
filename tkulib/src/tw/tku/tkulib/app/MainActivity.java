package tw.tku.tkulib.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tw.tku.tkulib.R;
import tw.tku.tkulib.widget.ObservableScrollView;

public class MainActivity extends FragmentActivity {
    public static final String EXTRA_KEYWORD = "keyword";

    @InjectView(R.id.search)
    EditText searchView;

    @InjectView(R.id.scroll_view)
    ObservableScrollView scrollView;

    @InjectView(R.id.overflow)
    ImageButton overflowBtn;

    @InjectView(R.id.cancel)
    ImageButton cancelBtn;

    private String lastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String str = textView.getText().toString();

                    if (str.length() > 0) {
                        search(str);
                    }

                    return true;
                }

                return false;
            }
        });
        showMainFragment();

        if (savedInstanceState != null) {
            String keyword = savedInstanceState.getString(EXTRA_KEYWORD, "");

            searchView.setText(keyword);
            search(keyword);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(EXTRA_KEYWORD, searchView.getText().toString());
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
        cancelBtn.setVisibility(View.VISIBLE);
        overflowBtn.setVisibility(View.GONE);

        // Hide soft keyboard: http://stackoverflow.com/a/18415233
        searchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

        if (keyword == lastQuery) return;

        lastQuery = keyword;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        args.putString(SearchFragment.EXTRA_KEYWORD, keyword);

        resetScrollView();
        fragment.setArguments(args);
        ft.replace(R.id.container, fragment, SearchFragment.TAG);
        ft.commit();
    }

    private void showMainFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new MainFragment();

        resetScrollView();
        ft.replace(R.id.container, fragment, MainFragment.TAG);
        ft.commit();
    }

    @OnClick(R.id.overflow)
    public void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, overflowBtn);

        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        popupMenu.show();
    }

    @OnClick(R.id.cancel)
    public void onCancelBtnClick() {
        searchView.setText("");
        cancelBtn.setVisibility(View.GONE);
        overflowBtn.setVisibility(View.VISIBLE);
        showMainFragment();
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
        Intent intent = new Intent(this, PrefActivity.class);

        startActivity(intent);
    }

    private void resetScrollView() {
        scrollView.setEnd(false);
        scrollView.setLoading(true);
        scrollView.setPage(0);
        scrollView.scrollTo(0, 0);
    }
}
