package com.example.controle_de_gastos.db;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.Transacao;

import java.util.List;
@Dao
public interface TransacaoDao {
    @Insert
    void insert(Transacao transacao);

    @Query("SELECT * FROM transacoes ORDER BY data DESC")
    LiveData<List<Transacao>> getAllTransacoes();

    // Query para calcular o saldo total (SEU CÓDIGO)
    @Query("SELECT SUM(CASE WHEN tipo = 'Receita' THEN valor ELSE -valor END) FROM transacoes")
    LiveData<Double> getTotalBalance();

    // --- NOVO ---
    // Query para somar apenas as Receitas (Ganhos)
    @Query("SELECT SUM(valor) FROM transacoes WHERE tipo = 'Receita'")
    LiveData<Double> getTotalIncome();

    // --- NOVO ---
    // Query para somar apenas as Despesas (Gastos)
    @Query("SELECT SUM(valor) FROM transacoes WHERE tipo = 'Despesa'")
    LiveData<Double> getTotalExpenses();
}