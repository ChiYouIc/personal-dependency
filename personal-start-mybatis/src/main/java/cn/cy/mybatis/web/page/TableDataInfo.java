package cn.cy.mybatis.web.page;


import cn.cy.web.response.SuccessResponse;

import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author 友叔
 */
public class TableDataInfo<T> extends SuccessResponse<T> {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}