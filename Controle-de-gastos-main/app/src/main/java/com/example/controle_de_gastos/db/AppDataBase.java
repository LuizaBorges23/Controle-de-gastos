package com.example.controle_de_gastos.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// --- Imports dos Models ---
import com.example.controle_de_gastos.model.OrcamentoCategoria;
import com.example.controle_de_gastos.model.PagamentoRecorrente;
import com.example.controle_de_gastos.model.Transacao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Versão (deve ser 3, se seguiu todos os passos)
@Database(entities = {Transacao.class, OrcamentoCategoria.class, PagamentoRecorrente.class}, version = 3, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract TransacaoDao transacaoDao();
    public abstract OrcamentoCategoriaDao orcamentoCategoriaDao();
    public abstract PagamentoRecorrenteDao pagamentoRecorrenteDao();

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
        TransacaoDao transacaoDao = db.transacaoDao();

        // Apaga transações antigas (se houver, para o reset)
        // (Nota: clearAllTables() é chamado no reset, não precisamos disto aqui)

        // Adiciona um depósito inicial
        long dataInicial = System.currentTimeMillis(); // Data de hoje
        transacaoDao.insert(new Transacao(2500.00, "Receita", "Salário", dataInicial - (1000 * 60 * 60 * 24 * 5))); // 5 dias atrás

        // Adiciona as despesas de exemplo como se já tivessem sido pagas
        transacaoDao.insert(new Transacao(99.99, "Despesa", "Academia", dataInicial - (1000 * 60 * 60 * 24 * 2))); // 2 dias atrás
        transacaoDao.insert(new Transacao(29.99, "Despesa", "Netflix", dataInicial - (1000 * 60 * 60 * 24 * 1))); // ontem
        transacaoDao.insert(new Transacao(350.00, "Despesa", "Mercado", dataInicial - (1000 * 60 * 60 * 24 * 1))); // ontem

        // Saldo esperado: 2500 - 99.99 - 29.99 - 350.00 = 2020.02

        // --- 2. POPULAR COFRINHOS (FINANÇAS) ---
        OrcamentoCategoriaDao orcamentoDao = db.orcamentoCategoriaDao();
        orcamentoDao.insert(new OrcamentoCategoria("Lazer", 300.00));
        orcamentoDao.insert(new OrcamentoCategoria("Contas", 1500.00));
        orcamentoDao.insert(new OrcamentoCategoria("Mercado", 400.00));

        // --- 3. POPULAR PAGAMENTOS RECORRENTES (PRÓXIMOS PAGAMENTOS) ---
        PagamentoRecorrenteDao recorrenteDao = db.pagamentoRecorrenteDao();
        recorrenteDao.insert(new PagamentoRecorrente("Academia Smartfit", "Saúde", 99.99, 30));
        recorrenteDao.insert(new PagamentoRecorrente("Netflix", "Lazer", 29.99, 1));
        recorrenteDao.insert(new PagamentoRecorrente("Aluguel", "Contas", 1200.00, 5));
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