package com.example.controle_de_gastos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.model.OrcamentoCategoria;
import com.example.controle_de_gastos.repositorio.OrcamentoRepositorio;

import java.util.List;

public class OrcamentoViewModel extends AndroidViewModel {

    private OrcamentoRepositorio mRepositorio;
    private final LiveData<List<OrcamentoCategorio>> mAllOrcamentoCategorias;

    public OrcamentoViewModel(@NonNull Application application) {
        super(application);
        mRepositorio = new OrcamentoRepositorio(application);
        mAllOrcamentoCategorias = mRepositorio.getAllOrcamentoCategorias();
    }

    public LiveData<List<OrcamentoCategorio>> getAllOrcamentoCategorias() {
        return mAllOrcamentoCategorias;
    }

    public void insert(OrcamentoCategorio categorio) {
        mRepositorio.insert(categorio);
    }
}