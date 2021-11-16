package cn.cy.mybatis.web.page;


import cn.cy.framework.ResponseType;

import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author 友叔
 */
public class TableDataInfo<T> implements ResponseType {

    /**
     * 状态
     */
    private int code;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}