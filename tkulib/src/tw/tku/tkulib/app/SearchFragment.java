package tw.tku.tkulib.app;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.Constant;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Book;
import tw.tku.tkulib.util.HttpRequestHelper;
import tw.tku.tkulib.util.L;
import tw.tku.tkulib.util.NetworkHelper;
import tw.tku.tkulib.widget.InfiniteScrollListener;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "SearchFragment";

    public static final String EXTRA_KEYWORD = "keyword";
    public static final String EXTRA_POSITION = "position";

    private static final Uri QUERY_URI = Uri.parse(Constant.QUERY_URL);

    @InjectView(R.id.list)
    ListView listView;

    private SearchListAdapter adapter;
    private List<Book> bookList;
    private GetBookListTask task;
    private int currentPage;

    private View footer;
    private ProgressBar footerProgressBar;
    private TextView footerError;
    private Button footerRetryBtn;

    private NetworkHelper network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, view);

        Bundle args = getArguments();
        String keyword = args.getString(EXTRA_KEYWORD);
        network = NetworkHelper.getInstance(getActivity());

        bookList = new ArrayList<Book>();
        adapter = new SearchListAdapter(getActivity(), bookList);
        task = new GetBookListTask(keyword);

        footer = inflater.inflate(R.layout.book_list_footer, null);
        footerProgressBar = (ProgressBar) footer.findViewById(R.id.loading);
        footerError = (TextView) footer.findViewById(R.id.error);
        footerRetryBtn = (Button) footer.findViewById(R.id.retry);
        footerRetryBtn.setOnClickListener(onRetryBtnClick);

        listView.setOnItemClickListener(this);
        listView.addFooterView(footer);
        listView.setFooterDividersEnabled(false);
        listView.setAdapter(adapter);

        getBookList(1);

        if (savedInstanceState != null) {
            listView.setSelection(savedInstanceState.getInt(EXTRA_POSITION));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(EXTRA_POSITION, listView.getSelectedItemPosition());
    }

    private String getQueryUrl(String keyword, int page) {
        Uri.Builder builder = QUERY_URI.buildUpon();

        builder.appendQueryParameter("term_1", keyword);
        builder.appendQueryParameter("pageNumber", Integer.toString(page));

        return builder.build().toString();
    }

    private void getBookList(int page) {
        if (network.isAvailable()) {
//            task = new GetBookListTask();
            task.execute(page);
        } else {
            currentPage = page;
            showError(R.string.error_network, true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Book book = (Book) adapterView.getAdapter().getItem(i);

        if (book == null) return;

        Intent intent = new Intent(getActivity(), BookActivity.class);

        intent.putExtra(BookActivity.EXTRA_BOOK, book.getId());
        startActivity(intent);
    }

    private void showError(int res, boolean retry) {
        footerError.setVisibility(View.VISIBLE);
        footerError.setText(res);

        if (retry) footerRetryBtn.setVisibility(View.VISIBLE);
    }

    private void setLoading(boolean loading) {
        footerProgressBar.setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    private class GetBookListTask extends AsyncTask<Integer, Integer, List<Book>> {
        private String keyword;

        private GetBookListTask(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected List<Book> doInBackground(Integer... integers) {
            int page = integers[0];
            String url = getQueryUrl(keyword, page);

            L.v("Get book list: %s", url);

            try {
                String html = HttpRequestHelper.getString(url);

                if (html == null) return null;

                List<Book> list = new ArrayList<Book>();
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("div.record");

                for (Element element : elements) {
                    Book book = new Book();

                    book.setId(Long.parseLong(element.getElementsByClass("unapi-id").attr("title").substring(6)));
                    book.setAuthor(element.getElementsByClass("author").html());
                    book.setTitle(element.getElementsByClass("title").html());

                    list.add(book);
                }

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            setLoading(true);
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            if (books == null) {
                showError(R.string.error_book_not_found, false);
            } else {
                bookList.addAll(books);
                adapter.notifyDataSetChanged();
            }

            setLoading(false);
        }
    }

    private View.OnClickListener onRetryBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            footerError.setVisibility(View.GONE);
            footerRetryBtn.setVisibility(View.GONE);
            getBookList(currentPage);
        }
    };
}
