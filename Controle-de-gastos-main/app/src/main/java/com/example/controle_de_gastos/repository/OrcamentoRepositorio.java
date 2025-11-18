package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.OrcamentoCategoriaDao;
import com.example.controle_de_gastos.model.OrcamentoCategoria;

import java.util.List;

public class OrcamentoRepositorio {
    private OrcamentoCategoriaDao mOrcamentoCategoriaDao;
    private LiveData<List<OrcamentoCategoria>> mAllOrcamentoCategorias;

    public OrcamentoRepositorio(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mOrcamentoCategoriaDao = db.orcamentoCategoriaDao();
        mAllOrcamentoCategorias = mOrcamentoCategoriaDao.getAllOrcamentoCategorias();
    }

    public LiveData<List<OrcamentoCategoria>> getAllOrcamentoCategorias() {
        return mAllOrcamentoCategorias;
    }

    public void insert(OrcamentoCategoria categoria) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mOrcamentoCategoriaDao.insert(categoria);
        });
    }
}