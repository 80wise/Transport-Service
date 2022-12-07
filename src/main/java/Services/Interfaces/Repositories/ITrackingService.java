package Services.Interfaces.Repositories;

import DAL.Models.Tracking;
import Helper.Implementation.BaseResponse;
import Services.IBaseService;

public interface ITrackingService extends IBaseService<Tracking> {
    BaseResponse<Boolean> changeLocation(int idTrack, String location);
}
