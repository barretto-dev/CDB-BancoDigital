package br.com.cdb.bancodigital.converter;

import br.com.cdb.bancodigital.entity.enums.TipoClienteEnum;
import br.com.cdb.bancodigital.service.exception.EnumInvalidoException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoClienteConverter implements AttributeConverter<TipoClienteEnum, String> {
    @Override
    public String convertToDatabaseColumn(TipoClienteEnum tipoClienteEnum) {
        if (tipoClienteEnum == null) {
            return null;
        }
        return tipoClienteEnum.toString();
    }

    @Override
    public TipoClienteEnum convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }

        return Stream.of(TipoClienteEnum.values())
                .filter(tc -> tc.toString().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new EnumInvalidoException("Tipo de cliente informado é invalido"));
    }
}
