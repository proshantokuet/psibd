package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.dto.UserDTO;

public class UserDataConverter {
	
	public JSONObject toConvert(UserDTO user) throws JSONException {
		JSONObject userJson = new JSONObject();
		userJson.putOpt("id", user.getId());
		userJson.putOpt("username", user.getUsername());
		userJson.putOpt("userRole", user.getUserRole());
		userJson.putOpt("designation", user.getRole());
		return userJson;
	}
	
	public JSONArray toConvert(List<UserDTO> users) throws JSONException {
		JSONArray usersJson = new JSONArray();
		for (UserDTO user : users) {
			usersJson.put(toConvert(user));
		}
		return usersJson;
	}
}
