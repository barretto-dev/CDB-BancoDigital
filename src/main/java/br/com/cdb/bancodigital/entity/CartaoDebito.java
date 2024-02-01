package br.com.cdb.bancodigital.entity;

public class CartaoDebito extends Cartao {

    private double limiteDiário;

    public CartaoDebito(double limiteDiário) {
        this.limiteDiário = limiteDiário;
    }

    @Override
    public boolean realizarPagamento(Double valor) {
        return false;
    }
}
