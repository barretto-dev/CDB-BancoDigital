package br.com.cdb.bancodigital.serializer;

import br.com.cdb.bancodigital.entity.enums.TipoConta;
import br.com.cdb.bancodigital.service.exception.DeserializacaoException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@JsonComponent
public class TipoContaJson {

    static class TipoContaDeserializador extends JsonDeserializer<TipoConta> {
        private List<String> enums = Arrays.stream(TipoConta.values()).map(tc -> tc.getCodigo()).toList();
        @Override
        public TipoConta deserialize(JsonParser jsonParser, DeserializationContext context)
                throws IOException, JacksonException {

            String tipoText = jsonParser.getText();

            if (tipoText == null)
                return null;

            return Stream.of(TipoConta.values())
                    .filter(tc -> tc.getCodigo().equals(tipoText))
                    .findFirst()
                    .orElseThrow(() -> new DeserializacaoException(
                            "Tipo da conta deve ter os seguintes valores: " + enums.toString())
                    );

        }
    }
    static class TipoContaSerializador extends JsonSerializer<TipoConta> {
        @Override
        public void serialize(TipoConta tipoConta, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(tipoConta.getCodigo());
        }
    }


}
