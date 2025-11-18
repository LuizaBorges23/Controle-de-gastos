package com.example.controle_de_gastos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.controle_de_gastos.model.PagamentoRecorrente;
import com.example.controle_de_gastos.repositorio.PagamentoRecorrenteRepositorio;
import java.util.List;

public class PagamentoRecorrenteViewModel extends AndroidViewModel {

    private PagamentoRecorrenteRepositorio mRepositorio;
    private final LiveData<List<PagamentoRecorrente>> mAllPagamentosRecorrentes;

    public PagamentoRecorrenteViewModel(@NonNull Application application) {
        super(application);
        mRepositorio = new PagamentoRecorrenteRepositorio(application);
        mAllPagamentosRecorrentes = mRepositorio.getAllPagamentosRecorrentes();
    }

    public LiveData<List<PagamentoRecorrente>> getAllPagamentosRecorrentes() {
        return mAllPagamentosRecorrentes;
    }

    public void insert(PagamentoRecorrente pagamento) {
        mRepositorio.insert(pagamento);
    }
}