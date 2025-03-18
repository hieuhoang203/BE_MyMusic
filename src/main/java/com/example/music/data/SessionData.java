package com.example.music.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionData {

    @ApiModelProperty(notes = "Id phiên làm việc")
    private String session_id;

    @ApiModelProperty(notes = "Id định danh user")
    private String user_id;

    @ApiModelProperty(notes = "Username đăng nhập của khách hàng")
    private String user_name;

    @ApiModelProperty(notes = "Tên người dùng")
    private String display_name;

}
