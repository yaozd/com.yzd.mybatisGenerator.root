package com.yzd.mybatis.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class InsertPlugin extends PluginAdapter
{
  public boolean validate(List<String> warnings)
  {
    return true;
  }

  public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable)
  {
    return false;
  }

  public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable)
  {
    addPrimaryKey(element, introspectedTable);
    return true;
  }

  private void addPrimaryKey(XmlElement element, IntrospectedTable introspectedTable)
  {
    if (introspectedTable.getPrimaryKeyColumns().size() > 0)
    {
      IntrospectedColumn primaryKeyColumn = (IntrospectedColumn)introspectedTable.getPrimaryKeyColumns().get(0);
      element.addAttribute(new Attribute("useGeneratedKeys", "true"));
      element.addAttribute(new Attribute("keyColumn", MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn)));
      element.addAttribute(new Attribute("keyProperty", primaryKeyColumn.getJavaProperty()));
    }
    element.getElements().removeAll(element.getElements());

    StringBuilder insertClause = new StringBuilder();
    StringBuilder valuesClause = new StringBuilder();

    insertClause.append("insert into ");
    insertClause.append(introspectedTable
      .getFullyQualifiedTableNameAtRuntime());
    insertClause.append(" (");

    valuesClause.append("values (");

    List<String> valuesClauses = new ArrayList();

    Iterator<IntrospectedColumn> iter = introspectedTable.getNonPrimaryKeyColumns()
      .iterator();
    while (iter.hasNext())
    {
      IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
      if (!introspectedColumn.isIdentity())
      {
        insertClause.append(
          MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
        valuesClause.append(
          MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
        if (iter.hasNext())
        {
          insertClause.append(", ");
          valuesClause.append(", ");
        }
        if (valuesClause.length() > 80)
        {
          element.addElement(new TextElement(insertClause.toString()));
          insertClause.setLength(0);
          OutputUtilities.xmlIndent(insertClause, 1);

          valuesClauses.add(valuesClause.toString());
          valuesClause.setLength(0);
          OutputUtilities.xmlIndent(valuesClause, 1);
        }
      }
    }
    insertClause.append(')');
    element.addElement(new TextElement(insertClause.toString()));

    valuesClause.append(')');
    valuesClauses.add(valuesClause.toString());
    for (String clause : valuesClauses) {
      element.addElement(new TextElement(clause));
    }
  }
}

