package com.example.controle_de_gastos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.model.Transacao;
import com.example.controle_de_gastos.repositorio.TransacaoRepositorio;

import java.util.List;

public class TransacaoViewmodel extends AndroidViewModel {

    private TransacaoRepositorio mRepositorio;
    private final LiveData<List<Transacao>> mAllTransacoes;
    private final LiveData<Double> mTotalBalance;
    private final LiveData<Double> mTotalIncome;    
    private final LiveData<Double> mTotalExpenses;  

    public TransacaoViewmodel(@NonNull Application application) {
        super(application);
        mRepositorio = new TransacaoRepositorio(application);
        mAllTransacoes = mRepositorio.getAllTransacoes();
        mTotalBalance = mRepositorio.getTotalBalance();
        mTotalIncome = mRepositorio.getTotalIncome(); 
        mTotalExpenses = mRepositorio.getTotalExpenses();
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

    public void insert(Transacao transacao) {
        mRepositorio.insert(transacao);
    }
}
