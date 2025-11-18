package com.example.controle_de_gastos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.RecurringPayment;
import com.example.controle_de_gastos.viewmodel.RecurringPaymentViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddRecurringPaymentActivity extends AppCompatActivity {

    private TextInputEditText editTextNome;
    private TextInputEditText editTextCategoria;
    private TextInputEditText editTextValor;
    private TextInputEditText editTextDia;
    private Button buttonSave;
    private MaterialToolbar toolbar;

    private RecurringPaymentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recurring_payment);

        // Encontrar os componentes
        editTextNome = findViewById(R.id.edit_text_payment_nome);
        editTextCategoria = findViewById(R.id.edit_text_payment_categoria);
        editTextValor = findViewById(R.id.edit_text_payment_valor);
        editTextDia = findViewById(R.id.edit_text_payment_dia);
        buttonSave = findViewById(R.id.button_save_recurring_payment);
        toolbar = findViewById(R.id.toolbar_add_recurring);

        // Configurar o ViewModel
        viewModel = new ViewModelProvider(this).get(RecurringPaymentViewModel.class);

        // Configurar o botão de voltar da Toolbar
        toolbar.setNavigationOnClickListener(v -> finish());

        // Configurar o clique do botão Salvar
        buttonSave.setOnClickListener(v -> savePayment());
    }

    private void savePayment() {
        String nome = editTextNome.getText().toString().trim();
        String categoria = editTextCategoria.getText().toString().trim();
        String valorStr = editTextValor.getText().toString().trim();
        String diaStr = editTextDia.getText().toString().trim();

        // Validação simples
        if (nome.isEmpty() || categoria.isEmpty() || valorStr.isEmpty() || diaStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int dia = Integer.parseInt(diaStr);

            // Validação do dia
            if (dia < 1 || dia > 31) {
                Toast.makeText(this, "Dia do vencimento inválido (1-31)", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criar e salvar o objeto
            RecurringPayment payment = new RecurringPayment(nome, categoria, valor, dia);
            viewModel.insert(payment);

            Toast.makeText(this, "Pagamento Fixo salvo!", Toast.LENGTH_SHORT).show();
            finish(); // Fecha a tela e volta para a MainActivity

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor ou Dia inválido", Toast.LENGTH_SHORT).show();
        }
    }
}