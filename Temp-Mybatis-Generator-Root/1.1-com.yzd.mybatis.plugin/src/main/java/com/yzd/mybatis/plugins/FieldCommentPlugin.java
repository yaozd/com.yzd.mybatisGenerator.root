package com.yzd.mybatis.plugins;

import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.Plugin.ModelClassType;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class FieldCommentPlugin extends PluginAdapter
{
  public boolean validate(List<String> warnings)
  {
    return true;
  }

  public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType)
  {
    generateFiledComment(field, introspectedColumn);
    return true;
  }

  private void generateFiledComment(Field field, IntrospectedColumn introspectedColumn)
  {
    field.addJavaDocLine("/** " + introspectedColumn.getRemarks() + " */");
  }
}



