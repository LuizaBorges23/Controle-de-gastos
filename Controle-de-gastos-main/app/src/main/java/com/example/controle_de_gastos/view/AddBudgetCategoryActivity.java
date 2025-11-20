package com.example.controle_de_gastos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.BudgetCategory;
import com.example.controle_de_gastos.viewmodel.BudgetViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddBudgetCategoryActivity extends AppCompatActivity {

    private TextInputEditText editTextNome;
    private TextInputEditText editTextValor;
    private Button buttonSave;
    private MaterialToolbar toolbar;

    private BudgetViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_budget_category);

       
        editTextNome = findViewById(R.id.edit_text_budget_nome);
        editTextValor = findViewById(R.id.edit_text_budget_valor);
        buttonSave = findViewById(R.id.button_save_budget_category);
        toolbar = findViewById(R.id.toolbar_add_budget);

       
        viewModel = new ViewModelProvider(this).get(BudgetViewModel.class);

        
        toolbar.setNavigationOnClickListener(v -> finish());

        
        buttonSave.setOnClickListener(v -> saveCategory());
    }

    private void saveCategory() {
        String nome = editTextNome.getText().toString().trim();
        String valorStr = editTextValor.getText().toString().trim();

        
        if (nome.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = 0.0;
        if (!valorStr.isEmpty()) {
            try {
                valor = Double.parseDouble(valorStr.replace(",", "."));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        
        BudgetCategory category = new BudgetCategory(nome, valor);
        viewModel.insert(category);

        Toast.makeText(this, "Categoria salva!", Toast.LENGTH_SHORT).show();
        finish(); 
    }
}
