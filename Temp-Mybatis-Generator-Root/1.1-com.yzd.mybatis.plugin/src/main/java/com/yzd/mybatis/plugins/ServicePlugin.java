package com.yzd.mybatis.plugins;


import com.yzd.mybatis.model.ServiceImplModelGenerator;
import com.yzd.mybatis.model.ServiceModelGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.ArrayList;
import java.util.List;

public class ServicePlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = new ArrayList();

        AbstractJavaGenerator serviceGenerator = new ServiceModelGenerator();
        serviceGenerator.setContext(this.context);
        serviceGenerator.setIntrospectedTable(introspectedTable);
        List<CompilationUnit> serviceCompilationUnits2 = serviceGenerator.getCompilationUnits();
        for (CompilationUnit compilationUnit : serviceCompilationUnits2) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(
                    compilationUnit,
                    this.context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    this.context.getProperty("javaFileEncoding"),
                    this.context.getJavaFormatter());
            list.add(gjf);
        }
        AbstractJavaGenerator serviceImplGenerator = new ServiceImplModelGenerator();
        serviceImplGenerator.setContext(this.context);
        serviceImplGenerator.setIntrospectedTable(introspectedTable);
        List<CompilationUnit> serviceImplCompilationUnits2 = serviceImplGenerator.getCompilationUnits();
        for (CompilationUnit compilationUnit : serviceImplCompilationUnits2) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(
                    compilationUnit,
                    this.context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    this.context.getProperty("javaFileEncoding"),
                    this.context.getJavaFormatter());
            list.add(gjf);
        }
        return list;
    }
}


