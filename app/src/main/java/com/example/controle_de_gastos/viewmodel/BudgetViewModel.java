package com.example.controle_de_gastos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.model.BudgetCategory;
import com.example.controle_de_gastos.repository.BudgetRepository;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

    private BudgetRepository mRepository;
    private final LiveData<List<BudgetCategory>> mAllBudgetCategories;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BudgetRepository(application);
        mAllBudgetCategories = mRepository.getAllBudgetCategories();
    }

    public LiveData<List<BudgetCategory>> getAllBudgetCategories() {
        return mAllBudgetCategories;
    }

    public void insert(BudgetCategory category) {
        mRepository.insert(category);
    }
}