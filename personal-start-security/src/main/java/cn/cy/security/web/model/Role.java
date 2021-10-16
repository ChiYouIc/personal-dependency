package cn.cy.security.web.model;

import cn.cy.mybatis.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 友叔
 * @Date: 2021/3/26 14:47
 * @Description: 角色
 */
@Setter
@Getter
@ToString
@Alias("Role")
public class Role extends BaseEntity {
    private Long id;

    private String roleName;

    private String remark;

    private boolean status;

}
