package com.yzd.mybatis.plugins;


import com.yzd.mybatis.model.DaoImplModelGenerator;
import com.yzd.mybatis.model.DaoModelGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.ArrayList;
import java.util.List;

public class DaoPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> list = new ArrayList();

        AbstractJavaGenerator daoGenerator = new DaoModelGenerator();
        daoGenerator.setContext(this.context);
        daoGenerator.setIntrospectedTable(introspectedTable);
        List<CompilationUnit> daoCompilationUnits2 = daoGenerator.getCompilationUnits();
        for (CompilationUnit compilationUnit : daoCompilationUnits2) {
            GeneratedJavaFile gjf = new GeneratedJavaFile(
                    compilationUnit,
                    this.context.getJavaModelGeneratorConfiguration().getTargetProject(),
                    this.context.getProperty("javaFileEncoding"),
                    this.context.getJavaFormatter());
            list.add(gjf);
        }
        AbstractJavaGenerator daoImplGenerator = new DaoImplModelGenerator();
        daoImplGenerator.setContext(this.context);
        daoImplGenerator.setIntrospectedTable(introspectedTable);
        List<CompilationUnit>  daoImplCompilationUnits2 = daoImplGenerator.getCompilationUnits();
        for (CompilationUnit compilationUnit :  daoImplCompilationUnits2) {
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

