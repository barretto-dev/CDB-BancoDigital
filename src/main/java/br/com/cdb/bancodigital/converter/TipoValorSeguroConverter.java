package br.com.cdb.bancodigital.converter;

import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;
import br.com.cdb.bancodigital.service.exception.EnumInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoValorSeguroConverter implements AttributeConverter<TipoValorSeguro, String> {
    @Override
    public String convertToDatabaseColumn(TipoValorSeguro tipoValorSeguro) {
        if(tipoValorSeguro == null)
            return null;

        return tipoValorSeguro.toString();
    }

    @Override
    public TipoValorSeguro convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }

        return Stream.of(TipoValorSeguro.values())
                .filter(tvs -> tvs.toString().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new EnumInvalidoException("Tipo do valor do seguro informado é invalido"));
    }
}
