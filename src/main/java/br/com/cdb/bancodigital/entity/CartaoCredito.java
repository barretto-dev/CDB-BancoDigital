package br.com.cdb.bancodigital.entity;

public class CartaoCredito extends Cartao{

    private double limiteMensal;

    public CartaoCredito(double limiteMensal) {
        this.limiteMensal = limiteMensal;
    }

    @Override
    public boolean realizarPagamento(Double valor) {
        return false;
    }
}
