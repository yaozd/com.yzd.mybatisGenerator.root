package com.yzd.mybatis.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class DaoImplModelGenerator extends AbstractJavaGenerator {
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        String domainObjectName = this.introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                this.introspectedTable.getBaseRecordType()
                        .replace("entity", "dao")
                        .replace(domainObjectName, new StringBuilder("impl.").append(domainObjectName).toString()) +
                        "DaoImpl");
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append("com.kjj.core.dao.BaseDao");
        rootInterface.append("Impl<");
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
            FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(rootInterface.toString());
            FullyQualifiedJavaType domainObjectNameType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
            FullyQualifiedJavaType implType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType().replace("entity", "dao") + "Dao");

            topLevelClass.setSuperClass(superClass);
            topLevelClass.addSuperInterface(implType);
            topLevelClass.addImportedType(superClass);
            topLevelClass.addImportedType(domainObjectNameType);
            topLevelClass.addImportedType(implType);
        }
        topLevelClass.addAnnotation("@Repository");
        topLevelClass.addImportedType("org.springframework.stereotype.Repository");

        List<CompilationUnit> answer = new ArrayList();
        answer.add(topLevelClass);

        return answer;
    }
}
