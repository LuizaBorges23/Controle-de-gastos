package com.example.controle_de_gastos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.controle_de_gastos.R;
import com.example.controle_de_gastos.model.Transaction;
import com.example.controle_de_gastos.viewmodel.TransactionViewmodel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddTransactionActivity extends AppCompatActivity{
    
    public static final String EXTRA_TIPO = "com.example.contrledegastos.EXTRA_TIPO";

    private TextInputEditText mEditTextValor;
    private TextInputEditText mEditTextCategoria;
    private RadioGroup mRadioGroupTipo;
    private RadioButton mRadioReceita;
    private RadioButton mRadioDespesa;
    private Button mButtonSave;

    private TransactionViewmodel mTransactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.transaction);
        

        mEditTextValor = findViewById(R.id.edit_text_valor);
        mEditTextCategoria = findViewById(R.id.edit_text_categoria);
        mRadioGroupTipo = findViewById(R.id.radio_group_tipo);
        mRadioReceita = findViewById(R.id.radio_receita);
        mRadioDespesa = findViewById(R.id.radio_despesa);
        mButtonSave = findViewById(R.id.button_save);

        mTransactionViewModel = new ViewModelProvider(this).get(TransactionViewmodel.class);

        
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        
        if (getIntent().hasExtra(EXTRA_TIPO)) {
            String tipo = getIntent().getStringExtra(EXTRA_TIPO);
            if ("Receita".equals(tipo)) {
                mRadioReceita.setChecked(true);
            } else if ("Despesa".equals(tipo)) {
                mRadioDespesa.setChecked(true);
            }
        }

        mButtonSave.setOnClickListener(v -> saveTransaction());
    }

    private void savarTransicao() {
        
        String valorStr = mEditTextValor.getText().toString().trim();
        String categoria = mEditTextCategoria.getText().toString().trim();
        int selectedTipoId = mRadioGroupTipo.getCheckedRadioButtonId();

        if (valorStr.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        try {
            
            valorStr = valorStr.replace("R$", "").replace(".", "").replace(",", ".").trim();
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipo = (selectedTipoId == R.id.radio_receita) ? "Receita" : "Despesa";
        long data = System.currentTimeMillis(); 

        Transaction transaction = new Transaction(valor, tipo, categoria, data);
        mTransactionViewModel.insert(transaction);

        Toast.makeText(this, "Transação salva!", Toast.LENGTH_SHORT).show();
        finish(); 
    }
}
