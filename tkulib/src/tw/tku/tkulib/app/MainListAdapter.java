package tw.tku.tkulib.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Book;

/**
 * Created by SkyArrow on 2014/3/30.
 */
public class MainListAdapter extends BookListAdapter {
    public MainListAdapter(Context context, List<Book> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Book book = getItem(i);

        if (view == null) {
            view = getInflater().inflate(R.layout.main_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.title.setText(book.getTitle());

        if (book.getThumbnail() != null && !book.getThumbnail().isEmpty()) {
            Picasso.with(getContext()).load(book.getThumbnail()).into(holder.cover);
        }

        return view;
    }

    static final class ViewHolder {
        @InjectView(R.id.cover)
        ImageView cover;

        @InjectView(R.id.title)
        TextView title;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
