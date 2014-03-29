package tw.tku.tkulib.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.R;
import tw.tku.tkulib.api.DataLoader;
import tw.tku.tkulib.model.Book;
import tw.tku.tkulib.model.BookDao;
import tw.tku.tkulib.model.DaoMaster;
import tw.tku.tkulib.model.DaoSession;
import tw.tku.tkulib.util.ActionBarHelper;
import tw.tku.tkulib.util.DatabaseHelper;
import tw.tku.tkulib.util.HttpRequestHelper;
import tw.tku.tkulib.util.L;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class BookActivity extends FragmentActivity {
    public static final String EXTRA_BOOK = "book";
    public static final String EXTRA_TITLE = "title";

    @InjectView(R.id.loading)
    ProgressBar progressBar;

    @InjectView(R.id.cover)
    ImageView coverView;

    @InjectView(R.id.title)
    TextView titleView;

    @InjectView(R.id.isbn)
    TextView isbnView;

    @InjectView(R.id.author)
    TextView authorView;

    @InjectView(R.id.publisher)
    TextView publisherView;

    private BookDao bookDao;
    private DataLoader dataLoader;

    private long bookId;
    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.inject(this);

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bookDao = daoSession.getBookDao();
        dataLoader = DataLoader.getInstance(this);

        Intent intent = getIntent();
        bookId = intent.getLongExtra(EXTRA_BOOK, 0);
        bookTitle = intent.getStringExtra(EXTRA_TITLE);

        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(bookTitle);

        Book book = bookDao.load(bookId);

        if (book != null) {
            showBookInfo(book);
        } else {
            new GetBookInfoTask().execute(bookId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book, menu);

        MenuItem shareItem = menu.findItem(R.id.menu_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        shareActionProvider.setShareIntent(getShareIntent());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ActionBarHelper.upNavigation(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_TEXT, bookTitle + " " + Book.getUrl(bookId));

        return intent;
    }

    private void showBookInfo(Book book) {
        titleView.setText(book.getTitle());

        showTextView(titleView, book.getTitle());
        showTextView(isbnView, book.getIsbn());
        showTextView(authorView, book.getAuthor());
        showTextView(publisherView, book.getPublisher());

        if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
            Picasso.with(this).load(book.getThumbnail()).into(coverView);
        } else {
            coverView.setVisibility(View.GONE);
        }
    }

    private void showTextView(TextView textView, String str) {
        if (str != null && !str.isEmpty()) {
            textView.setText(str);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private class GetBookInfoTask extends AsyncTask<Long, Integer, Book> {
        @Override
        protected Book doInBackground(Long... longs) {
            String url = Book.getUrl(bookId);
            try {
                String html = HttpRequestHelper.getString(url);

                if (html == null) return null;

                Document doc = Jsoup.parse(html);
                Book book = new Book();
                Element cover = doc.getElementById("bibliographicImage");
                Elements fields = doc.select("#itemView tr");
                String isbn = cover.attr("data-isbn");

                book.setId(bookId);
                book.setTitle(doc.getElementsByClass("title").html());
                book.setAuthor(doc.getElementsByClass("author").html());
                book.setIsbn(isbn);

                for (Element row : fields) {
                    String label = row.child(0).html();

                    if (label.equals("出版項")) {
                        book.setPublisher(row.child(1).text());
                    }
                }

                if (isbn != null && !isbn.isEmpty()) {
                    book.setThumbnail(dataLoader.getThumbnail(isbn));
                }

                bookDao.insertInTx(book);

                return book;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Book book) {
            progressBar.setVisibility(View.GONE);

            if (book == null) {
                // TODO error handling
                return;
            }

            showBookInfo(book);
        }
    }
}