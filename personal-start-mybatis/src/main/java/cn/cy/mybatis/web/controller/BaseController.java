package cn.cy.mybatis.web.controller;

import cn.cy.common.util.StringUtils;
import cn.cy.common.util.sql.SqlUtil;
import cn.cy.mybatis.web.page.PageDomain;
import cn.cy.mybatis.web.page.TableDataInfo;
import cn.cy.mybatis.web.page.TableSupport;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/11 21:32
 * @Description: 基础公共的Controller
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            // 检测 orderBy 字符串，防止 sql 注入
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     *
     * @param list 列表数据
     * @return 分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected <T> TableDataInfo<T> getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        PageHelper.clearPage();
        return rspData;
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StrUtil.format("redirect:{}", url);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
