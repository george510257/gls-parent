package com.gls.generate.code.helper;

import com.gls.generate.code.model.ColumnModel;
import com.gls.generate.code.model.TableModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Slf4j
@Component
public class JdbcHelper {
    private static final String TABLE_PREFIX = "tb_";
    private static final String TABLES_SQL = "SELECT t.TABLE_NAME, t.TABLE_COMMENT FROM information_schema.TABLES t WHERE t.TABLE_SCHEMA = ?";
    private static final String COLUMNS_SQL = "SELECT t1.COLUMN_NAME, t1.DATA_TYPE, t1.CHARACTER_MAXIMUM_LENGTH, t1.COLUMN_COMMENT, t1.COLUMN_KEY, t2.REFERENCED_TABLE_NAME " +
            "FROM information_schema.COLUMNS t1 LEFT JOIN information_schema.KEY_COLUMN_USAGE t2 " +
            "ON t1.TABLE_SCHEMA = t2.TABLE_SCHEMA " +
            "AND t1.TABLE_NAME = t2.TABLE_NAME " +
            "AND t1.COLUMN_NAME = t2.COLUMN_NAME " +
            "WHERE t1.TABLE_SCHEMA = ? " +
            "AND t1.TABLE_NAME = ? " +
            "ORDER BY t1.ORDINAL_POSITION ";
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTables(String schema) {
        return jdbcTemplate.queryForList(TABLES_SQL, schema);
    }

    public List<Map<String, Object>> getTableColumns(String schema, String tableName) {
        return jdbcTemplate.queryForList(COLUMNS_SQL, schema, tableName);
    }

    public List<TableModel> getTableModels(String basePackage, String schema) {
        List<TableModel> tableModels = new ArrayList<>();
        List<Map<String, Object>> tables = getTables(schema);
        tables.forEach(table -> {
            String tableName = StringHelper.getString(table.get("TABLE_NAME"));
            String entityName = getEntityName(tableName);
            String entityComment = StringHelper.getString(table.get("TABLE_COMMENT"));
            log.info("tableName:{},entityName:{},entityComment:{}", tableName, entityName, entityComment);
            TableModel tableModel = new TableModel();
            tableModel.setBasePackageUrl(basePackage)
                    .setEntityName(entityName)
                    .setEntityNameLower(StringHelper.toLowerCaseFirstOne(entityName))
                    .setEntityComment(entityComment)
                    .setColumns(getColumnModels(schema, tableName));
            tableModels.add(tableModel);
        });
        return tableModels;
    }

    private List<ColumnModel> getColumnModels(String schema, String tableName) {
        List<ColumnModel> columnModels = new ArrayList<>();
        List<Map<String, Object>> columns = getTableColumns(schema, tableName);
        columns.forEach(column -> {
            ColumnModel columnModel = new ColumnModel();
            String columnName = StringHelper.getString(column.get("COLUMN_NAME"));
            String columnType = StringHelper.getString(column.get("DATA_TYPE"));
            String characterMaximumLength = StringHelper.getString(column.get("CHARACTER_MAXIMUM_LENGTH"));
            String columnComment = StringHelper.getString(column.get("COLUMN_COMMENT"));
            String columnKey = StringHelper.getString(column.get("COLUMN_KEY"));
            String referencedTableName = StringHelper.getString(column.get("REFERENCED_TABLE_NAME"));
            String name = getColumnName(columnName);
            String type = getColumnType(columnType, columnKey, referencedTableName);
            String typeEntityName = type.replaceAll("Entity", "")
                    .replaceAll("List", "")
                    .replaceAll("<", "").replaceAll(">", "");
            columnModel.setName(name)
                    .setNameUpper(StringHelper.toUpperCaseFirstOne(name))
                    .setType(type)
                    .setTypeEntityName(typeEntityName)
                    .setTypeEntityNameLower(StringHelper.toLowerCaseFirstOne(typeEntityName))
                    .setComment(columnComment.replaceAll("\"", "\\\""))
                    .setColumnKey(columnKey)
                    .setReferencedTableName(referencedTableName)
                    .setLength(characterMaximumLength);
            columnModels.add(columnModel);
        });
        return columnModels;
    }

    private String getColumnType(String columnType, String columnKey, String referencedTableName) {
        if ("MUL".equals(columnKey) && StringUtils.hasText(referencedTableName)) {
            log.info("columnType:{},columnKey:{},referencedTableName:{}", columnType, columnKey, referencedTableName);
            return getEntityName(referencedTableName) + "Entity";
        }
        return convertToJava(columnType);
    }

    private String getColumnName(String columnName) {
        return StringHelper.lineToHump(columnName);
    }

    private String getEntityName(String tableName) {
        return StringHelper.toUpperCaseFirstOne(StringHelper.lineToHump(tableName.replaceFirst(TABLE_PREFIX, "")));
    }

    public String convertToJava(String columnType) {
        String result;
        switch (columnType) {
            case "mediumtext":
            case "longtext":
            case "varchar":
            case "text": {
                result = "String";
                break;
            }
            case "tinyint":
            case "int": {
                result = "Integer";
                break;
            }
            case "bigint": {
                result = "Long";
                break;
            }
            case "float": {
                result = "Float";
                break;
            }
            case "double": {
                result = "Double";
                break;
            }
            case "timestamp":
            case "datetime": {
                result = "Date";
                break;
            }
            case "bit": {
                result = "Boolean";
                break;
            }
            default: {
                log.info("columnType:{}", columnType);
                result = "Undefine";
                break;
            }
        }
        return result;
    }
}
