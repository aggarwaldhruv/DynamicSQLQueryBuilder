package com.querybuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The Class DynamicQueryBuilder.
 */
public class DynamicQueryBuilder {

	/** The table name. */
	private String tableName;
	
	/** The query type. */
	private QueryType queryType;
	
	/** The columns. */
	private List<String> columns;
	
	/** The where clause columns. */
	private List<String> whereClauseColumns;
	
	/** The values. */
	private List<Object> values;
	
	
	
	/**
	 * Instantiates a new dynamic query builder.
	 */
	public DynamicQueryBuilder() {
		this.columns = new ArrayList<>();
		this.whereClauseColumns = new ArrayList<>();
		this.values = new ArrayList<>();
	}

	/**
	 * The Enum QueryType.
	 */
	public enum QueryType {
		
		/** The insert. */
		INSERT , 
		/** The delete. */
		DELETE , 
		/** The update. */
		UPDATE
	}
	
	/**
	 * Sets the query type.
	 *
	 * @param queryType the query type
	 * @return the dynamic query builder
	 */
	public DynamicQueryBuilder setQueryType(QueryType queryType){
		this.queryType = queryType;
		return this;
	}
	
	/**
	 * Sets the table name.
	 *
	 * @param tableName the table name
	 * @return the dynamic query builder
	 */
	public DynamicQueryBuilder setTableName(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	/**
	 * Adds the coloumn if not null.
	 *
	 * @param colName the col name
	 * @param colValue the col value
	 * @return the boolean
	 */
	public Boolean addColoumnIfNotNull(String colName,Object colValue){
		if(colValue != null){
			columns.add(colName);
			values.add(colValue);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	/**
	 * Adds the coloumn if not null map.
	 *
	 * @param map the map
	 * @return the dynamic query builder
	 */
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
	
	/**
	 * Adds the where clause columns.
	 *
	 * @param cols the cols
	 * @param vals the vals
	 * @return the dynamic query builder
	 */
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
	
	/**
	 * Reset columns list.
	 *
	 * @return the dynamic query builder
	 */
	public DynamicQueryBuilder resetColumnsList(){
		columns.clear();
		return this;
	}
	
	/**
	 * Query.
	 *
	 * @return the string
	 */
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
	
	/**
	 * Return query for delete.
	 *
	 * @param sb the sb
	 */
	private void returnQueryForDelete(StringBuilder sb) {

		sb.append(QueryType.DELETE.toString()+" ").append(tableName);
		
		if(!whereClauseColumns.isEmpty()){
			sb.append(" WHERE ");
			addColumnsToQueryForDelete(sb , whereClauseColumns , " AND ");
		}
	}
	
	/**
	 * Adds the columns to query for delete.
	 *
	 * @param sb the sb
	 * @param colNameList the col name list
	 * @param seprator the seprator
	 */
	private void addColumnsToQueryForDelete(StringBuilder sb, List<String> colNameList, String seprator) {

		for(int i = 0 ; i < colNameList.size() ; i++){
			sb.append(colNameList.get(i)).append("=? ");
			if(i < (colNameList.size() - 1)){
				sb.append(seprator);
			}
		}
	}
	
	/**
	 * Return query for insert.
	 *
	 * @param sb the sb
	 */
	private void returnQueryForInsert(StringBuilder sb) {
		StringBuilder valuesSb = new StringBuilder();
		sb.append("Insert into ").append(tableName).append(" ( ");
		for(int i=0 ; i<columns.size() ; i++){
			sb.append(columns.get(i)).append(" , ");
			
			valuesSb.append("?").append(" , ");
			
		}
		sb.deleteCharAt(sb.length() - 2);
		valuesSb.deleteCharAt(sb.length() - 2);
		sb.append(" ) values (").append(valuesSb).append(" ) " );
		
	}
	
	/**
	 * Return query for update.
	 *
	 * @param sb the sb
	 */
	private void returnQueryForUpdate(StringBuilder sb) {
		sb.append("Update").append(tableName).append(" Set ");
		addColoumnsToQueryForUpdate(sb,columns," , ");
		
		if( whereClauseColumns != null && (!whereClauseColumns.isEmpty())){
			sb.append(" where " );
			addColoumnsToQueryForUpdate(sb,columns," AND ");
		}
	}
	
	/**
	 * Adds the coloumns to query for update.
	 *
	 * @param sb the sb
	 * @param columns2 the columns 2
	 * @param string the string
	 */
	private void addColoumnsToQueryForUpdate(StringBuilder sb, List<String> columns2, String string) {
		for(int i = 0 ; i < columns2.size() ; i++){
			if( i < (columns2.size()-1)){
				sb.append(string);
			}
		}
	}
}
