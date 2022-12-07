package Services;

import Helper.Implementation.BaseResponse;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    BaseResponse<Optional<T>> getById(int id);
    BaseResponse<Boolean> create(T entity);
    BaseResponse<Optional<List<T>>> getAll();
    BaseResponse<Boolean> deleteById(int id);
    BaseResponse<Boolean> update(T entity);
}
