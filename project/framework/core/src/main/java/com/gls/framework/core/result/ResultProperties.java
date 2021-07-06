package com.gls.framework.core.result;

import com.gls.framework.core.constants.FrameworkConstants;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = FrameworkConstants.RESULT_PROPERTIES_PREFIX, ignoreInvalidFields = true)
public class ResultProperties {
    private List<String> methods = new ArrayList<>();
}
