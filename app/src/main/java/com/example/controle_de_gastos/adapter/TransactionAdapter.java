package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.graphics.Color; // <-- NOVO IMPORT
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // <-- NOVO IMPORT
import android.widget.TextView;

import androidx.annotation.NonNull;
// import androidx.core.content.ContextCompat; // (Vamos usar Color.parseColor)
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.Transaction;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final LayoutInflater mInflater;
    private List<Transaction> mTransactions;
    private final Context mContext;

    // Formatadores
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", new Locale("pt", "BR"));

    public TransactionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        if (mTransactions != null) {
            Transaction current = mTransactions.get(position);

            holder.categoria.setText(current.getCategoria());

            // Formatar data
            holder.data.setText(dateFormat.format(new Date(current.getData())));

            // Formatar Valor e Lógica de Ícone
            double valor = current.getValor();

            if ("Receita".equals(current.getTipo())) {
                holder.valor.setText(currencyFormat.format(valor));
                holder.valor.setTextColor(Color.parseColor("#4CAF50")); // Cor verde

                // NOVO: Setar ícone de Receita
                holder.iconType.setImageResource(R.drawable.ic_trending_up);
            } else { // Despesa
                holder.valor.setText("-" + currencyFormat.format(valor));
                holder.valor.setTextColor(Color.parseColor("#F44336")); // Cor vermelha

                // NOVO: Setar ícone de Despesa
                holder.iconType.setImageResource(R.drawable.ic_trending_down);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mTransactions != null)
            return mTransactions.size();
        else return 0;
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView valor;
        private final TextView categoria;
        private final TextView data;
        private final ImageView iconType; // <-- NOVO

        private TransactionViewHolder(View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.text_view_valor);
            categoria = itemView.findViewById(R.id.text_view_categoria);
            data = itemView.findViewById(R.id.text_view_data);
            iconType = itemView.findViewById(R.id.icon_transaction_type); // <-- NOVO
        }
    }
}