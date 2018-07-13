package cn.xz.dbdicgen;

public enum EMapKey {
    TABLE_NAME("table_name"),
    TABLE_COMMENT("table_comment"),
    TABLE_ENGINE("engine"),

    COLUMN_NAME("column_name"),
    COLUMN_TYPE("column_type"),
    COLUMN_KEY("column_key"),
    COLUMN_DEFAULT("column_default"),
    COLUMN_IS_NULLABLE("is_nullable"),
    COLUMN_COMMENT("column_comment"),;

    private String key;

    EMapKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
