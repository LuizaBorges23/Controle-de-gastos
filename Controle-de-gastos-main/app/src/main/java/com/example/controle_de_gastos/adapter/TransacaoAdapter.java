package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.graphics.Color; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; 
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.Transacao;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder> {

    private final LayoutInflater mInflater;
    private List<Transacao> mTransacoes;
    private final Context mContext;

   
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", new Locale("pt", "BR"));

    public TransacaoAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public TransacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transacao_item, parent, false);
        return new TransacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransacaoViewHolder holder, int position) {
        if (mTransacoes != null) {
            Transacao current = mTransacoes.get(position);

            holder.categoria.setText(current.getCategoria());

            
            holder.data.setText(dateFormat.format(new Date(current.getData())));

            
            double valor = current.getValor();

            if ("Receita".equals(current.getTipo())) {
                holder.valor.setText(currencyFormat.format(valor));
                holder.valor.setTextColor(Color.parseColor("#4CAF50")); 

                
                holder.iconType.setImageResource(R.drawable.ic_trending_up);
            } else { 
                holder.valor.setText("-" + currencyFormat.format(valor));
                holder.valor.setTextColor(Color.parseColor("#F44336")); 

                
                holder.iconType.setImageResource(R.drawable.ic_trending_down);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mTransacoes != null)
            return mTransacoes.size();
        else return 0;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        mTransacoes = transacoes;
        notifyDataSetChanged();
    }

    static class TransacaoViewHolder extends RecyclerView.ViewHolder {
        private final TextView valor;
        private final TextView categoria;
        private final TextView data;
        private final ImageView iconType; // <-- NOVO

        private TransacaoViewHolder(View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.text_view_valor);
            categoria = itemView.findViewById(R.id.text_view_categoria);
            data = itemView.findViewById(R.id.text_view_data);
            iconType = itemView.findViewById(R.id.icon_transacao_type); // <-- NOVO
        }
    }
}
