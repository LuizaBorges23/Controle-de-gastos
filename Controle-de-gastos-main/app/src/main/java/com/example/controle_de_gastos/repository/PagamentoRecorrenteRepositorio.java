package com.example.controle_de_gastos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.PagamentoRecorrenteDao;
import com.example.controle_de_gastos.model.PagamentoRecorrente;
import java.util.List;

public class PagamentoRecorrenteRepositorio {
    private PagamentoRecorrenteDao mPagamentoRecorrenteDao;
    private LiveData<List<PagamentoRecorrente>> mAllPagamentosRecorrentes;

    public PagamentoRecorrenteRepositorio(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mPagamentoRecorrenteDao = db.pagamentoRecorrenteDao();
        mAllPagamentosRecorrentes = mPagamentoRecorrenteDao.getAllPagamentosRecorrentes();
    }

    public LiveData<List<PagamentoRecorrente>> getAllPagamentosRecorrentes() {
        return mAllPagamentosRecorrentes;
    }

    public void insert(PagamentoRecorrente pagamento) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mPagamentoRecorrenteDao.insert(pagamento);
        });
    }
}