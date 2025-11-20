package com.example.controle_de_gastos.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recurring_payments")
public class PagamentoRecorrente {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nome;
    private String categoria;
    private double valor;
    private int diaVencimento; 

    
    public PagamentoRecorrente(String nome, String categoria, double valor, int diaVencimento) {
        this.nome = nome;
        this.categoria = categoria;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }
}
