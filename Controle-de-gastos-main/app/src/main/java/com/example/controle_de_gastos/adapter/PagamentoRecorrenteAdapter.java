package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.PagamentoRecorrente;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PagamentoRecorrenteAdapter extends RecyclerView.Adapter<PagamentoRecorrenteAdapter.PagamentoViewHolder> {

    private final LayoutInflater mInflater;
    private List<PagamentoRecorrente> mPagamentosRecorrentes;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public PagamentoRecorrenteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PagamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recurring_pagamento_item, parent, false);
        return new PagamentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagamentoViewHolder holder, int position) {
        if (mPagamentosRecorrentes != null) {
            PagamentoRecorrente current = mPagamentosRecorrentes.get(position);
            holder.pagamentoName.setText(current.getNome());
            holder.pagamentoCategory.setText(current.getCategoria() + " | Recorrente");

            
            holder.pagamentoAmount.setText("-" + currencyFormat.format(current.getValor()));

           
            holder.pagamentoDueDate.setText("Vence dia " + current.getDiaVencimento());
        }
    }

    @Override
    public int getItemCount() {
        if (mPagamentosRecorrentes != null)
            return mPagamentosRecorrentes.size();
        else return 0;
    }

    public void setPagamentosRecorrentes(List<PagamentoRecorrente> pagamentos) {
        mPagamentosRecorrentes = pagamentos;
        notifyDataSetChanged();
    }

    static class PagamentoViewHolder extends RecyclerView.ViewHolder {
        private final TextView pagamentoName;
        private final TextView pagamentoCategory;
        private final TextView pagamentoDueDate;
        private final TextView pagamentoAmount;

        private PagamentoViewHolder(View itemView) {
            super(itemView);
            pagamentoName = itemView.findViewById(R.id.text_pagamento_name);
            pagamentoCategory = itemView.findViewById(R.id.text_pagamento_category);
            pagamentoDueDate = itemView.findViewById(R.id.text_pagamento_due_date);
            pagamentoAmount = itemView.findViewById(R.id.text_pagamento_amount);
        }
    }
}
