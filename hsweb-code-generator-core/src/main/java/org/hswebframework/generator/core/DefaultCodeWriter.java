package org.hswebframework.generator.core;

import org.hswebframework.utils.file.FileUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

/**
 * @author zhouhao
 * @since 3.0
 */
@org.springframework.stereotype.Service
public class DefaultCodeWriter implements CodeWriter {

    @Value("${hsweb.generator.workspace:./}")
    private String workspace = "./";

    private void writeCode(String path, GeneratedCode code) {
        File file = new File(path);
        file.mkdir();
        String type = code.getType();
        if ("dir".equals(type)) {
            code.getChildren()
                    .forEach(childrenCode -> writeCode(path + "/" + code.getFile(), childrenCode));
        } else if ("file".equals(type)) {
            String template = code.getTemplate();
            try {
                String fileName = path + "/" + code.getFile();
                String replaceMod = code.getRepeat();
                File codeFile = new File(fileName);
                if (codeFile.exists() && replaceMod != null) {
                    switch (replaceMod) {
                        case "ignore":
                            return;
                        case "append":
                            String old = FileUtils.reader2String(fileName);
                            template = old + template;
                            break;
                        default:
                            break;
                    }
                }
                FileUtils.writeString2File(template, fileName, "utf-8");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String write(List<GeneratedCode> codes) {

        codes.forEach(code -> writeCode(workspace, code));

        return workspace;
    }
}
