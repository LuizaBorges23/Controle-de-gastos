package com.example.controle_de_gastos.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// --- Imports dos Models ---
import com.example.controle_de_gastos.model.BudgetCategory;
import com.example.controle_de_gastos.model.RecurringPayment;
import com.example.controle_de_gastos.model.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Versão (deve ser 3, se seguiu todos os passos)
@Database(entities = {Transaction.class, BudgetCategory.class, RecurringPayment.class}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract TransactionDao transactionDao();
    public abstract BudgetCategoryDao budgetCategoryDao();
    public abstract RecurringPaymentDao recurringPaymentDao();

    private static volatile AppDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "app_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration() // Isto vai apagar o banco ao subir a versão
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Chama o método de popular
                populateDb(INSTANCE);
            });
        }
    };

    // --- NOVO MÉTODO (lógica de popular movida para aqui) ---
    private static void populateDb(@NonNull AppDataBase db) {

        // --- 1. POPULAR TRANSAÇÕES (PARA O SALDO) ---
        // (Isto é novo!)
        TransactionDao transactionDao = db.transactionDao();

        // Apaga transações antigas (se houver, para o reset)
        // (Nota: clearAllTables() é chamado no reset, não precisamos disto aqui)

        // Adiciona um depósito inicial
        long dataInicial = System.currentTimeMillis(); // Data de hoje
        transactionDao.insert(new Transaction(2500.00, "Receita", "Salário", dataInicial - (1000 * 60 * 60 * 24 * 5))); // 5 dias atrás

        // Adiciona as despesas de exemplo como se já tivessem sido pagas
        transactionDao.insert(new Transaction(99.99, "Despesa", "Academia", dataInicial - (1000 * 60 * 60 * 24 * 2))); // 2 dias atrás
        transactionDao.insert(new Transaction(29.99, "Despesa", "Netflix", dataInicial - (1000 * 60 * 60 * 24 * 1))); // ontem
        transactionDao.insert(new Transaction(350.00, "Despesa", "Mercado", dataInicial - (1000 * 60 * 60 * 24 * 1))); // ontem

        // Saldo esperado: 2500 - 99.99 - 29.99 - 350.00 = 2020.02

        // --- 2. POPULAR COFRINHOS (FINANÇAS) ---
        BudgetCategoryDao budgetDao = db.budgetCategoryDao();
        budgetDao.insert(new BudgetCategory("Lazer", 300.00));
        budgetDao.insert(new BudgetCategory("Contas", 1500.00));
        budgetDao.insert(new BudgetCategory("Mercado", 400.00));

        // --- 3. POPULAR PAGAMENTOS RECORRENTES (PRÓXIMOS PAGAMENTOS) ---
        RecurringPaymentDao recurringDao = db.recurringPaymentDao();
        recurringDao.insert(new RecurringPayment("Academia Smartfit", "Saúde", 99.99, 30));
        recurringDao.insert(new RecurringPayment("Netflix", "Lazer", 29.99, 1));
        recurringDao.insert(new RecurringPayment("Aluguel", "Contas", 1200.00, 5));
    }


    // Método público para resetar a base de dados
    public static void resetDatabase() {
        databaseWriteExecutor.execute(() -> {
            if (INSTANCE == null) return;

            // 1. Limpa todas as tabelas
            INSTANCE.clearAllTables();

            // 2. Chama o método de popular novamente
            populateDb(INSTANCE);
        });
    }
}