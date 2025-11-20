package com.example.controle_de_gastos.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.controle_de_gastos.model.OrcamentoCategoria;
import com.example.controle_de_gastos.model.PagamentoRecorrente;
import com.example.controle_de_gastos.model.Transacao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
                            .fallbackToDestructiveMigration() 
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
                
                populateDb(INSTANCE);
            });
        }
    };

    
    private static void populateDb(@NonNull AppDataBase db) {

        
        
        TransacaoDao transacaoDao = db.transacaoDao();

        
        

        
        long dataInicial = System.currentTimeMillis();
        transacaoDao.insert(new Transacao(2500.00, "Receita", "Salário", dataInicial - (1000 * 60 * 60 * 24 * 5))); 

        
        transacaoDao.insert(new Transacao(99.99, "Despesa", "Academia", dataInicial - (1000 * 60 * 60 * 24 * 2))); 
        transacaoDao.insert(new Transacao(29.99, "Despesa", "Netflix", dataInicial - (1000 * 60 * 60 * 24 * 1))); 
        transacaoDao.insert(new Transacao(350.00, "Despesa", "Mercado", dataInicial - (1000 * 60 * 60 * 24 * 1)));

        

        
        OrcamentoCategoriaDao orcamentoDao = db.orcamentoCategoriaDao();
        orcamentoDao.insert(new OrcamentoCategoria("Lazer", 300.00));
        orcamentoDao.insert(new OrcamentoCategoria("Contas", 1500.00));
        orcamentoDao.insert(new OrcamentoCategoria("Mercado", 400.00));

        
        PagamentoRecorrenteDao recorrenteDao = db.pagamentoRecorrenteDao();
        recorrenteDao.insert(new PagamentoRecorrente("Academia Smartfit", "Saúde", 99.99, 30));
        recorrenteDao.insert(new PagamentoRecorrente("Netflix", "Lazer", 29.99, 1));
        recorrenteDao.insert(new PagamentoRecorrente("Aluguel", "Contas", 1200.00, 5));
    }


    
    public static void resetDatabase() {
        databaseWriteExecutor.execute(() -> {
            if (INSTANCE == null) return;

            
            INSTANCE.clearAllTables();

            
            populateDb(INSTANCE);
        });
    }
}
