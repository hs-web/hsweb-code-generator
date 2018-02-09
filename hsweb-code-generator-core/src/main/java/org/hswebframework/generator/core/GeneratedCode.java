package org.hswebframework.generator.core;

import lombok.Data;

import java.util.List;

/**
 * @author zhouhao
 * @since 3.0
 */
@Data
public class GeneratedCode {
    private String file;

    private String type;

    private String repeat;

    private String template;

    private List<GeneratedCode> children;
}
