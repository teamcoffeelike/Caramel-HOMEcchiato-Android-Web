package com.hanul.coffeelike.caramelweb.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.lang.Nullable;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UUIDHandler extends BaseTypeHandler<UUID>{
	@Override public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException{
		ps.setString(i, convertToString(parameter));
	}
	@Override public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException{
		return convertToUUID(rs.getString(columnName));
	}
	@Override public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
		return convertToUUID(rs.getString(columnIndex));
	}
	@Override public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
		return convertToUUID(cs.getString(columnIndex));
	}

	private String convertToString(UUID uuid){
		return uuid.toString();
	}

	private UUID convertToUUID(@Nullable String s){
		return s==null ? null : UUID.fromString(s);
	}
}
