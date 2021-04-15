package com.gls.demo.security.web.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.gls.demo.security.constraints.MyConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * @author george
 */
@Data
public class UserModel implements Serializable {

    @JsonView(UserSimpleView.class)
    private Long id;

    @MyConstraint(message = "这是一个测试")
    @JsonView(UserSimpleView.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;

    @Past(message = "生日必须是过去的时间")
    @JsonView(UserSimpleView.class)
    private Date birthday;

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }
}
