package TestRoulette;

import useCase.Roulette.SurpriseMeInteractor;

public class TestSurpriseMeInteractor {

    private final MockSpotifyInteractor mockSpotifyInteractor = new MockSpotifyInteractor();
    private final MockUserProfile mockUserProfile = new MockUserProfile(mockSpotifyInteractor);
    private final SurpriseMeInteractor surpriseMeInteractor = new SurpriseMeInteractor(mockSpotifyInteractor, mockUserProfile);
}
