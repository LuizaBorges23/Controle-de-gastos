package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.OrcamentoCategiaDao; 
import com.example.controle_de_gastos.db.TransacaoDao;
import com.example.controle_de_gastos.model.Transacao;

import java.util.List;
public class TransacaoRepositorio {
    private TransacaoDao mTransacaoDao;
    private OrcamentoCategiaDao mOrcamentoCategiaDao; 
    private LiveData<List<Transacao>> mAllTransacoes;
    private LiveData<Double> mTotalBalance;
    private LiveData<Double> mTotalIncome;
    private LiveData<Double> mTotalExpenses;

    public TransacaoRepositorio(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mTransacaoDao = db.transacaoDao();
        mBudgetCategoryDao = db.budgetCategoryDao(); 

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

    
    public void insert(Transacao transacao) {
        AppDataBase.databaseWriteExecutor.execute(() -> {

            
            mTransacaoDao.insert(transacao);

            
            if ("Despesa".equals(transacao.getTipo())) {
                
                double valorDespesa = transacao.getValor();
                
                String categoria = transacao.getCategoria();

                
                mOrcamentoCategiaDao.updateBudgetOnDespesa(categoria, valorDespesa);
            }
            
        });
    }
}
