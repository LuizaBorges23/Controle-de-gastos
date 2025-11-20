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

        
        MaterialToolbar toolbar = findViewById(R.id.toolbar_history);
        toolbar.setNavigationOnClickListener(v -> finish());

        
        RecyclerView recyclerView = findViewById(R.id.recycler_view_history);
        transacaoAdapter = new TransacaoAdapter(this);
        recyclerView.setAdapter(transacaoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewmodel.class);

        
        transacaoViewModel.getAllTransacoes().observe(this, transacoes -> {
            transacaoAdapter.setTransacoes(transacoes);
        });
    }
}
