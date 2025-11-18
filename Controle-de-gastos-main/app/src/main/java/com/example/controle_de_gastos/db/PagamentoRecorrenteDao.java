package com.example.controle_de_gastos.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.PagamentoRecorrente;

import java.util.List;

@Dao
public interface PagamentoRecorrenteDao {

    @Insert
    void insert(PagamentoRecorrente pagamento);

    @Query("SELECT * FROM recurring_pagamentos ORDER BY diaVencimento ASC")
    LiveData<List<PagamentoRecorrente>> getAllPagamentosRecorrentes();
}