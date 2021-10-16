package cn.cy.security.web.model;

import cn.cy.mybatis.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Author: 友叔
 * @Date: 2021/3/26 14:49
 * @Description: 权限
 */
@Setter
@Getter
@ToString
@Alias("Permission")
public class Permission extends BaseEntity {
    private Long id;

    private String permissionName;

    private String remark;

    private boolean status;
}
