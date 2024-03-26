package br.com.cdb.bancodigital.serializer;

import br.com.cdb.bancodigital.service.exception.DeserializacaoException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@JsonComponent
public class LocalDateJson {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    static class LocalDateDeserializer extends JsonDeserializer<LocalDate>{
        @Override
        public LocalDate deserialize (JsonParser jsonParser, DeserializationContext context) throws
                IOException, JacksonException {

            String dataTexto = jsonParser.getText();
            LocalDate data = null;

            try {
                data = LocalDate.parse(dataTexto, LocalDateJson.formatter);
            } catch (DateTimeParseException e) {
                throw new DeserializacaoException("data deve está no formato dd/mm/yyyy");
            }

            return data;
        }
    }

    static class LocalDateSerializer extends JsonSerializer<LocalDate>{
        @Override
        public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(localDate.format(LocalDateJson.formatter));
        }
    }

}
