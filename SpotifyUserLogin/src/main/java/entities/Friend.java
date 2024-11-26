package entities;

import org.json.JSONObject;

public class Friend {

    private final JSONObject miscFriendData;
    private final String id;
    private final String name;

    public Friend(JSONObject friendData) {
        miscFriendData = friendData;
        id = friendData.getString("id");
        name = friendData.getString("name");
    }

    public JSONObject getMiscFriendData() {
        return miscFriendData;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
