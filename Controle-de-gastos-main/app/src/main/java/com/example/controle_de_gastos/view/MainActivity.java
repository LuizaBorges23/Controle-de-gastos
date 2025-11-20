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

import com.example.controle_de_gastos.adapter.BudgetCategoryAdapter;
import com.example.controle_de_gastos.adapter.RecurringPaymentAdapter;

import com.example.controle_de_gastos.viewmodel.BudgetViewModel;
import com.example.controle_de_gastos.viewmodel.RecurringPaymentViewModel;
import com.example.controle_de_gastos.viewmodel.TransacaoViewmodel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    
    private TransacaoViewmodel transacaoViewModel;

    
    private RecurringPaymentViewModel recurringPaymentViewModel;
    private RecurringPaymentAdapter recurringPaymentAdapter;
    private RecyclerView recurringPaymentsRecyclerView;

    
    private BudgetViewModel budgetViewModel;
    private BudgetCategoryAdapter budgetAdapter;
    private RecyclerView budgetRecyclerView;

    
    private TextView textTotalBalance;
    private TextView textViewHistoryLink; 

    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        textTotalBalance = findViewById(R.id.text_total_balance);
        textViewHistoryLink = findViewById(R.id.text_view_history_link); 

        
        transacaoViewModel = new ViewModelProvider(this).get(TransacaoViewmodel.class);
        setupTransacaoObservers(); 


        
        recurringPaymentsRecyclerView = findViewById(R.id.recycler_view_recurring_payments);
        recurringPaymentAdapter = new RecurringPaymentAdapter(this);
        recurringPaymentsRecyclerView.setAdapter(recurringPaymentAdapter);
        recurringPaymentsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        
        recurringPaymentViewModel = new ViewModelProvider(this).get(RecurringPaymentViewModel.class);
        recurringPaymentViewModel.getAllRecurringPayments().observe(this, payments -> {
            recurringPaymentAdapter.setRecurringPayments(payments);
        });


        
        budgetRecyclerView = findViewById(R.id.recycler_view_budgets);
        budgetAdapter = new BudgetCategoryAdapter(this);
        budgetRecyclerView.setAdapter(budgetAdapter);
        budgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetCategories().observe(this, categories -> {
            budgetAdapter.setBudgetCategories(categories);
        });


        
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            
            showAddOptionsDialog();
        });

        
        textViewHistoryLink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }


    
    private void showAddOptionsDialog() {
        
        final CharSequence[] options = {"Nova Transação", "Novo Pagamento Fixo", "Nova Categoria (Cofrinho)", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar...");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Nova Transação")) {
                
                Intent intent = new Intent(MainActivity.this, AddTransacaoActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Novo Pagamento Fixo")) {
                
                Intent intent = new Intent(MainActivity.this, AddRecurringPaymentActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Nova Categoria (Cofrinho)")) {
                
                Intent intent = new Intent(MainActivity.this, AddBudgetCategoryActivity.class);
                startActivity(intent);

            } else if (options[item].equals("Cancelar")) {
                
                dialog.dismiss();
            }
        });
        builder.show();
    }


    
    private void setupTransacaoObservers() {
        
        transacaoViewModel.getTotalBalance().observe(this, balance -> {
            textTotalBalance.setText(currencyFormat.format(balance != null ? balance : 0.0));
        });
    }
}
