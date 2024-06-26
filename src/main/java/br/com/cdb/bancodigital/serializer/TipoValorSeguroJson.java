package br.com.cdb.bancodigital.serializer;

import br.com.cdb.bancodigital.entity.enums.TipoCartao;
import br.com.cdb.bancodigital.entity.enums.TipoValorSeguro;
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
public class TipoValorSeguroJson {

    static class TipoValorSeguroDeserializador extends JsonDeserializer<TipoValorSeguro> {

        private List<String> enums = Arrays.stream(TipoValorSeguro.values()).map( tvs -> tvs.toString()).toList();


        @Override
        public TipoValorSeguro deserialize(JsonParser jsonParser, DeserializationContext context)
                throws IOException, JacksonException {

            String tipoText = jsonParser.getText();

            if (tipoText == null)
                return null;

            return Stream.of(TipoValorSeguro.values())
                    .filter(tvs -> tvs.toString().equals(tipoText))
                    .findFirst()
                    .orElseThrow(() -> new DeserializacaoException(
                            "Tipo do valor do seguro deve ter os seguintes valores: " + enums.toString())
                    );
        }

        static class TipoValorSeguroSerializador extends JsonSerializer<TipoValorSeguro> {
            @Override
            public void serialize(TipoValorSeguro tipoValorSeguro, JsonGenerator generator, SerializerProvider provider)
                    throws IOException {

                generator.writeString(tipoValorSeguro.toString());
            }
        }
    }
}
