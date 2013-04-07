package cc.epass.factory.core;

import org.springframework.stereotype.Service;

import cc.epass.factory.session.GenerateRequest;
import cc.epass.factory.session.RequestContext;

@Service
public class GeneratorRuntime {

    public RequestContext createContext(GenerateRequest request) {
        return new RequestContext();
    }
    
}
