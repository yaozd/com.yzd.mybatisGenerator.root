package com.yzd.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.Iterator;
import java.util.List;

public class SelectPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement voByIdElements = getVoByIdElements(introspectedTable);
        this.context.getCommentGenerator().addComment(voByIdElements);
        document.getRootElement().addElement(voByIdElements);


        XmlElement queryWhereElements = getQueryWhereElements(introspectedTable);
        this.context.getCommentGenerator().addComment(queryWhereElements);
        document.getRootElement().addElement(queryWhereElements);


        XmlElement countElements = getCountElements(introspectedTable);
        this.context.getCommentGenerator().addComment(countElements);
        document.getRootElement().addElement(countElements);


        XmlElement selectElements = getSelectElements(introspectedTable);
        this.context.getCommentGenerator().addComment(selectElements);
        document.getRootElement().addElement(selectElements);


        XmlElement deleteElements = getDeleteElements(introspectedTable);
        this.context.getCommentGenerator().addComment(deleteElements);
        document.getRootElement().addElement(deleteElements);
        this.context.getCommentGenerator().addComment(deleteElements);

        document.getRootElement().addElement(new TextElement("<!--  ************************expand your SQL below the line***********************  -->"));


        XmlElement voResultMap = getVoResultMap(introspectedTable);
        this.context.getCommentGenerator().addComment(voResultMap);
        document.getRootElement().addElement(voResultMap);


        XmlElement voColumn = getVoColumn(introspectedTable);
        this.context.getCommentGenerator().addComment(voColumn);
        document.getRootElement().addElement(voColumn);


        XmlElement voWhereElements = getVoWhereElements(introspectedTable);
        this.context.getCommentGenerator().addComment(voWhereElements);
        document.getRootElement().addElement(voWhereElements);


        XmlElement tableJoinElements = getTableJoinElements(introspectedTable);
        this.context.getCommentGenerator().addComment(tableJoinElements);
        document.getRootElement().addElement(tableJoinElements);


        return true;
    }

    private XmlElement getVoResultMap(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("resultMap");
        answer.addAttribute(new Attribute("id", "VoResultMap"));
        answer.addElement(new TextElement("<!--  <association property=\"beanName\" javaType=\"beanVoFullyQualifiedName\">  -->"));
        answer.addElement(new TextElement("<!--  <result column=\"dbField\" property=\"beanField\" jdbcType=\"dbFieldStyle\" /> -->"));
        answer.addElement(new TextElement("<!--  </association> -->"));
        String returnType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            returnType = introspectedTable.getRecordWithBLOBsType();
        } else {
            returnType = introspectedTable.getBaseRecordType();
        }
        String domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
        returnType = returnType.replace(domainObjectName, "aide." + domainObjectName + "Vo");

        answer.addAttribute(new Attribute("type", returnType));

        answer.addAttribute(new Attribute("extends", "BaseResultMap"));

        this.context.getCommentGenerator().addComment(answer);

        return answer;
    }

    private XmlElement getVoColumn(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id", "Vo_Column_List"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iter = introspectedTable
                .getNonBLOBColumns().iterator();
        while (iter.hasNext()) {
            sb.append("t.");
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(
                    (IntrospectedColumn) iter.next()));
            if (iter.hasNext()) {
                sb.append(", ");
            }
            answer.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
        }
        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(new TextElement(" <!-- ,alias.field -->"));
        return answer;
    }

    private XmlElement getVoWhereElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "Vo_Where_Clause"));

        answer.addElement(new TextElement("<!-- <if test=\"queryBeanField != null\" > -->"));
        answer.addElement(new TextElement("<!-- and dbField = #{queryBeanField,jdbcType=dbFieldStyle} -->"));
        answer.addElement(new TextElement("<!-- </if> -->"));

        return answer;
    }

    private XmlElement getQueryWhereElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id", "Query_Where_Clause"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        XmlElement dynamicElement = new XmlElement("where");
        answer.addElement(dynamicElement);


        Iterator localIterator = introspectedTable.getAllColumns().iterator();
        while (localIterator.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) localIterator.next();
            XmlElement isNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append("  and ");
            sb.append("t.");
            sb.append(
                    MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(
                    MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        XmlElement includeWhereElement = new XmlElement("include");
        includeWhereElement.addAttribute(new Attribute("refid", "Vo_Where_Clause"));
        dynamicElement.addElement(includeWhereElement);


        XmlElement isShotNotNullElement = new XmlElement("if");
        isShotNotNullElement.addAttribute(new Attribute("test", "sorting != null"));
        sb.setLength(0);

        sb.append("order by ${sorting}");
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
            IntrospectedColumn primaryKeyColumn = (IntrospectedColumn) introspectedTable.getPrimaryKeyColumns().get(0);
            sb.append(",t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
            sb.append(" desc");
        }
        isShotNotNullElement.addElement(new TextElement(sb.toString()));
        answer.addElement(isShotNotNullElement);

        return answer;
    }

    private XmlElement getVoByIdElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", "selectVoByPrimaryKey"));
        answer.addAttribute(new Attribute("parameterType", "java.util.Map"));
        answer.addAttribute(new Attribute("resultMap", "VoResultMap"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        XmlElement includeColumnElement = new XmlElement("include");
        includeColumnElement.addAttribute(new Attribute("refid", "Vo_Column_List"));
        answer.addElement(includeColumnElement);
        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" t");
        answer.addElement(new TextElement(sb.toString()));
        addTableJoinClause(answer);
        boolean and = false;

        Iterator localIterator = introspectedTable.getPrimaryKeyColumns().iterator();
        while (localIterator.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) localIterator.next();
            sb.setLength(0);
            if (and) {
                sb.append("  and ");
            } else {
                sb.append("where ");
                and = true;
            }
            sb.append("t.");
            sb.append(
                    MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(
                    MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            answer.addElement(new TextElement(sb.toString()));
        }
        return answer;
    }

    private XmlElement getCountElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", "selectCount"));
        answer.addAttribute(new Attribute("parameterType", "java.util.Map"));
        answer.addAttribute(new Attribute("resultType", "java.lang.Long"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
            IntrospectedColumn primaryKeyColumn = (IntrospectedColumn) introspectedTable.getPrimaryKeyColumns().get(0);
            sb.append("select count(t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
            sb.append(") from ");
        } else {
            sb.append("select count(1) from ");
        }
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" t");
        answer.addElement(new TextElement(sb.toString()));
        addTableJoinClause(answer);
        XmlElement includeWhereElement = new XmlElement("include");
        includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
        answer.addElement(includeWhereElement);

        return answer;
    }

    private XmlElement getSelectElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute("id", "select"));
        answer.addAttribute(new Attribute("parameterType", "java.util.Map"));
        answer.addAttribute(new Attribute("resultMap", "VoResultMap"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        XmlElement includeColumnElement = new XmlElement("include");
        includeColumnElement.addAttribute(new Attribute("refid", "Vo_Column_List"));
        answer.addElement(includeColumnElement);
        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" t");
        answer.addElement(new TextElement(sb.toString()));
        addTableJoinClause(answer);
        XmlElement includeWhereElement = new XmlElement("include");
        includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
        answer.addElement(includeWhereElement);

        return answer;
    }

    private XmlElement getDeleteElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("delete");

        answer.addAttribute(new Attribute("id", "delete"));
        answer.addAttribute(new Attribute("parameterType", "java.util.Map"));

        this.context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        sb.append(" where ");
        if (introspectedTable.getPrimaryKeyColumns().size() > 0) {
            IntrospectedColumn primaryKeyColumn = (IntrospectedColumn) introspectedTable.getPrimaryKeyColumns().get(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
            sb.append(" in (");
            answer.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
            sb.append("select t.");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(primaryKeyColumn));
            sb.append(" from ");
            sb.append("(select * from ");
            sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
            sb.append(") t");
            answer.addElement(new TextElement(sb.toString()));
            addTableJoinClause(answer);
            XmlElement includeWhereElement = new XmlElement("include");
            includeWhereElement.addAttribute(new Attribute("refid", "Query_Where_Clause"));
            answer.addElement(includeWhereElement);
            sb.setLength(0);
            sb.append(")");
            answer.addElement(new TextElement(sb.toString()));
        } else {
            sb.append("1 = 2");
            answer.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
        }
        return answer;
    }

    private XmlElement getTableJoinElements(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("sql");
        answer.addAttribute(new Attribute("id", "Table_Join_Clause"));
        answer.addElement(new TextElement("<!-- left join tableName tn on t.primaryKey=tn.foreignKey -->"));

        return answer;
    }

    private void addTableJoinClause(XmlElement answer) {
        XmlElement includeJoinElement = new XmlElement("include");
        includeJoinElement.addAttribute(new Attribute("refid", "Table_Join_Clause"));
        answer.addElement(includeJoinElement);
    }
}

