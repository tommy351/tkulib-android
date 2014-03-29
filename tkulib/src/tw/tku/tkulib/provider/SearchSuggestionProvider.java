package tw.tku.tkulib.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "tw.tku.tkulib.provider.SearchSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
