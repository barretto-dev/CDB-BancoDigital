package br.com.cdb.bancodigital.entity;

public abstract class Cartao {

    private String senha;
    private boolean ativo;

    private Conta conta;

    /*public boolean aplicarTaxaDeUso(Double valorCompra, Double taxa){
        double valorTaxa = valorCompra * taxa;
        double saldoFinal = conta.getSaldo() - valorTaxa;
        if(saldoFinal >= 0) {
            conta.setSaldo(saldoFinal);
            return true;
        }
        return false;
    }*/

    public abstract boolean realizarPagamento(Double valor);
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}
