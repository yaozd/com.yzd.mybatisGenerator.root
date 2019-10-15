package com.yzd.mybatis.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class ServiceModelGenerator
        extends AbstractJavaGenerator {
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                this.introspectedTable.getBaseRecordType().replace("entity", "service") + "Service");
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String domainObjectName = this.introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append("com.kjj.core.service.BaseService");
        rootInterface.append("<");
        rootInterface.append(domainObjectName);
        rootInterface.append(",");
        if (this.introspectedTable.getPrimaryKeyColumns().size() > 0) {
            IntrospectedColumn primaryKeyColumn = (IntrospectedColumn) this.introspectedTable.getPrimaryKeyColumns().get(0);
            rootInterface.append(primaryKeyColumn.getFullyQualifiedJavaType().getShortName());
        } else {
            rootInterface.append("Integer");
        }
        rootInterface.append(">");
        if (StringUtility.stringHasValue(rootInterface.toString())) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface.toString());
            FullyQualifiedJavaType domainObjectNameType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
            interfaze.addImportedType(domainObjectNameType);
        }
        List<CompilationUnit> answer = new ArrayList();
        answer.add(interfaze);

        return answer;
    }
}

