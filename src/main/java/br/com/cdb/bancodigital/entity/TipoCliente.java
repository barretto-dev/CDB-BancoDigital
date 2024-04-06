package br.com.cdb.bancodigital.entity;

import br.com.cdb.bancodigital.entity.enums.TipoClienteEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tipo_cliente")
public class TipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome", nullable = false)
    private TipoClienteEnum nome;

    @OneToMany(mappedBy = "tipo")
    List<Cliente> cliente;

    @OneToOne(mappedBy = "tipoCliente")
    MensalidadeConta mensalidadeConta;

    @OneToOne(mappedBy = "tipoCliente")
    RendimentoConta rendimentoConta;

    public TipoCliente(){}

    public TipoCliente(TipoClienteEnum nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoClienteEnum getNome() {
        return nome;
    }

    public void setTipo(TipoClienteEnum nome) {
        this.nome = nome;
    }

    public List<Cliente> getCliente() {
        return cliente;
    }

    public void setCliente(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public MensalidadeConta getMensalitadeConta() {
        return mensalidadeConta;
    }

    public RendimentoConta getRendimentoConta() {
        return rendimentoConta;
    }
}
