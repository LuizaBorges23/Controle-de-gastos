package com.example.controle_de_gastos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.RecurringPaymentDao;
import com.example.controle_de_gastos.model.RecurringPayment;
import java.util.List;

public class RecurringPaymentRepository {
    private RecurringPaymentDao mRecurringPaymentDao;
    private LiveData<List<RecurringPayment>> mAllRecurringPayments;

    public RecurringPaymentRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mRecurringPaymentDao = db.recurringPaymentDao();
        mAllRecurringPayments = mRecurringPaymentDao.getAllRecurringPayments();
    }

    public LiveData<List<RecurringPayment>> getAllRecurringPayments() {
        return mAllRecurringPayments;
    }

    public void insert(RecurringPayment payment) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mRecurringPaymentDao.insert(payment);
        });
    }
}