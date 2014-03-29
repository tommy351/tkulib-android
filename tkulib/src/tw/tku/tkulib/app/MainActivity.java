package tw.tku.tkulib.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.R;

public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener {
    @InjectView(R.id.search)
    SearchView searchView;

    private String lastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        searchView.setOnQueryTextListener(this);
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

        if (s == lastQuery) {
            return true;
        }

        lastQuery = s;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new SearchFragment();
        Bundle args = new Bundle();

        args.putString(SearchFragment.EXTRA_KEYWORD, s);

        fragment.setArguments(args);
        ft.replace(R.id.container, fragment, SearchFragment.TAG);
        ft.commit();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s == "") {
            // TODO handle empty string
        }

        return true;
    }
}
