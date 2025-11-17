package com.example.controle_de_gastos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.model.Transaction;
import com.example.controle_de_gastos.repository.TransactionRepository;

import java.util.List;

public class TransactionViewmodel extends AndroidViewModel {

    private TransactionRepository mRepository;
    private final LiveData<List<Transaction>> mAllTransactions;
    private final LiveData<Double> mTotalBalance;
    private final LiveData<Double> mTotalIncome;    // --- NOVO ---
    private final LiveData<Double> mTotalExpenses;  // --- NOVO ---

    public TransactionViewmodel(@NonNull Application application) {
        super(application);
        mRepository = new TransactionRepository(application);
        mAllTransactions = mRepository.getAllTransactions();
        mTotalBalance = mRepository.getTotalBalance();
        mTotalIncome = mRepository.getTotalIncome();      // --- NOVO ---
        mTotalExpenses = mRepository.getTotalExpenses();  // --- NOVO ---
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return mAllTransactions;
    }

    public LiveData<Double> getTotalBalance() {
        return mTotalBalance;
    }

    // --- NOVO ---
    public LiveData<Double> getTotalIncome() {
        return mTotalIncome;
    }

    // --- NOVO ---
    public LiveData<Double> getTotalExpenses() {
        return mTotalExpenses;
    }

    public void insert(Transaction transaction) {
        mRepository.insert(transaction);
    }
}