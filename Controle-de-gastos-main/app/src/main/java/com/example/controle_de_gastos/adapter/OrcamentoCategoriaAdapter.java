package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.OrcamentoCategoria;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrcamentoCategoriaAdapter extends RecyclerView.Adapter<OrcamentoCategoriaAdapter.OrcamentoViewHolder> {

    private final LayoutInflater mInflater;
    private List<OrcamentoCategoria> mOrcamentoCategorias; 
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public OrcamentoCategoriaAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrcamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.orcamento_categoria_item, parent, false);
        return new OrcamentoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrcamentoViewHolder holder, int position) {
        if (mOrcamentoCategorias != null) {
            OrcamentoCategoria current = mOrcamentoCategorias.get(position);
            holder.orcamentoName.setText(current.getNome());
            holder.orcamentoAmount.setText(currencyFormat.format(current.getValorGuardado()));
        } else {
            // Cobre o caso da lista ainda não estar pronta
            holder.orcamentoName.setText("Carregando...");
            holder.orcamentoAmount.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (mOrcamentoCategorias != null)
            return mOrcamentoCategorias.size();
        else return 0;
    }

    public void setOrcamentoCategorias(List<OrcamentoCategoria> categorias) {
        mOrcamentoCategorias = categorias;
        notifyDataSetChanged();
    }

    static class OrcamentoViewHolder extends RecyclerView.ViewHolder {
        private final TextView orcamentoName;
        private final TextView orcamentoAmount;

        private OrcamentoViewHolder(View itemView) {
            super(itemView);
            orcamentoName = itemView.findViewById(R.id.text_orcamento_name);
            orcamentoAmount = itemView.findViewById(R.id.text_orcamento_amount);
        }
    }
}
