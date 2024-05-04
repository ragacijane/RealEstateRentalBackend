package com.project.RealEstateRental.controller;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public class ByteArrayResourceSerializer extends JsonSerializer<ByteArrayResource> {

    @Override
    public void serialize(ByteArrayResource byteArrayResource, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        byte[] bytes = byteArrayResource.getByteArray();
        jsonGenerator.writeBinary(bytes);
    }
}
