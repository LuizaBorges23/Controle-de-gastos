package com.example.controle_de_gastos.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.BudgetCategory;

import java.util.List;

@Dao
public interface BudgetCategoryDao {

    @Insert
    void insert(BudgetCategory category);

    @Query("SELECT * FROM budget_categories ORDER BY nome ASC")
    LiveData<List<BudgetCategory>> getAllBudgetCategories();

    // --- NOVO MÉTODO ADICIONADO ---
    // Procura uma categoria pelo nome e subtrai um valor do "valorGuardado"
    @Query("UPDATE budget_categories SET valorGuardado = valorGuardado - :valorDespesa WHERE nome = :nomeCategoria")
    void updateBudgetOnDespesa(String nomeCategoria, double valorDespesa);
}