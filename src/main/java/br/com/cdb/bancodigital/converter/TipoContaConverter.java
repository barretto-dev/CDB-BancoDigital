package br.com.cdb.bancodigital.converter;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import br.com.cdb.bancodigital.service.exception.EnumInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoContaConverter implements AttributeConverter<TipoConta, String> {
    @Override
    public String convertToDatabaseColumn(TipoConta tipoConta) {
        if (tipoConta == null) {
            return null;
        }
        return tipoConta.toString();
    }

    @Override
    public TipoConta convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }

        return Stream.of(TipoConta.values())
                .filter(tc -> tc.toString().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new EnumInvalidoException("Tipo da conta informada é invalida"));
    }
}
