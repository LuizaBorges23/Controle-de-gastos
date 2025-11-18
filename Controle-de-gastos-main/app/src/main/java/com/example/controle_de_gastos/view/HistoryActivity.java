package com.example.controle_de_gastos.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.adapter.TransacaoAdapter;
import com.example.controle_de_gastos.viewmodel.TransacaoViewmodel;
import com.google.android.material.appbar.MaterialToolbar;

public class HistoryActivity extends AppCompatActivity {

    private TransacaoViewmodel transacaoViewModel;
    private TransacaoAdapter transacaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Configurar a Toolbar e o botão de voltar
        MaterialToolbar toolbar = findViewById(R.id.toolbar_history);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Configurar o RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_history);
        transacaoAdapter = new TransacaoAdapter(this);
        recyclerView.setAdapter(transacaoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar o ViewModel
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewmodel.class);

        // Observar a lista de transações
        // (O mesmo ViewModel que a MainActivity usa para o saldo)
        transacaoViewModel.getAllTransacoes().observe(this, transacoes -> {
            transacaoAdapter.setTransacoes(transacoes);
        });
    }
}