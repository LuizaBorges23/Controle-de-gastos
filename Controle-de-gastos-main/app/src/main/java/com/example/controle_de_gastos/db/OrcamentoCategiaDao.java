package com.example.controle_de_gastos.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.OrcamentoCategia;

import java.util.List;

@Dao
public interface OrcamentoCategiaDao {

    @Insert
    void insert(OrcamentoCategia categia);

    @Query("SELECT * FROM orcamento_categorias ORDER BY nome ASC")
    LiveData<List<OrcamentoCategia>> getAllOrcamentoCategorias();

    // --- NOVO MÉTODO ADICIONADO ---
    // Procura uma categoria pelo nome e subtrai um valor do "valorGuardado"
    @Query("UPDATE orcamento_categorias SET valorGuardado = valorGuardado - :valorDespesa WHERE nome = :nomeCategoria")
    void updateOrcamentoOnDespesa(String nomeCategoria, double valorDespesa);
}