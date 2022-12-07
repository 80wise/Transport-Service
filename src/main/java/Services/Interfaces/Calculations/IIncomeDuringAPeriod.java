package Services.Interfaces.Calculations;

import Helper.Implementation.BaseResponse;

import java.util.Date;

public interface IIncomeDuringAPeriod {
    BaseResponse<Double> getIncomeDuringAPeriod(Date from, Date to);
}
