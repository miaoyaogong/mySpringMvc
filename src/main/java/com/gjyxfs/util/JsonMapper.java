package com.gjyxfs.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vance on 16/6/14.
 */
public class JsonMapper {

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private ObjectMapper mapper;

    private static JsonMapper jsonMapper = new JsonMapper();

    private static JsonMapper nonEmptyMapper = new JsonMapper(Include.NON_EMPTY);

    private static JsonMapper nonDefaultMapper = new JsonMapper(Include.NON_DEFAULT);

    public JsonMapper() {
        this(null);
    }

    public JsonMapper(Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(formatter);
    }

    public static JsonMapper getInstance() {
        return jsonMapper;
    }

    /**
     * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
     */
    public static JsonMapper nonEmptyMapper() {
        return nonEmptyMapper;
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。
     */
    public static JsonMapper nonDefaultMapper() {
        return nonDefaultMapper;
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    public byte[] toJsonByBytes(Object object) {

        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            logger.warn("write to json byte error:" + object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p>
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public <T> T fromJson(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(bytes, clazz);
        } catch (IOException e) {
            logger.warn("parse json bytes error:" + bytes, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, e.g. new TypeReference<Map<Integer, List<ScheduleAndPrice>>>(){}
     */
    public <T> T fromJson(String jsonString, TypeReference<T> valueType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            T t = mapper.readValue(jsonString, valueType);
            return t;
        } catch (Exception e) {
            logger.warn("parse json string by TypeReference error:" + jsonString, e);
            return null;
        }
    }

    public <T> T fromJson(byte[] bytes, TypeReference<T> valueType) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            T t = mapper.readValue(bytes, valueType);
            return t;
        } catch (Exception e) {
            logger.warn("parse json bytes by TypeReference error:" + bytes, e);
            return null;
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型.
     */
    public JavaType contructCollectionType(Class<? extends Collection> collectionClass,
        Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型.
     */
    public JavaType contructMapType(Class<? extends Map> mapClass, Class<?> keyClass,
        Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 輸出JSONP格式數據.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 設定是否使用Enum的toString函數來讀寫Enum, 為False時時使用Enum的name()函數來讀寫Enum, 默認為False. 注意本函數一定要在Mapper創建後,
     * 所有的讀寫動作之前調用.
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (json == null || json.equals("")) {
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(json, Map.class);
            if (result == null) {
                return new HashMap<String, Object>();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
