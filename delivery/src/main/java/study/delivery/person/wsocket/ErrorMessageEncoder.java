package study.delivery.person.wsocket;

import com.google.gson.Gson;
import study.delivery.util.GlobalExceptionHandler;
import study.delivery.util.GsonProducer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ErrorMessageEncoder implements Encoder.Text<GlobalExceptionHandler.ErrorMessage> {


    @Override
    public String encode(GlobalExceptionHandler.ErrorMessage object) throws EncodeException {
        Gson gson = GsonProducer.create();
        return gson.toJson(object);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
