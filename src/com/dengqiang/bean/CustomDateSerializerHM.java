package com.dengqiang.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CustomDateSerializerHM extends JsonSerializer<Date> {
    @Override
    public void serialize(Date arg0, JsonGenerator arg1,
           SerializerProvider arg2) throws IOException,
            JsonProcessingException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里可以换成你想要的时间格式
            String formattedDate = formatter.format(arg0);
             arg1.writeString(formattedDate);
       }
}
 