package Services.Implementations;

import DAL.Enums.StatusCode;
import DAL.Interfaces.ITransportRepository;
import DAL.Models.Transport;
import Helper.Implementation.BaseResponse;
import Services.Interfaces.Repositories.ITransportService;

import java.util.List;
import java.util.Optional;

public class TransportService implements ITransportService {

    private final ITransportRepository _transportRepository;

    public TransportService(ITransportRepository _transportRepository) {
        this._transportRepository = _transportRepository;
    }

    @Override
    public BaseResponse<Optional<Transport>> getById(int id) {
        try {
            Optional<Transport> transport = _transportRepository.getById(id);
            if (transport.isPresent()) {
                return new BaseResponse<Optional<Transport>>(transport,
                        "Successfully processed",
                        StatusCode.success);
            }

            return new BaseResponse<>(Optional.empty(),
                    "There is no transport with this Id",
                    StatusCode.errorClient);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> create(Transport entity) {
        try {
            boolean created = _transportRepository.create(entity);
            if (created) {
                return new BaseResponse<>(true, "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(false, "The transport has not been created",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Error server", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Transport>>> getAll() {
        try {
            Optional<List<Transport>> transports = _transportRepository.getAll();
            if (transports.isPresent()) {
                return new BaseResponse<>(transports, "Successfully processed", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "The is no mean of transportation",
                    StatusCode.emptyResource);

        } catch (Exception ex) {
            return new BaseResponse<>(null, "Successfully processed", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> deleteById(int id) {
        try {
            boolean deleted = _transportRepository.deleteById(id);
            if (deleted) {
                return new BaseResponse<>(true, "The user have been deleted", StatusCode.success);
            }
            return new BaseResponse<>(false, "The used was not deleted", StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Boolean> update(Transport entity) {
        try {
            boolean updated = _transportRepository.update(entity);
            if (updated) {
                return new BaseResponse<>(true, "The user has been updated", StatusCode.success);
            }
            return new BaseResponse<>(false,
                    "the user was not updated",
                    StatusCode.errorClient);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }

    @Override
    public BaseResponse<Optional<List<Transport>>> getByName(String name) {
        try {
            Optional<List<Transport>> transport = _transportRepository.getByName(name);
            if (transport.isPresent()) {
                return new BaseResponse<>(transport, "Processed successfully", StatusCode.success);
            }
            return new BaseResponse<>(Optional.empty(),
                    "Unsuccessfully processed",
                    StatusCode.emptyResource);
        } catch (Exception ex) {
            return new BaseResponse<>(null, "Server error", StatusCode.errorServer);
        }
    }
}
