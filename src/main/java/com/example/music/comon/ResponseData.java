package com.example.music.comon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> {

    @NonNull
    private T result;

    @NonNull
    private T data;

    public static <T> ResponseData <T> createResponse(Map<Object, Object> mapData) {
        ResponseData responseData = new ResponseData();
        responseData.setData(mapData.getOrDefault(Constant.RESPONSE_KEY.DATA, null));
        responseData.setResult(mapData.getOrDefault(Constant.RESPONSE_KEY.RESULT, Result.errSystem()));
        return responseData;
    }

}
