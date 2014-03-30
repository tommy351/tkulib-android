package tw.tku.tkulib.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.dao.query.QueryBuilder;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Book;
import tw.tku.tkulib.model.BookDao;
import tw.tku.tkulib.model.DaoMaster;
import tw.tku.tkulib.model.DaoSession;
import tw.tku.tkulib.util.DatabaseHelper;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String TAG = "MainFragment";

    @InjectView(R.id.starred)
    View starView;

    @InjectView(R.id.starred_list)
    ListView starList;

    @InjectView(R.id.history)
    View historyView;

    @InjectView(R.id.history_list)
    ListView historyList;

    private BookDao bookDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);

        DatabaseHelper dbHelper = DatabaseHelper.getInstance(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bookDao = daoSession.getBookDao();

        starList.setOnItemClickListener(this);
        historyList.setOnItemClickListener(this);

        showStarredList();
        showHistoryList();

        return view;
    }

    private void showStarredList() {
        QueryBuilder<Book> qb = bookDao.queryBuilder();
        qb.where(BookDao.Properties.Starred.eq(true));
        List<Book> bookList = qb.list();

        if (bookList.size() == 0) {
            starView.setVisibility(View.GONE);
        } else {
            SearchListAdapter adapter = new SearchListAdapter(getActivity(), bookList);
            starList.setAdapter(adapter);
        }
    }

    private void showHistoryList() {
        QueryBuilder<Book> qb = bookDao.queryBuilder();
        qb.orderDesc(BookDao.Properties.LastRead);
        qb.limit(20);
        List<Book> bookList = qb.list();

        if (bookList.size() == 0) {
            historyView.setVisibility(View.GONE);
        } else {
            SearchListAdapter adapter = new SearchListAdapter(getActivity(), bookList);
            historyList.setAdapter(adapter);
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
}
