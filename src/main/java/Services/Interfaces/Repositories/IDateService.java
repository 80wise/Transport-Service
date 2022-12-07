package Services.Interfaces.Repositories;

import Helper.Implementation.BaseResponse;

import java.sql.Date;

public interface IDateService {
    BaseResponse<Boolean> setDate(int idEntity, Date date);
}
