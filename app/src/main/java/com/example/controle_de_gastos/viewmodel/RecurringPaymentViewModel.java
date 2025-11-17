package com.example.controle_de_gastos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.controle_de_gastos.model.RecurringPayment;
import com.example.controle_de_gastos.repository.RecurringPaymentRepository;
import java.util.List;

public class RecurringPaymentViewModel extends AndroidViewModel {

    private RecurringPaymentRepository mRepository;
    private final LiveData<List<RecurringPayment>> mAllRecurringPayments;

    public RecurringPaymentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RecurringPaymentRepository(application);
        mAllRecurringPayments = mRepository.getAllRecurringPayments();
    }

    public LiveData<List<RecurringPayment>> getAllRecurringPayments() {
        return mAllRecurringPayments;
    }

    public void insert(RecurringPayment payment) {
        mRepository.insert(payment);
    }
}