package com.example.music.comon;

public class Constant {

    public interface RESPONSE_KEY {
        String RESULT = "RESULT_KEY";
        String DATA = "DATA_KEY";
    }

    public enum Role {
        ADMIN,
        USER,
        ARTIS
    }

    public interface Status {
        String Activate = "Activate";
        String ShutDown = "ShutDown";
        String Wait = "Wait";
    }

    public interface Create {
        String HVH = "Hoàng Văn Hiếu";
        String NTH = "Nguyễn Thanh Hòa";
    }

}
