package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.RecurringPayment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RecurringPaymentAdapter extends RecyclerView.Adapter<RecurringPaymentAdapter.PaymentViewHolder> {

    private final LayoutInflater mInflater;
    private List<RecurringPayment> mRecurringPayments;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public RecurringPaymentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recurring_payment_item, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        if (mRecurringPayments != null) {
            RecurringPayment current = mRecurringPayments.get(position);
            holder.paymentName.setText(current.getNome());
            holder.paymentCategory.setText(current.getCategoria() + " | Recorrente");

            // Formata o valor para exibir como negativo
            holder.paymentAmount.setText("-" + currencyFormat.format(current.getValor()));

            // Formata a data de vencimento
            holder.paymentDueDate.setText("Vence dia " + current.getDiaVencimento());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecurringPayments != null)
            return mRecurringPayments.size();
        else return 0;
    }

    public void setRecurringPayments(List<RecurringPayment> payments) {
        mRecurringPayments = payments;
        notifyDataSetChanged();
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        private final TextView paymentName;
        private final TextView paymentCategory;
        private final TextView paymentDueDate;
        private final TextView paymentAmount;

        private PaymentViewHolder(View itemView) {
            super(itemView);
            paymentName = itemView.findViewById(R.id.text_payment_name);
            paymentCategory = itemView.findViewById(R.id.text_payment_category);
            paymentDueDate = itemView.findViewById(R.id.text_payment_due_date);
            paymentAmount = itemView.findViewById(R.id.text_payment_amount);
        }
    }
}