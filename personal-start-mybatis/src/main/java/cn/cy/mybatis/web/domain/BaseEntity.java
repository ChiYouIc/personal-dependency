package cn.cy.mybatis.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 友叔
 * @Date: 2020/11/24 21:14
 * @Description: Entity基类
 */
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String createdBy;

    private LocalDateTime createdTime;

    private String updatedBy;

    private LocalDateTime updatedTime;

    /**
     * 开始时间
     */
    @JsonIgnore
    private String beginTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    private String endTime;

    /**
     * 请求参数
     */
    @JsonIgnore
    @Getter(value = AccessLevel.NONE)
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>(8);
        }
        return params;
    }

}
