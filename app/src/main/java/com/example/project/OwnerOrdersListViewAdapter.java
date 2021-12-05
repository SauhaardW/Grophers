package com.example.project;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OwnerOrdersListViewAdapter extends RecyclerView.Adapter<OwnerOrdersListViewAdapter.ViewHolder> implements CustomSpinner.OnSpinnerEventsListener{

    private CustomSpinner modalSpinner;

//    int p;
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
//        p = holder.getAdapterPosition();

        Date date = new Date(order.getTimestamp());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatted = formatter.format(date);

        double total = 0.0;
        for (CartItem item : order.getCart()) {
            total += item.getPrice()*item.getQuantity();
        }

        holder.orderTitle.setText(String.format("Order #%d", position));
        holder.orderSubtitle.setText(order.getCustomerName() + " • " + dateFormatted);
        holder.orderTotal.setText(String.format("$%.2f", total));

        CardView card = (CardView) holder.itemView.findViewById(R.id.cardOwnerOrders);
        card.setOnClickListener(view -> {
            BottomSheetDialog orderInfoModal = new BottomSheetDialog(context);
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View modalView = li.inflate(R.layout.order_info_modal, null);

            createSpinner(modalView);

            orderInfoModal.setContentView(modalView);

            TextView customerName = orderInfoModal.findViewById(R.id.orderPlacedByTextOrderInfoModal);
            TextView orderDate = orderInfoModal.findViewById(R.id.datePlacedTextOrderInfoModal);
            customerName.setText("Order placed by: " + order.getCustomerName());
            orderDate.setText("Date placed: " + dateFormatted);

            // Handle recyclerview in modal
            RecyclerView modalRecyclerView = orderInfoModal.findViewById(R.id.recyclerView);
            modalRecyclerView.setHasFixedSize(true);
            modalRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            ArrayList<CartItem> modalList = order.getCart();
            OwnerOrderProductListViewAdapter modalAdapter= new OwnerOrderProductListViewAdapter(context, modalList);
            modalRecyclerView.setAdapter(modalAdapter);
            modalAdapter.notifyDataSetChanged();

            orderInfoModal.show();
            orderInfoModal.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    spinnerChoiceWrite(modalSpinner.getSelectedItem().toString(), order);
                }//end on dismiss
            });
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

    //Change so this only happens when modal is closed
    public void spinnerChoiceWrite(String spinnerChoice, Order order){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child("owners").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    Owner owner = task.getResult().getValue(Owner.class);
                    Integer storeID = owner.getStoreId();
                    String timeStamp = String.valueOf(order.getTimestamp());
                    FirebaseDatabase.getInstance().getReference("stores").child(storeID.toString()).child("orders").child(timeStamp).child("status").setValue(spinnerChoice);
                } else {
                    Toast.makeText(context, "Error while getting user data", Toast.LENGTH_SHORT).show();
                }//end else
            }//end onComplete
        });
    }//end spinnerChoiceWrite


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