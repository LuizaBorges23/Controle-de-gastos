package com.example.controle_de_gastos.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.controle_de_gastos.db.AppDataBase;
import com.example.controle_de_gastos.db.BudgetCategoryDao;
import com.example.controle_de_gastos.model.BudgetCategory;

import java.util.List;

public class BudgetRepository {
    private BudgetCategoryDao mBudgetCategoryDao;
    private LiveData<List<BudgetCategory>> mAllBudgetCategories;

    public BudgetRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);
        mBudgetCategoryDao = db.budgetCategoryDao();
        mAllBudgetCategories = mBudgetCategoryDao.getAllBudgetCategories();
    }

    public LiveData<List<BudgetCategory>> getAllBudgetCategories() {
        return mAllBudgetCategories;
    }

    public void insert(BudgetCategory category) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mBudgetCategoryDao.insert(category);
        });
    }
}