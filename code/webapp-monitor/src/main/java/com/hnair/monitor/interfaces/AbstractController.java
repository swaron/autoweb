package com.hnair.monitor.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * The Class AbstractController.
 */
public abstract class AbstractController extends ApplicationObjectSupport {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.INDENT_OUTPUT, true);
    }
    /**
     * Gets the i18n message.
     * 
     * @param code the code
     * @return the message
     */
    public String getMessage(String code) {
        return super.getMessageSourceAccessor().getMessage(code);
    }

    protected List<String> getErrorMessages(final Errors errors) {
        List<String> errorMessage = new ArrayList<String>();
        List<ObjectError> list = errors.getAllErrors();
        for (ObjectError objectError : list) {
            errorMessage.add(getMessageSourceAccessor().getMessage(objectError));
        }
        return errorMessage;
    }

    public String objectToJson(Object object) {
        String jsonValue = null;
        try {
            jsonValue = objectMapper.writeValueAsString(object);
            jsonValue = StringUtils.replace(jsonValue, "/", "\\/");
//            StringUtils.replace(jsonValue, "script", "sxxipt");
        } catch (IOException ex) {
            jsonValue = null;
        }
        return jsonValue;
    }
    
    public Object jsonToObject(String jsonValue, Class<?> valueType) {
        Object object = null;
        try {
            object = objectMapper.readValue(jsonValue, valueType);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return object;
    }
    
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TaskModel model = new TaskModel();
        model.setLastResponse("网页快照 - 翻译此页");
        String output = objectMapper.writeValueAsString(model);
        System.out.println(output);
    }
}
