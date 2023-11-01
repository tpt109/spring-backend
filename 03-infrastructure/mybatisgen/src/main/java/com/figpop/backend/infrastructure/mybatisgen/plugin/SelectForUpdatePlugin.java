package com.figpop.backend.infrastructure.mybatisgen.plugin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.SelectByPrimaryKeyMethodGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

@Slf4j
public class SelectForUpdatePlugin extends PluginAdapter {
    private static final String CONFIG_XML_KEY = "implementSelectForUpdate";

    private static final String METHOD_NAME = "selectByIdForUpdate";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String implementUpdate = introspectedTable.getTableConfiguration().getProperty(CONFIG_XML_KEY);
        if (StringUtility.isTrue(implementUpdate)) {
            Method method = new Method(METHOD_NAME);
            FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
            method.setReturnType(returnType);
            method.setAbstract(true);
            method.addParameter( new Parameter(introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType(), "id" ));
            String  docComment  =  "/**\n" +
                    " * Use id to pessimistically lock the data row\n" +
                    " */" ;
            method.addJavaDocLine(docComment);
            interfaze.addMethod(method);
        }

        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String implementUpdate = introspectedTable.getTableConfiguration().getProperty(CONFIG_XML_KEY);
        if (!StringUtility.isTrue(implementUpdate)) {
            return super.sqlMapDocumentGenerated(document, introspectedTable);
        }

        XmlElement selectForUpdate = new XmlElement("select");
        selectForUpdate.addAttribute(new Attribute("id", METHOD_NAME));
        StringBuilder sb;

        String resultMapId = introspectedTable.hasBLOBColumns() ? introspectedTable.getResultMapWithBLOBsId() : introspectedTable.getBaseResultMapId();
        selectForUpdate.addAttribute(new Attribute("resultMap", resultMapId));
        selectForUpdate.addAttribute(new Attribute("parameterType", introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString()));
        selectForUpdate.addElement(new TextElement("select"));

        sb = new StringBuilder();
        if (StringUtility.stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,");
            selectForUpdate.addElement( new  TextElement (sb.toString()));
        }

        XmlElement baseColumn = new XmlElement("include");
        baseColumn.addAttribute(new Attribute("refid", introspectedTable.getBaseColumnListId()));
        selectForUpdate.addElement(baseColumn);
        if (introspectedTable.hasBLOBColumns()) {
            selectForUpdate.addElement(new TextElement(","));
            XmlElement blobColumns = new XmlElement("include");
            blobColumns.addAttribute(new Attribute("refid", introspectedTable.getBaseColumnListId()));
            selectForUpdate.addElement(blobColumns);
        }

        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        selectForUpdate.addElement(new TextElement(sb.toString()));
        TextElement whereXml = new TextElement("where id = #{id} for update");
        selectForUpdate.addElement(whereXml);

        document.getRootElement().addElement(selectForUpdate);
        LOGGER.debug( "(pessimistic lock plug-in):" + introspectedTable.getMyBatis3XmlMapperFileName() + "Add" + METHOD_NAME + "Method(" + (introspectedTable.hasBLOBColumns() ? "Yes" : "None" ) + "Blob type) )." );
        return  super .sqlMapDocumentGenerated(document, introspectedTable);
    }


}
