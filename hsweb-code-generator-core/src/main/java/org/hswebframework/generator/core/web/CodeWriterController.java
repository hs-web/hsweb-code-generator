package org.hswebframework.generator.core.web;

import org.hswebframework.generator.core.CodeWriter;
import org.hswebframework.generator.core.GeneratedCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouhao
 * @since 3.0
 */
@RestController
@RequestMapping("/code-generator")
public class CodeWriterController {

    @Autowired
    private CodeWriter codeWriter;

    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody List<GeneratedCode> codes) {
        return ResponseEntity.ok(codeWriter.write(codes));
    }
}
