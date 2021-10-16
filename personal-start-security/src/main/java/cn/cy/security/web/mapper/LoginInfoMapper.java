package cn.cy.security.web.mapper;

import cn.cy.security.web.model.LoginInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 友叔
 * @Date: 2020/12/25 17:18
 * @Description: 用户登陆日志Mapper
 */
@Mapper
@Repository
public interface LoginInfoMapper {
    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    public void insertLoginInfo(LoginInfo loginInfo);

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    public List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLoginInfo();
}
