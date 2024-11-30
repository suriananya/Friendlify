package entities.users;

import org.json.JSONArray;

public class Utility {
    public static JSONArray sanitizeJSONArray(JSONArray unsanitized) {
        JSONArray sanitized = new JSONArray();
        for (int i = 0; i < unsanitized.length(); i++) {
            if (!unsanitized.isNull(i)) {
                System.out.println(unsanitized.getJSONObject(i).toString());
                sanitized.put(unsanitized.getJSONObject(i));
            }
        }
        return sanitized;
    }
}
