package com.xmplar.music.playlist.util;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Utilities class for Gson functionality
 */
public class GsonUtils {
	
	 private static final Gson gsson = new Gson();
	
	private static final Gson gson = new GsonBuilder()
			.serializeNulls()
			.setDateFormat("MM/dd/yyyy HH:mm:ss.SSS z")
			.excludeFieldsWithoutExposeAnnotation()
			.setPrettyPrinting().disableHtmlEscaping()
			.create();

	/**
	 * Takes an object and deserializes it to a json string
	 * @param obj - class we're deserializing
	 * @return - json string
	 */
	public static String deserializeObjectToJSON(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * Serializes a json string into T class type object.
	 * 
	 * @param json
	 * @param classType
	 * @return - an object of type T
	 */
	public static <T> Object serializeObjectFromJSON(String json, Class<T> classType) {
		return gson.fromJson(json, classType);
	}
	
	/**
	 * Serializes a json string into T class type object.
	 * 
	 * @param json
	 * @param classType
	 * @return - an object of type T
	 */
	public static <T> Object serializeObjectFromJSON(String json, Type classType) {
		return gson.fromJson(json, classType);
	}
	
	/**
	 * Serializes a json string into T class type object.
	 * 
	 * @param json
	 * @param listType
	 * @return - a list of object type T
	 */
	public static <T> List<T> serializeListOfObjectsFromJSON(String json, Type listType) {
		return gson.fromJson(json, listType);
	}

	/**
	 * Converts an object into a JsonElement tree. This is useful to get the children of a parent
	 * or to get the attributes from a node
	 * 
	 * @param yourMap - the LinkedMapTree map, in most cases
	 * @return - The JsonElement object that represents yourMap
	 */
	public static JsonElement toJsonTree(Object yourMap) {
		return gson.toJsonTree(yourMap);
	}
	
	/**
	 * Converts a map into a JsonElement and then into an Object (classType)
	 * 
	 * @param <T> - the object we're converting into
	 * @param objectAttributeMap - the map
	 * @param classType - the type of object we're converting into
	 * @return
	 */
//	public static <T> Object serializeObjectFromMap(Map<String, Object> objectAttributeMap, Type classType) {
//		
//		JsonElement jsonElement = gson.toJsonTree(objectAttributeMap);
//		return gson.fromJson(jsonElement, classType);
//	}
	
	public static <T> T serializeObjectFromMap(Map<String, Object> map, Class<T> clazz) {
        String json = gsson.toJson(map);
        return gsson.fromJson(json, clazz);
    }

	/**
	 * Converts a reader into an Object (classType)
	 * 
	 * @param <T> - the object we're converting into
	 * @param reader - the BufferedReader we're converting into an object
	 * @param classType - the type of object we're converting into
	 * @return
	 */
	public static <T> T serializeObjectFromJSON(BufferedReader reader, Class<T> classType) {
		return gson.fromJson(reader, classType);
	}
	
	/**
	 * @param obj
	 * @return
	 */
	public static String convertToJosnString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
}