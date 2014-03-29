package tw.tku.tkulib.app;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    @InjectView(R.id.starred)
    View starView;

    @InjectView(R.id.starred_list)
    GridView starList;

    @InjectView(R.id.history)
    View historyView;

    @InjectView(R.id.history_list)
    GridView historyList;

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
            MainListAdapter adapter = new MainListAdapter(getActivity(), bookList);
            starList.setAdapter(adapter);
        }
    }

    private void showHistoryList() {
        QueryBuilder<Book> qb = bookDao.queryBuilder();
        qb.orderDesc(BookDao.Properties.LastRead);
        List<Book> bookList = qb.list();

        if (bookList.size() == 0) {
            historyView.setVisibility(View.GONE);
        } else {
            MainListAdapter adapter = new MainListAdapter(getActivity(), bookList);
            historyList.setAdapter(adapter);
        }
    }
}
