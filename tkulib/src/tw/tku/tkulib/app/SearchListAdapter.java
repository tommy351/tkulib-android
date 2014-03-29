package tw.tku.tkulib.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Book;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class SearchListAdapter extends BookListAdapter {
    public SearchListAdapter(Context context, List<Book> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Book book = getItem(i);

        if (view == null) {
            view = getInflater().inflate(R.layout.search_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(book.getTitle());

        return view;
    }

    static final class ViewHolder {
        @InjectView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
