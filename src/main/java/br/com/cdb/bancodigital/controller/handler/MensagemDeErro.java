package br.com.cdb.bancodigital.controller.handler;

public class MensagemDeErro {

    private Integer status;
    private String mensagem;
    private String caminho;

    public MensagemDeErro() {
    }

    public MensagemDeErro(Integer status, String mensagem, String caminho) {
        this.status = status;
        this.mensagem = mensagem;
        this.caminho = caminho;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
