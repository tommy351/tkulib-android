package tw.tku.tkulib.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Book;
import tw.tku.tkulib.model.BookDao;
import tw.tku.tkulib.model.DaoMaster;
import tw.tku.tkulib.model.DaoSession;
import tw.tku.tkulib.util.DatabaseHelper;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class ClearHistoryDialog extends DialogFragment {
    public static final String TAG = "ClearHistoryDialog";

    private BookDao bookDao;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DatabaseHelper helper = DatabaseHelper.getInstance(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        bookDao = daoSession.getBookDao();

        builder.setTitle(R.string.clear_history_title)
                .setMessage(R.string.clear_history_msg)
                .setPositiveButton(R.string.ok, onSubmitClick)
                .setNegativeButton(R.string.cancel, null);

        return builder.create();
    }

    private DialogInterface.OnClickListener onSubmitClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            QueryBuilder<Book> qb = bookDao.queryBuilder();
            qb.where(BookDao.Properties.Starred.notEq(true));
            List<Book> list  = qb.list();

            for (Book book : list) {
                bookDao.deleteInTx(book);
            }
        }
    };
}
