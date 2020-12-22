package com.hanul.coffeelike.caramelweb.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YesNoHandler extends BaseTypeHandler<Boolean>{
	@Override public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException{
		ps.setString(i, convertToString(parameter));
	}
	@Override public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException{
		return convertToBoolean(rs.getString(columnName));
	}
	@Override public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
		return convertToBoolean(rs.getString(columnIndex));
	}
	@Override public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
		return convertToBoolean(cs.getString(columnIndex));
	}

	private String convertToString(Boolean b){
		return b ? "Y" : "N";
	}

	private Boolean convertToBoolean(String s){
		return s==null ? null : s.equalsIgnoreCase("Y");
	}
}
