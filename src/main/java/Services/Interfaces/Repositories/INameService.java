package Services.Interfaces.Repositories;

import Helper.Implementation.BaseResponse;

import java.util.List;
import java.util.Optional;

public interface INameService<T> {
    BaseResponse<Optional<List<T>>> getByName(String name);
}
