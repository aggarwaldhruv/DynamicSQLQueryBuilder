# DynamicSQLQueryBuilder

Simple Java API to create SQL queries (Insert , Update and Delete) during runtime.  
While using Spring JDBCTemplate for DAO layer , there was no method which removes the null entries automatically. So if updating , there are some null values , then jdbctemplate will set it to null.
