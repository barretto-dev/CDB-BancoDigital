package br.com.cdb.bancodigital.converter;

import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.service.exception.EnumInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoCartaoConverter implements AttributeConverter<TipoCartao, String> {
    @Override
    public String convertToDatabaseColumn(TipoCartao tipoCartao) {
        if (tipoCartao == null) {
            return null;
        }
        return tipoCartao.toString();
    }

    @Override
    public TipoCartao convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }

        return Stream.of(TipoCartao.values())
                .filter(tc -> tc.toString().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new EnumInvalidoException("Tipo de cartão informado é invalido"));
    }
}
