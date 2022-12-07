package Helper.Implementation;

import DAL.Enums.StatusCode;
import Helper.Interfaces.IBaseResponse;

public class BaseResponse<T> implements IBaseResponse<T> {
    private T resultQuery;
    private String description;
    private StatusCode statusCode;

    public BaseResponse(T resultQuery,String description, StatusCode statusCode){
        this.resultQuery = resultQuery;
        this.description = description;
        this.statusCode = statusCode;
    }

    @Override
    public T getResultQuery() {
        return resultQuery;
    }

    @Override
    public void setResultQuery(T resultQuery) {
        this.resultQuery = resultQuery;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
