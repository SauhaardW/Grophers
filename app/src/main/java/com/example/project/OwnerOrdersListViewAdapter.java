package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OwnerOrdersListViewAdapter extends RecyclerView.Adapter<OwnerOrdersListViewAdapter.ViewHolder> implements CustomSpinner.OnSpinnerEventsListener{

    private CustomSpinner modalSpinner;

    Context context;
    ArrayList<Order> list;

    public OwnerOrdersListViewAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_owner_orders, parent, false);
        return new OwnerOrdersListViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOrdersListViewAdapter.ViewHolder holder, int position) {
        Order order = list.get(position);

        Date date = new Date(order.getTimestamp());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = formatter.format(date);

        double total = 0.0;
        for (CartItem item : order.getCart()) {
            total += item.getPrice();
        }

        holder.orderTitle.setText(String.format("Order #%d", position));
        holder.orderSubtitle.setText(order.getCustomerName() + " • " + dateFormatted);
        holder.orderTotal.setText(String.format("$%.2f", total));

        //implement image setting

        CardView card = (CardView) holder.itemView.findViewById(R.id.cardOwnerOrders);
        card.setOnClickListener(view -> {
            BottomSheetDialog orderInfoModal = new BottomSheetDialog(context);
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View modalView = li.inflate(R.layout.order_info_modal, null);

            createSpinner(modalView);

            orderInfoModal.setContentView(modalView);
            orderInfoModal.show();
        });
    }

    public void createSpinner(View modalView){
        modalSpinner = (CustomSpinner) modalView.findViewById(R.id.spinnerChoicesOrderInfo);
        modalSpinner.setSpinnerEventsListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, context.getResources().getStringArray(R.array.orderInfoModalList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modalSpinner.setAdapter(adapter);
    }//end createSpinner

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        modalSpinner.setBackground(context.getResources().getDrawable(R.drawable.spinnerchoicesdropup));
    }//end onPopUpWindowOpened

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        modalSpinner.setBackground(context.getResources().getDrawable(R.drawable.spinnerchoicesdropdown));
    }//end onPopupWondowClosed


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderTitle, orderSubtitle, orderInfo, orderTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.orderTitle = itemView.findViewById(R.id.textViewOwnerOrdersOrderNumber);
            this.orderSubtitle = itemView.findViewById(R.id.textViewOwnerOrdersUsername);
            this.orderInfo = itemView.findViewById(R.id.textViewOwnerOrders_orderInfoButton);
            this.orderTotal = itemView.findViewById(R.id.textViewOwnerOrders_priceTotal);
        }
    }
}
