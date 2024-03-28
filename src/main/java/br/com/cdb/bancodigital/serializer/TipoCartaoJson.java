package br.com.cdb.bancodigital.serializer;

import br.com.cdb.bancodigital.entity.enums.TipoCartao;
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
public class TipoCartaoJson {

    static class TipoCartaoDeserializador extends JsonDeserializer<TipoCartao> {
        private List<String> enums = Arrays.stream(TipoCartao.values()).map(tc -> tc.getCodigo()).toList();
        @Override
        public TipoCartao deserialize(JsonParser jsonParser, DeserializationContext context)
                throws IOException, JacksonException {

            String tipoText = jsonParser.getText();

            if (tipoText == null)
                return null;

            return Stream.of(TipoCartao.values())
                    .filter(tc -> tc.getCodigo().equals(tipoText))
                    .findFirst()
                    .orElseThrow(() -> new DeserializacaoException(
                            "Tipo do cartão deve ter os seguintes valores: " + enums.toString())
                    );

        }
    }

    static class TipoCartaoSerializador extends JsonSerializer<TipoCartao> {
        @Override
        public void serialize(TipoCartao tipoCartao, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(tipoCartao.getCodigo());
        }
    }
}
