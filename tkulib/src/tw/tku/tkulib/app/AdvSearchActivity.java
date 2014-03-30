package tw.tku.tkulib.app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import tw.tku.tkulib.R;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class AdvSearchActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new AdvSearchQueryFragment();

        ft.replace(R.id.container, fragment, AdvSearchQueryFragment.TAG);
        ft.commit();
    }
}
