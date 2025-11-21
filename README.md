# 💰 Controle de Gastos

Um aplicativo Android nativo simples e eficiente para gerenciamento de despesas pessoais, focado na persistência de dados local.

![Badge em Desenvolvimento](https://img.shields.io/badge/STATUS-EM_DESENVOLVIMENTO-green)
![Badge Android](https://img.shields.io/badge/Android-Studio-3DDC84?style=flat&logo=android&logoColor=white)

## 📱 Sobre o Projeto

O **Controle de Gastos** é uma aplicação desenvolvida para ajudar usuários a registrarem suas despesas do dia a dia de forma rápida. O principal objetivo técnico deste projeto foi a implementação de um banco de dados local robusto utilizando a biblioteca **Room**, garantindo que os dados permaneçam salvos mesmo após o fechamento do app ou reinicialização do dispositivo.

### ✨ Funcionalidades

* **Cadastro de Gastos:** Interface intuitiva para adicionar novas despesas (Nome, Valor, Categoria).
* **Listagem:** Visualização de todos os gastos cadastrados.
* **Persistência de Dados:** Uso de banco de dados local para armazenamento seguro.
* **Gestão de Estado:** Tratamento do Ciclo de Vida das Activities para evitar perda de dados durante o uso.

---

## 🛠️ Tecnologias e Conceitos Aplicados

O projeto foi desenvolvido no **Android Studio**, aplicando conceitos fundamentais do desenvolvimento mobile moderno:

* **Activity & Intent:** Navegação entre telas e criação de interfaces de usuário.
* **Ciclo de Vida (Lifecycle):** Gerenciamento correto dos estados da Activity (`onCreate`, `onResume`, etc.) para otimização de recursos e prevenção de crashs.
* **Room Database:**
    * Uso da biblioteca de persistência do Android Jetpack.
    * Implementação de **Entities** (tabelas), **DAOs** (Data Access Objects) e **Database**.
    * Abstração do SQL puro para operações mais seguras e limpas.
* **RecyclerView:** Listagem eficiente de itens na tela.

---

## 📸 Screenshots

| Tela Inicial | Cadastro de Gasto |
|:---:|:---:|
| ![Home](https://web.whatsapp.com/07cd3301-98d7-41bc-aeb6-8fcac64b237f) | ![Cadastro](https://web.whatsapp.com/2cb4fef1-11b0-41a2-97f9-7e32059b14f9) |



---



## ✒️ Autores

**Maria Luiza Borges**
**Thais Ribeiro Menezes**
**Anderson Jose**

---
