package cc.epass.factory.session;

import java.util.HashMap;

import org.springframework.mock.web.MockHttpServletRequest;

public class GenerateRequest extends MockHttpServletRequest{
    HashMap<String, String> requestParam = new HashMap<String, String>();
}
