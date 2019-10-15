package com.yzd.mybatis.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class VoModelGenerator
        extends AbstractJavaGenerator {
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        String domainObjectName = this.introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                this.introspectedTable.getBaseRecordType()
                        .replace(domainObjectName, "aide." + domainObjectName + "Vo"));
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        if (StringUtility.stringHasValue(domainObjectName)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
            topLevelClass.setSuperClass(fqjt);
            topLevelClass.addImportedType(fqjt);
        }
        List<CompilationUnit> answer = new ArrayList();
        answer.add(topLevelClass);

        return answer;
    }
}



