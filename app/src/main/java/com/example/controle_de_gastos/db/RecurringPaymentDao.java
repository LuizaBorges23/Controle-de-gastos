package com.example.controle_de_gastos.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.controle_de_gastos.model.RecurringPayment;

import java.util.List;

@Dao
public interface RecurringPaymentDao {

    @Insert
    void insert(RecurringPayment payment);

    @Query("SELECT * FROM recurring_payments ORDER BY diaVencimento ASC")
    LiveData<List<RecurringPayment>> getAllRecurringPayments();
}