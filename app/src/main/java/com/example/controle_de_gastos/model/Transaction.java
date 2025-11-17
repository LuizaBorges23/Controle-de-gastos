package com.example.controle_de_gastos.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "transactions")
public class Transaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private double valor;
    private String tipo; // "Receita" ou "Despesa"
    private String categoria;
    private long data; // Salvar como timestamp para facilitar a ordenação

    // Construtor, getters e setters

    public Transaction(double valor, String tipo, String categoria, long data) {
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}