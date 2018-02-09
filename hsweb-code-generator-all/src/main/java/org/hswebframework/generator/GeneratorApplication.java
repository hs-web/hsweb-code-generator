package org.hswebframework.generator;

import org.hswebframework.web.authorization.Authentication;
import org.hswebframework.web.authorization.AuthenticationHolder;
import org.hswebframework.web.authorization.AuthenticationSupplier;
import org.hswebframework.web.authorization.listener.event.AuthorizationBeforeEvent;
import org.hswebframework.web.authorization.listener.event.AuthorizingHandleBeforeEvent;
import org.hswebframework.web.authorization.simple.SimpleAuthentication;
import org.hswebframework.web.authorization.simple.SimpleUser;
import org.hswebframework.web.authorization.simple.builder.SimpleAuthenticationBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;

/**
 * @author zhouhao
 * @since 3.0
 */
@SpringBootApplication
public class GeneratorApplication implements ApplicationListener<AuthorizingHandleBeforeEvent> {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class);
        //启用用户,只使用admin
        SimpleAuthentication authentication = new SimpleAuthentication();
        authentication.setUser(SimpleUser.builder()
                .id("admin")
                .name("admin")
                .type("code-generator")
                .build());

        AuthenticationSupplier supplier = new AuthenticationSupplier() {
            @Override
            public Authentication get(String userId) {
                return authentication;
            }

            @Override
            public Authentication get() {
                return authentication;
            }
        };
        AuthenticationHolder.addSupplier(supplier);
    }

    @Override
    public void onApplicationEvent(AuthorizingHandleBeforeEvent event) {
        //所有请求都通过
        event.setAllow(true);
    }
}
