package ${basePackageUrl}.converter;
import com.gls.framework.core.base.BaseConverter;
import ${basePackageUrl}.entity.${entityName}Entity;
import ${basePackageUrl}.model.${entityName}Model;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ${entityName}Converter implements BaseConverter<${entityName}Entity, ${entityName}Model> {

<#if columns??>
<#--循环生成变量-->
    <#list columns as col>
        <#if col["type"]?contains("Model")>
    @Resource
    private ${col["typeEntityName"]}Converter ${col["typeEntityNameLower"]}Converter;
        </#if>
    </#list>
</#if>

    @Override
    public ${entityName}Model copySourceToTarget(${entityName}Entity ${entityNameLower}Entity, ${entityName}Model ${entityNameLower}Model) {
<#if columns??>
<#--循环生成变量-->
    <#list columns as col>
        <#if col["type"]?contains("Model") >
            <#if col["type"]?starts_with("List") >
        ${entityNameLower}Model.set${col["nameUpper"]}(${col["typeEntityNameLower"]}Converter.sourceToTargetList(${entityNameLower}Entity.get${col["nameUpper"]}()));
            <#else>
        ${entityNameLower}Model.set${col["nameUpper"]}(${col["typeEntityNameLower"]}Converter.sourceToTarget(${entityNameLower}Entity.get${col["nameUpper"]}()));
            </#if>
        <#else>
        ${entityNameLower}Model.set${col["nameUpper"]}(${entityNameLower}Entity.get${col["nameUpper"]}());
        </#if>
    </#list>
</#if>
        ${entityNameLower}Model.setId(${entityNameLower}Entity.getId());
        return ${entityNameLower}Model;
    }

    @Override
    public ${entityName}Entity copyTargetToSource(${entityName}Model ${entityNameLower}Model, ${entityName}Entity ${entityNameLower}Entity) {
<#if columns??>
<#--循环生成变量-->
    <#list columns as col>
        <#if col["type"]?contains("Model") >
            <#if col["type"]?starts_with("List") >
        ${entityNameLower}Entity.set${col["nameUpper"]}(${col["typeEntityNameLower"]}Converter.targetToSourceList(${entityNameLower}Model.get${col["nameUpper"]}()));
            <#else>
        ${entityNameLower}Entity.set${col["nameUpper"]}(${col["typeEntityNameLower"]}Converter.targetToSource(${entityNameLower}Model.get${col["nameUpper"]}()));
            </#if>
        <#else>
        ${entityNameLower}Entity.set${col["nameUpper"]}(${entityNameLower}Model.get${col["nameUpper"]}());
        </#if>
    </#list>
</#if>
        ${entityNameLower}Entity.setId(${entityNameLower}Model.getId());
        return ${entityNameLower}Entity;
    }
}