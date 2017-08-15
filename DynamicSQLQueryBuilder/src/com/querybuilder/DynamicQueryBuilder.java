package com.querybuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DynamicQueryBuilder {

	private String tableName;
	private QueryType queryType;
	private List<String> columns;
	private List<String> whereClauseColumns;
	private List<Object> values;
	
	
	
	public DynamicQueryBuilder() {
		this.columns = new ArrayList<>();
		this.whereClauseColumns = new ArrayList<>();
		this.values = new ArrayList<>();
	}

	public enum QueryType {
		INSERT , DELETE , UPDATE
	}
	
	public DynamicQueryBuilder setQueryType(QueryType queryType){
		this.queryType = queryType;
		return this;
	}
	public DynamicQueryBuilder setTableName(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public Boolean addColoumnIfNotNull(String colName,Object colValue){
		if(colValue != null){
			columns.add(colName);
			values.add(colValue);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public DynamicQueryBuilder addColoumnIfNotNullMap(Map<String,Object> map){
		if(map==null) 
			return this ;
		
		for(Map.Entry<String, Object> entry: map.entrySet()){
			if(entry.getValue() != null && entry.getKey() != null){
				columns.add(entry.getKey());
				values.add(entry.getValue());
			}
		}
		return this;
	}
	
	public DynamicQueryBuilder addWhereClauseColumns(String cols[] , Object[] vals){
		if(cols==null || vals==null) 
			return this ;
		for(int i = 0 ; i < cols.length ; i++){
			if(cols[i] != null && vals[i] != null){
				whereClauseColumns.add(cols[i]);
				values.add(vals[i]);
			}
		}
		return this;
	}
	
	public DynamicQueryBuilder resetColumnsList(){
		columns.clear();
		return this;
	}
	
	public String query(){
		StringBuilder sb = new StringBuilder();
		
		switch(this.queryType){
		
		case UPDATE :
			returnQueryForUpdate(sb);
			break;
		case INSERT :
			returnQueryForInsert(sb);
			break;
		case DELETE :
			returnQueryForDelete(sb);
			break;
		default:
			break;
		}
		
		return sb.toString();
	}
	private void returnQueryForDelete(StringBuilder sb) {

		sb.append(QueryType.DELETE.toString()+" ").append(tableName);
		
		if(!whereClauseColumns.isEmpty()){
			sb.append(" WHERE ");
			addColumnsToQueryForDelete(sb , whereClauseColumns , " AND ");
		}
	}
	private void addColumnsToQueryForDelete(StringBuilder sb, List<String> colNameList, String seprator) {

		for(int i = 0 ; i < colNameList.size() ; i++){
			sb.append(colNameList.get(i)).append("=? ");
			if(i < (colNameList.size() - 1)){
				sb.append(seprator);
			}
		}
	}
	
	private void returnQueryForInsert(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
	private void returnQueryForUpdate(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}
