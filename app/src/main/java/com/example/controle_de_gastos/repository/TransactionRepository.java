package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.BudgetCategoryDao; // <-- IMPORT ADICIONADO
import com.example.controle_de_gastos.db.TransactionDao;
import com.example.controle_de_gastos.model.Transaction;

import java.util.List;
public class TransactionRepository {
    private TransactionDao mTransactionDao;
    private BudgetCategoryDao mBudgetCategoryDao; // <-- ADICIONADO
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<Double> mTotalBalance;
    private LiveData<Double> mTotalIncome;
    private LiveData<Double> mTotalExpenses;

    public TransactionRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mTransactionDao = db.transactionDao();
        mBudgetCategoryDao = db.budgetCategoryDao(); // <-- ADICIONADO

        mAllTransactions = mTransactionDao.getAllTransactions();
        mTotalBalance = mTransactionDao.getTotalBalance();
        mTotalIncome = mTransactionDao.getTotalIncome();
        mTotalExpenses = mTransactionDao.getTotalExpenses();
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
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
    public void insert(Transaction transaction) {
        AppDataBase.databaseWriteExecutor.execute(() -> {

            // 1. Insere a transação (como antes)
            mTransactionDao.insert(transaction);

            // 2. Lógica para atualizar o "Cofrinho" (Finanças)
            // Se for uma "Despesa", atualiza o cofrinho correspondente
            if ("Despesa".equals(transaction.getTipo())) {
                // O valor da transação (ex: 50.00)
                double valorDespesa = transaction.getValor();
                // A categoria da transação (ex: "Lazer")
                String categoria = transaction.getCategoria();

                // Manda o DAO subtrair o valor do cofrinho "Lazer"
                mBudgetCategoryDao.updateBudgetOnDespesa(categoria, valorDespesa);
            }
            // (Nota: Se for "Receita", não fazemos nada com os cofrinhos por enquanto)
        });
    }
}