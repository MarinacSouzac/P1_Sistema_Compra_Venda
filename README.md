# P1_Sistema_Compra_Venda
Projeto avaliativo para disciplina de Linguagem de Programação em Java Swing com integração a banco de dados
Ministrada: Professor Fabrício Tonetto Londero

# Observações
- O sistema valida o estoque antes de emitir notas de saída
- Clientes vinculados a notas fiscais não podem ser excluídos
- O mesmo vale para fornecedores e produtos

## Funcionalidades

- Cadastro de clientes (Pessoa Física e Jurídica)
- Cadastro de fornecedores
- Cadastro de produtos com controle de estoque
- Emissão de notas fiscais de entrada e saída
- Validação de estoque antes de saída
- Máscaras de CPF, CNPJ, telefone e CEP
- Interface gráfica com Swing

---

## Tecnologias utilizadas

- **Java SE**
- **Swing** (interface gráfica)
- **MySQL** (banco de dados relacional)
- **JDBC** (conexão com o banco)
- **MVC** (organização do código)

---

## Estrutura do projeto


- **Beans** → contém as classes de modelo (entidades do sistema)  
- **DAO** → realiza as operações no banco de dados  
- **Visualização** → contém as telas (interfaces gráficas)
- **Conexão** → realiza a conexão com banco de dados
- **Menu.java** → tela principal para navegação do sistema  

---

## Como executar

 Clone o repositório:
   ```bash
   git clone https://github.com/MarinacSouzac/P1_Sistema_Compra_Venda


   


