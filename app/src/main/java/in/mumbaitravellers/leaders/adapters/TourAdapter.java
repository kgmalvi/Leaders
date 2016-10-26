package in.mumbaitravellers.leaders.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.mumbaitravellers.leaders.R;
import in.mumbaitravellers.leaders.model.Tour;

/**
 * Created by Administrator on 26-10-2016.
 */

public class TourAdapter extends ArrayAdapter<Tour> {

    public TourAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public TourAdapter(Context context, int resource, List<Tour> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.tour_list, null);
        }

        Tour tour = getItem(position);

        if (tour != null) {
            TextView tvTour = (TextView) view.findViewById(R.id.tv_eventName);
            TextView tvStart = (TextView) view.findViewById(R.id.tv_startDate);
            TextView tvLeaders = (TextView) view.findViewById(R.id.tv_leaders);

            if (tvTour != null) {
                tvTour.setText(tour.getEventName());
            }

            if (tvStart != null) {
                tvStart.setText(tour.getEventStartDate());
            }

            if (tvLeaders != null) {
                tvLeaders.setText(tour.getLeaders());
            }

        }

        return view;
    }
}
