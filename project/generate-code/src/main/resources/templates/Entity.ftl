package ${basePackageUrl}.entity;
import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("${entityComment}")
public class ${entityName}Entity extends BaseEntity {
<#if columns??>
<#--循环生成变量-->
    <#list columns as col>
        <#if col["columnKey"] == "PRI">
    @Id
    @Column<#if col["length"]??>(length = ${col["length"]})</#if>
        <#elseif col["columnKey"] == "UNI">
    @Column(unique = true <#if col["length"]??>, length = ${col["length"]}</#if>)
        <#elseif col["columnKey"] == "MUL">
            <#if col["referencedTableName"]??>
    @ManyToOne</#if>
    @Column<#if col["length"]??>(length = ${col["length"]})</#if>
        <#else>
    @Column<#if col["length"]??>(length = ${col["length"]})</#if>
        </#if>
    @Comment("${col["comment"]}")
    private ${col["type"]} ${col["name"]};
    </#list>
</#if>
}
