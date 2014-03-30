package tw.tku.tkulib.app;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import tw.tku.tkulib.R;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class PrefFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        Preference clearHistoryPref = findPreferenceByResource(R.string.pref_clear_history);
        clearHistoryPref.setOnPreferenceClickListener(
                new OpenDialogPreference(new ClearHistoryDialog(), ClearHistoryDialog.TAG));

        try {
            Preference versionPref = findPreferenceByResource(R.string.pref_version);
            PackageManager pm = getActivity().getPackageManager();
            String versionCode = pm.getPackageInfo(getActivity().getPackageName(), 0).versionName;
            versionPref.setSummary(versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private class OpenDialogPreference implements Preference.OnPreferenceClickListener {
        private DialogFragment dialog;
        private String tag;

        public OpenDialogPreference(DialogFragment dialog, String tag) {
            this.dialog = dialog;
            this.tag = tag;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            dialog.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), tag);
            return true;
        }
    };

    private Preference findPreferenceByResource(int id) {
        return findPreference(getString(id));
    }
}
