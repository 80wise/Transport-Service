package DAL.Interfaces;

import DAL.Models.Tracking;

public interface ITrackingRepository extends IBaseRepositoryDA<Tracking>, IDate {
    boolean changeLocation(int idTrack, String location) throws Exception;
}
