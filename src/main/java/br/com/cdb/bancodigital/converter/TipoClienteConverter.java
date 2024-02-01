package br.com.cdb.bancodigital.converter;

import br.com.cdb.bancodigital.entity.enums.TipoCliente;
import br.com.cdb.bancodigital.service.exception.EnumInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoClienteConverter implements AttributeConverter<TipoCliente, String> {
    @Override
    public String convertToDatabaseColumn(TipoCliente tipoCliente) {
        if (tipoCliente == null) {
            return null;
        }
        return tipoCliente.getCodigo();
    }

    @Override
    public TipoCliente convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }

        return Stream.of(TipoCliente.values())
                .filter(tc -> tc.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new EnumInvalidoException("Tipo de cliente informado é invalido"));
    }
}
