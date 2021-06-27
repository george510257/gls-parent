package com.gls.security.core.mobile.token;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * @author george
 */
public class MobileModule extends SimpleModule {
    public MobileModule() {
        super(MobileModule.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(MobileAuthenticationToken.class, MobileAuthenticationTokenMixIn.class);
    }
}
