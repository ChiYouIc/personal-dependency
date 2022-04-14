package cn.cy.log;

/**
 * @author: 开水白菜
 * @description: 操作类型
 * @create: 2021-07-13 22:34
 **/
public enum OperationType {
    /**
     * 查询
     */
    SEARCH,

    /**
     * 新增
     */
    INSERT,
    /**
     * 更新
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 导出
     */
    EXPORT;
}
