package Helper.Interfaces;

import DAL.Enums.StatusCode;

public interface IBaseResponse<T> {
    T getResultQuery();
    void setResultQuery(T resultQuery);
    String getDescription();
    void setDescription(String description);
    StatusCode getStatusCode();
    void setStatusCode(StatusCode statusCode);
}
