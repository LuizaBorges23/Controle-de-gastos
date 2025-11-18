package com.example.controle_de_gastos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controle_de_gastos.R;
// Imports dos adapters e viewmodels
import com.example.controle_de_gastos.adapter.BudgetCategoryAdapter;
import com.example.controle_de_gastos.adapter.RecurringPaymentAdapter;
// import com.example.controle_de_gastos.adapter.TransacaoAdapter; // (Não é mais usado aqui)
import com.example.controle_de_gastos.viewmodel.BudgetViewModel;
import com.example.controle_de_gastos.viewmodel.RecurringPaymentViewModel;
import com.example.controle_de_gastos.viewmodel.TransacaoViewmodel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Mantido para o Saldo Total
    private TransacaoViewmodel transacaoViewModel;

    // --- para Pagamentos Recorrentes ---
    private RecurringPaymentViewModel recurringPaymentViewModel;
    private RecurringPaymentAdapter recurringPaymentAdapter;
    private RecyclerView recurringPaymentsRecyclerView;

    // --- para Finanças (Cofrinhos) ---
    private BudgetViewModel budgetViewModel;
    private BudgetCategoryAdapter budgetAdapter;
    private RecyclerView budgetRecyclerView;

    // --- para Dashboard ---
    private TextView textTotalBalance;
    private TextView textViewHistoryLink; // <-- NOVO LINK

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Configuração dos Views do Dashboard ---
        textTotalBalance = findViewById(R.id.text_total_balance);
        textViewHistoryLink = findViewById(R.id.text_view_history_link); // <-- ENCONTRAR O LINK

        // --- Configuração do ViewModel de Transações (Mantido para Saldo) ---
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewmodel.class);
        setupTransacaoObservers(); // (Método helper modificado abaixo)


        // --- Configuração do RecyclerView de Pagamentos Recorrentes ---
        recurringPaymentsRecyclerView = findViewById(R.id.recycler_view_recurring_payments);
        recurringPaymentAdapter = new RecurringPaymentAdapter(this);
        recurringPaymentsRecyclerView.setAdapter(recurringPaymentAdapter);
        recurringPaymentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // --- Configuração do ViewModel de Pagamentos Recorrentes ---
        recurringPaymentViewModel = new ViewModelProvider(this).get(RecurringPaymentViewModel.class);
        recurringPaymentViewModel.getAllRecurringPayments().observe(this, payments -> {
            recurringPaymentAdapter.setRecurringPayments(payments);
        });


        // --- Configuração do RecyclerView de Finanças (Cofrinhos) ---
        budgetRecyclerView = findViewById(R.id.recycler_view_budgets);
        budgetAdapter = new BudgetCategoryAdapter(this);
        budgetRecyclerView.setAdapter(budgetAdapter);
        budgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // --- Configuração do ViewModel de Finanças ---
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetCategories().observe(this, categories -> {
            budgetAdapter.setBudgetCategories(categories);
        });


        // --- Listener do FloatingActionButton ---
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Chama o método para mostrar o diálogo de escolha
            showAddOptionsDialog();
        });

        // --- NOVO: Listener para o link de histórico ---
        textViewHistoryLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }


    // --- MÉTODO showAddOptionsDialog (Existente) ---
    private void showAddOptionsDialog() {
        // Define as opções que aparecerão no diálogo (COM A NOVA OPÇÃO)
        final CharSequence[] options = {"Nova Transação", "Novo Pagamento Fixo", "Nova Categoria (Cofrinho)", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar...");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Nova Transação")) {
                // Opção 0: Abrir a tela de adicionar transação
                Intent intent = new Intent(MainActivity.this, AddTransacaoActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Novo Pagamento Fixo")) {
                // Opção 1: Abrir a tela de adicionar pagamento fixo
                Intent intent = new Intent(MainActivity.this, AddRecurringPaymentActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Nova Categoria (Cofrinho)")) {
                // Opção 2: (NOVO) Abrir a tela de adicionar cofrinho
                Intent intent = new Intent(MainActivity.this, AddBudgetCategoryActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Cancelar")) {
                // Opção 3: Fechar o diálogo
                dialog.dismiss();
            }
        });
        builder.show();
    }


    // --- Método helper ---
    private void setupTransacaoObservers() {
        // Observador para o Saldo Total (Mantido)
        transacaoViewModel.getTotalBalance().observe(this, balance -> {
            textTotalBalance.setText(currencyFormat.format(balance != null ? balance : 0.0));
        });
    }
}