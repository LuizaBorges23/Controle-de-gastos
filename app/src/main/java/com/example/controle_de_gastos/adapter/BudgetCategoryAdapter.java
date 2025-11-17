package com.example.controle_de_gastos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.BudgetCategory;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BudgetCategoryAdapter extends RecyclerView.Adapter<BudgetCategoryAdapter.BudgetViewHolder> {

    private final LayoutInflater mInflater;
    private List<BudgetCategory> mBudgetCategories; // Lista em cache
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public BudgetCategoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.budget_category_item, parent, false);
        return new BudgetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        if (mBudgetCategories != null) {
            BudgetCategory current = mBudgetCategories.get(position);
            holder.budgetName.setText(current.getNome());
            holder.budgetAmount.setText(currencyFormat.format(current.getValorGuardado()));
        } else {
            // Cobre o caso da lista ainda não estar pronta
            holder.budgetName.setText("Carregando...");
            holder.budgetAmount.setText("");
        }
    }

    @Override
    public int getItemCount() {
        if (mBudgetCategories != null)
            return mBudgetCategories.size();
        else return 0;
    }

    public void setBudgetCategories(List<BudgetCategory> categories) {
        mBudgetCategories = categories;
        notifyDataSetChanged();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        private final TextView budgetName;
        private final TextView budgetAmount;

        private BudgetViewHolder(View itemView) {
            super(itemView);
            budgetName = itemView.findViewById(R.id.text_budget_name);
            budgetAmount = itemView.findViewById(R.id.text_budget_amount);
        }
    }
}