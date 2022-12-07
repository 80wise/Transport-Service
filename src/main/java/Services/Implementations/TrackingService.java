package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.ITrackingRepository;
import DAL.Models.Tracking;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.ITrackingService;

import java.util.List;
import java.util.Optional;

public class TrackingService implements ITrackingService {

    private final ITrackingRepository _trackingRepository;

    public TrackingService(ITrackingRepository _trackingRepository) {
        this._trackingRepository = _trackingRepository;
    }

    @Override
    public BaseResponse<Optional<Tracking>> getById(int id) {
        try {
            Optional<Tracking> track = _trackingRepository.getById(id);
            if (track.isPresent()) {
                return new BaseResponse<>(track,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),
                    "There is no track with this Id",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Tracking entity) {
        try {
            boolean created = _trackingRepository.create(entity);
            if (created) {
                return new BaseResponse<>(true,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "There is no track with this Id",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Tracking>>> getAll() {
        try {
            Optional<List<Tracking>> tracks = _trackingRepository.getAll();
            if (tracks.isPresent()) {
                return new BaseResponse<>(tracks,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),
                    "There is no track",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try {
            boolean deleted = _trackingRepository.deleteById(id);
            if (deleted) {
                return new BaseResponse<>(true,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The transport mean has not been deleted",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Tracking entity) {
        try {
            boolean updated = _trackingRepository.update(entity);
            if (updated) {
                return new BaseResponse<>(true,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The transport mean has not been updated",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> changeLocation(int idTrack, String location) {
        try {
            boolean changed = _trackingRepository.changeLocation(idTrack, location);
            if (changed) {
                return new BaseResponse<>(true,
                        "Successfully processed The location has been changed",
                        StatusCode.success);
            }

            return new BaseResponse<>(false,
                    "The location has not been changed",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
