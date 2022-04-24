package cn.cy.common.equator;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: 友
 * @Date: 2022/4/24 10:23
 * @Description: 不同的属性
 */
@Data
public class FieldInfo {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 第一个字段的类型
     */
    private Class<?> firstFieldType;
    /**
     * 第二个字段的类型
     */
    private Class<?> secondFieldType;
    /**
     * 第一个对象的值
     */
    private Object firstVal;
    /**
     * 第二个对象的值
     */
    private Object secondVal;

    public FieldInfo() {
    }

    public FieldInfo(String fieldName, Class<?> firstFieldType, Class<?> secondFieldType) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.secondFieldType = secondFieldType;
    }

    public FieldInfo(String fieldName, Class<?> fieldType, Object firstVal, Object secondVal) {
        this.fieldName = fieldName;
        this.firstFieldType = fieldType;
        this.secondFieldType = fieldType;
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    public FieldInfo(String fieldName, Class<?> firstFieldType, Class<?> secondFieldType, Object firstVal, Object secondVal) {
        this.fieldName = fieldName;
        this.firstFieldType = firstFieldType;
        this.secondFieldType = secondFieldType;
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldInfo fieldInfo = (FieldInfo) o;
        return Objects.equals(fieldName, fieldInfo.fieldName) &&
                Objects.equals(firstFieldType, fieldInfo.firstFieldType) &&
                Objects.equals(secondFieldType, fieldInfo.secondFieldType) &&
                Objects.equals(firstVal, fieldInfo.firstVal) &&
                Objects.equals(secondVal, fieldInfo.secondVal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, firstFieldType, secondFieldType, firstVal, secondVal);
    }

}
