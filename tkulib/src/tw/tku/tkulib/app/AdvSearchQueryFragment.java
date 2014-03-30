package tw.tku.tkulib.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import tw.tku.tkulib.R;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class AdvSearchQueryFragment extends Fragment {
    public static final String TAG = "AdvSearchQueryFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adv_search, container, false);
        ButterKnife.inject(this, view);

        return view;
    }
}
