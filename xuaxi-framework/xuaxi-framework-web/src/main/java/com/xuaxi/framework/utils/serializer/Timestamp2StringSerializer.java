package com.xuaxi.framework.utils.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer; 

@JacksonStdImpl
public final class Timestamp2StringSerializer
    extends StdScalarSerializer<Timestamp>
{
	private static final long serialVersionUID = 5154839562157077928L;
	 
	final static Timestamp2StringSerializer instance = new Timestamp2StringSerializer();

    public Timestamp2StringSerializer() { super(Timestamp.class); }
    
    @Override
    public void serialize(Timestamp value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
    {
        return createSchemaNode("string", true);
    }
}

