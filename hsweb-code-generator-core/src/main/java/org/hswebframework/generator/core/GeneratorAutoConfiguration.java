package org.hswebframework.generator.core;

import org.hswebframework.generator.core.web.CodeWriterController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouhao
 * @since 3.0
 */
@Configuration
public class GeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CodeWriter.class)
    public DefaultCodeWriter defaultCodeWriter() {
        return new DefaultCodeWriter();
    }

    @Bean
    @ConditionalOnMissingBean(CodeWriterController.class)
    public CodeWriterController codeWriterController() {
        return new CodeWriterController();
    }
}
