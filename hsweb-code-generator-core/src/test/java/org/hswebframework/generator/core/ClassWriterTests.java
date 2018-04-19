package org.hswebframework.generator.core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class ClassWriterTests {
    public static void main(String[] args) {
        CompilationUnit old = JavaParser.parse("" +
                "package org.hswebframework.web.dictionary.simple;\n" +
                "\n" +
                "import org.hswebframework.web.dictionary.api.DictionaryService;\n" +
                "import org.hswebframework.web.dictionary.api.entity.DictionaryEntity;\n" +
                "import org.hswebframework.web.dictionary.simple.dao.DictionaryDao;\n" +
                "import org.hswebframework.web.id.IDGenerator;\n" +
                "import org.hswebframework.web.service.EnableCacheAllEvictGenericEntityService;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.cache.annotation.CacheConfig;\n" +
                "import org.springframework.stereotype.Service;\n " +
                "" +
                "@Getter" +
                "@Setter" +
                "\n" +
                "public class Test{" +
                "\n//姓名\n" +
                "private String name;" +
                "}");

        CompilationUnit newClazz = JavaParser.parse("public class Test{" +
                "\n@NotNull(value=\"1\")\n" +
                "\n/**测试*/\n" +
                "private String name2;" +
                "\n" +
                "/**测试" +
                "*/" +
                "\n" +
                "@NotNull\n" +
                "public void addName(){}" +
                "}");

        Map<String, FieldDeclaration> oldFields = old
                .findAll(FieldDeclaration.class)
                .stream()
                .collect(Collectors.toMap(declaration -> declaration.getVariable(0).getNameAsString(), Function.identity()));


        Map<String, MethodDeclaration> oldMethod = old
                .findAll(MethodDeclaration.class)
                .stream()
                .collect(Collectors.toMap(NodeWithSimpleName::getNameAsString, Function.identity()));

        newClazz.findAll(FieldDeclaration.class).forEach(declaration -> {
            String name = declaration.getVariable(0).getNameAsString();
            if (oldFields.get(name) == null) {
                VariableDeclarator declarator = declaration.getVariable(0);
                FieldDeclaration newField = old.getType(0)
                        .addField(declarator.getType(), declarator.getNameAsString(),
                                declaration.getModifiers().toArray(new Modifier[]{}));

                declaration.getJavadocComment().ifPresent(newField::setJavadocComment);
                for (Comment comment : declaration.getAllContainedComments()) {
                    newField.setComment(comment);
                }
                for (AnnotationExpr annotationExpr : declaration.getAnnotations()) {
                    newField.addAnnotation(annotationExpr.clone());
                }
            }
        });
        newClazz.findAll(MethodDeclaration.class).forEach(declaration -> {
            String name =declaration.getNameAsString();
            if (oldMethod.get(name) == null) {
                MethodDeclaration newMethod = old.getType(0)
                        .addMethod(name, declaration.getModifiers().toArray(new Modifier[]{}));

                declaration.getJavadocComment().ifPresent(newMethod::setJavadocComment);
                for (Comment comment : declaration.getAllContainedComments()) {
                    newMethod.setComment(comment);
                }
                for (AnnotationExpr annotationExpr : declaration.getAnnotations()) {
                    newMethod.addAnnotation(annotationExpr.clone());
                }
            }
        });


        System.out.println(old.toString());
    }
}
