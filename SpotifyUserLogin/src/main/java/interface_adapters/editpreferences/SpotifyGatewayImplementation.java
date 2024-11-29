package interface_adapters.editpreferences;
import api.SpotifyInteractor;
import org.json.JSONObject;

public class SpotifyGatewayImplementation extends SpotifyDataGateway {
    private final SpotifyInteractor interactor;

    public SpotifyGatewayImplementation(SpotifyInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public JSONObject getCurrentUserPlaylists(int limit, int offset) {
        return interactor.getCurrentUserPlaylists(limit, offset);
    }
}

