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
import in.mumbaitravellers.leaders.model.Expense;

/**
 * Created by Administrator on 26-10-2016.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ExpenseAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.expense_list, null);
        }

        Expense expense = getItem(position);

        if (expense != null) {
            TextView tvType = (TextView) view.findViewById(R.id.text_type);
            TextView tvAmount = (TextView) view.findViewById(R.id.text_amount);
            TextView tvDescription = (TextView) view.findViewById(R.id.text_description);

            if (tvType != null) {
                tvType.setText(expense.getType());
            }

            if (tvAmount != null) {
                tvAmount.setText((String.valueOf( expense.getAmount())));
            }

            if (tvDescription != null) {
                tvDescription.setText(expense.getDescription());
            }

        }

        return view;
    }
}
