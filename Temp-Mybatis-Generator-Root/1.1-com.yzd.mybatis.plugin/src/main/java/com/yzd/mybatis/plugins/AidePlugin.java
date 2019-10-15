package com.yzd.mybatis.plugins;

 import java.util.ArrayList;
 import java.util.List;

 import com.yzd.mybatis.model.QueryModelGenerator;
 import com.yzd.mybatis.model.VoModelGenerator;
 import org.mybatis.generator.api.GeneratedJavaFile;
 import org.mybatis.generator.api.IntrospectedTable;
 import org.mybatis.generator.api.PluginAdapter;
 import org.mybatis.generator.api.dom.java.CompilationUnit;
 import org.mybatis.generator.codegen.AbstractJavaGenerator;

 public class AidePlugin extends PluginAdapter
 {
   public boolean validate(List<String> warnings)
   {
     return true;
   }

   public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable)
   {
     List<GeneratedJavaFile> list = new ArrayList();

     AbstractJavaGenerator queryGenerator = new QueryModelGenerator();
     queryGenerator.setContext(this.context);
     queryGenerator.setIntrospectedTable(introspectedTable);
     List<CompilationUnit> queryCompilationUnits2 = queryGenerator.getCompilationUnits();
     for (CompilationUnit compilationUnit : queryCompilationUnits2)
     {
       GeneratedJavaFile gjf = new GeneratedJavaFile(
         compilationUnit,
         this.context.getJavaModelGeneratorConfiguration().getTargetProject(),
         this.context.getProperty("javaFileEncoding"),
         this.context.getJavaFormatter());
       list.add(gjf);
     }
     AbstractJavaGenerator voGenerator = new VoModelGenerator();
     voGenerator.setContext(this.context);
     voGenerator.setIntrospectedTable(introspectedTable);
       List<CompilationUnit>  voCompilationUnits2 = voGenerator.getCompilationUnits();
     for (CompilationUnit compilationUnit : voCompilationUnits2)
     {
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

