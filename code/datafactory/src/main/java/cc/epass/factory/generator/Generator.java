package cc.epass.factory.generator;

import org.springframework.beans.factory.annotation.Autowired;

import cc.epass.factory.session.RequestContext;

public class Generator {
    @Autowired
    protected RequestContext context;
}
