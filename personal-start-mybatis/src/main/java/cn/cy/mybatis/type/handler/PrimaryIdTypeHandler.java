package cn.cy.mybatis.type.handler;

import cn.cy.mybatis.type.PrimaryId;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 友叔
 * @Date: 2021/12/28 11:34
 * @Description: 主键ID类型处理器
 */
@MappedJdbcTypes(JdbcType.BIGINT)
@MappedTypes(PrimaryId.class)
public class PrimaryIdTypeHandler extends BaseTypeHandler<PrimaryId> {

	/**
	 * 设置参数
	 *
	 * @param ps        预编译的 SQL 语句对象
	 * @param index     预编译的 SQL 语句对象的参数插槽位置
	 * @param parameter 参数
	 * @param jdbcType  数据库字段类型
	 * @throws SQLException
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int index, PrimaryId parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(index, parameter.getValue());
	}

	/**
	 * 获取值
	 *
	 * @param rs         结果集
	 * @param columnName 列名
	 * @return PrimaryId 对象
	 */
	@Override
	public PrimaryId getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return new PrimaryId(rs.getString(columnName));
	}

	/**
	 * 获取值
	 *
	 * @param rs          结果集
	 * @param columnIndex 列序号
	 * @return PrimaryId 对象
	 */
	@Override
	public PrimaryId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return new PrimaryId(rs.getString(columnIndex));
	}

	/**
	 * 获取值
	 *
	 * @param cs          SQL 存储过程的接口
	 * @param columnIndex 列序号
	 * @return PrimaryId 对象
	 */
	@Override
	public PrimaryId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new PrimaryId(cs.getString(columnIndex));
	}
}
