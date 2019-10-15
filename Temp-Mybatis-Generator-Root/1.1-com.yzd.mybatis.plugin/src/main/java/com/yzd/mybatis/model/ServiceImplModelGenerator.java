package com.yzd.mybatis.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class ServiceImplModelGenerator extends AbstractJavaGenerator {
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = this.context.getCommentGenerator();

        String domainObjectName = this.introspectedTable.getFullyQualifiedTable()
                .getDomainObjectName();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                this.introspectedTable.getBaseRecordType()
                        .replace("entity", "service")
                        .replace(domainObjectName, new StringBuilder("impl.").append(domainObjectName).toString()) +
                        "ServiceImpl");
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        StringBuilder rootInterface = new StringBuilder();
        rootInterface.append("com.kjj.core.service.BaseService");
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
            FullyQualifiedJavaType implType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType().replace("entity", "service") + "Service");
            FullyQualifiedJavaType daoClass = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType().replace("entity", "dao") + "Dao");

            topLevelClass.setSuperClass(superClass);
            topLevelClass.addSuperInterface(implType);
            topLevelClass.addImportedType(superClass);
            topLevelClass.addImportedType(domainObjectNameType);
            topLevelClass.addImportedType(implType);
            topLevelClass.addImportedType(daoClass);
        }
        topLevelClass.addAnnotation("@Service");
        topLevelClass.addImportedType("org.springframework.stereotype.Service");


        addDaoField(topLevelClass);


        addGetBaseDaoMethod(topLevelClass);

        List<CompilationUnit> answer = new ArrayList();
        answer.add(topLevelClass);

        return answer;
    }

    private void addGetBaseDaoMethod(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);

        method.setName("getBaseDao");
        method.addAnnotation("@Override");
        StringBuilder sb = new StringBuilder();
        sb.append("com.kjj.core.dao.BaseDao");
        sb.append("<");
        String domainObjectName = this.introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        sb.append(domainObjectName);
        sb.append(",");
        if (this.introspectedTable.getPrimaryKeyColumns().size() > 0) {
            IntrospectedColumn primaryKeyColumn = (IntrospectedColumn) this.introspectedTable.getPrimaryKeyColumns().get(0);
            sb.append(primaryKeyColumn.getFullyQualifiedJavaType().getShortName());
        } else {
            sb.append("Integer");
        }
        sb.append(">");
        FullyQualifiedJavaType baseDao = new FullyQualifiedJavaType(sb.toString());
        topLevelClass.addImportedType(baseDao);
        method.setReturnType(baseDao);
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        sb.setLength(0);
        sb.append("return ");
        sb.append(getClassName(domainObjectName));
        sb.append("Dao");
        sb.append(";");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void addDaoField(TopLevelClass topLevelClass) {
        Field field = new Field();
        field.addAnnotation("@Resource");
        topLevelClass.addImportedType("javax.annotation.Resource");
        field.setVisibility(JavaVisibility.PRIVATE);
        FullyQualifiedJavaType daoClass = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType().replace("entity", "dao") + "Dao");
        field.setType(daoClass);
        String domainObjectName = this.introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        field.setName(getClassName(domainObjectName) + "Dao");

        topLevelClass.addField(field);
    }

    private static String getClassName(String className) {
        String first = className.substring(0, 1).toLowerCase();
        return first + className.substring(1);
    }
}



