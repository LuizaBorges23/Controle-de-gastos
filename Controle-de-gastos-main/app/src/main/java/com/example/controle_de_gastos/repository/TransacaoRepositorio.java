package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.BudgetCategoryDao; // <-- IMPORT ADICIONADO
import com.example.controle_de_gastos.db.TransacaoDao;
import com.example.controle_de_gastos.model.Transacao;

import java.util.List;
public class TransacaoRepositorio {
    private TransacaoDao mTransacaoDao;
    private BudgetCategoryDao mBudgetCategoryDao; // <-- ADICIONADO
    private LiveData<List<Transacao>> mAllTransacoes;
    private LiveData<Double> mTotalBalance;
    private LiveData<Double> mTotalIncome;
    private LiveData<Double> mTotalExpenses;

    public TransacaoRepositorio(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mTransacaoDao = db.transacaoDao();
        mBudgetCategoryDao = db.budgetCategoryDao(); // <-- ADICIONADO

        mAllTransacoes = mTransacaoDao.getAllTransacoes();
        mTotalBalance = mTransacaoDao.getTotalBalance();
        mTotalIncome = mTransacaoDao.getTotalIncome();
        mTotalExpenses = mTransacaoDao.getTotalExpenses();
    }

    public LiveData<List<Transacao>> getAllTransacoes() {
        return mAllTransacoes;
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    public LiveData<Double> getTotalIncome() {
        return mTotalIncome;
    }

    public LiveData<Double> getTotalExpenses() {
        return mTotalExpenses;
    }

    // --- MÉTODO INSERT MODIFICADO ---
    public void insert(Transacao transacao) {
        AppDataBase.databaseWriteExecutor.execute(() -> {

            // 1. Insere a transação (como antes)
            mTransacaoDao.insert(transacao);

            // 2. Lógica para atualizar o "Cofrinho" (Finanças)
            // Se for uma "Despesa", atualiza o cofrinho correspondente
            if ("Despesa".equals(transacao.getTipo())) {
                // O valor da transação (ex: 50.00)
                double valorDespesa = transacao.getValor();
                // A categoria da transação (ex: "Lazer")
                String categoria = transacao.getCategoria();

                // Manda o DAO subtrair o valor do cofrinho "Lazer"
                mBudgetCategoryDao.updateBudgetOnDespesa(categoria, valorDespesa);
            }
            // (Nota: Se for "Receita", não fazemos nada com os cofrinhos por enquanto)
        });
    }
}