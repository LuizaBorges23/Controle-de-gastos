package com.example.controle_de_gastos.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget_categories")
public class OrcamentoCategoria {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private double valorGuardado;

    
    public OrcamentoCategoria(String nome, double valorGuardado) {
        this.nome = nome;
        this.valorGuardado = valorGuardado;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorGuardado() {
        return valorGuardado;
    }

    public void setValorGuardado(double valorGuardado) {
        this.valorGuardado = valorGuardado;
    }
}
