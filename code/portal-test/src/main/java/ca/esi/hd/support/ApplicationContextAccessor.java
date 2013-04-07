package ca.esi.hd.support;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAccessor implements MessageSourceAware {
    private static MessageSourceAccessor messageSourceAccessor;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    public static MessageSourceAccessor getMessageSourceAccessor() {
        return messageSourceAccessor;
    }

}
