package ${basePackageUrl}.model;
import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ${entityName}Model extends BaseModel {
    <#if columns??>
    <#--循环生成变量-->
        <#list columns as col>
    private ${col["type"]} ${col["name"]};
        </#list>
    </#if>
}