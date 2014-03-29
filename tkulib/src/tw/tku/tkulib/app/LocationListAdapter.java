package tw.tku.tkulib.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tw.tku.tkulib.R;
import tw.tku.tkulib.model.Location;

/**
 * Created by SkyArrow on 2014/3/29.
 */
public class LocationListAdapter extends BaseListAdapter<Location> {
    public LocationListAdapter(Context context, List<Location> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Location location = getItem(i);

        if (view == null) {
            view = getInflater().inflate(R.layout.location_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.location.setText(location.getLocation());
        holder.status.setText(location.getNumber() + " / " + location.getStatus());

        return view;
    }

    static final class ViewHolder {
        @InjectView(R.id.location)
        TextView location;

        @InjectView(R.id.status)
        TextView status;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
