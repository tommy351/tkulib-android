package tw.tku.tkulib.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tw.tku.tkulib.model.Book;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public abstract class BookListAdapter extends BaseListAdapter<Book> {
    public BookListAdapter(Context context, List<Book> list) {
        super(context, list);
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
