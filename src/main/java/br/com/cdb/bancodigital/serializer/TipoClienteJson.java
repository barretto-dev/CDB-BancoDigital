package br.com.cdb.bancodigital.serializer;

import br.com.cdb.bancodigital.entity.enums.TipoCliente;
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
public class TipoClienteJson {

    static class TipoClienteDeserializador extends JsonDeserializer<TipoCliente> {
        private List<String> enums = Arrays.stream(TipoCliente.values()).map(tc -> tc.getCodigo()).toList();
        @Override
        public TipoCliente deserialize(JsonParser jsonParser, DeserializationContext context)
                throws IOException, JacksonException {

            String tipoText = jsonParser.getText();

            if (tipoText == null)
                return null;

            return Stream.of(TipoCliente.values())
                    .filter(tc -> tc.getCodigo().equals(tipoText))
                    .findFirst()
                    .orElseThrow(() -> new DeserializacaoException(
                            "Tipo de cliente deve ter os seguintes valores: " + enums.toString())
                    );

        }
    }
    static class TipoClienteSerializador extends JsonSerializer<TipoCliente> {
        @Override
        public void serialize(TipoCliente tipoCliente, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(tipoCliente.getCodigo());
        }
    }
}
