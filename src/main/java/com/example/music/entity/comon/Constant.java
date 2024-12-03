package com.example.music.entity.comon;

import lombok.Getter;

public class Constant {

    public interface RESPONSE_KEY {
        String RESULT = "RESULT_KEY";
        String DATA = "DATA_KEY";
    }

    public interface Role {
        String ADMIN = "ADMIN";
        String USER = "USER";
        String ARTIS = "ARTIS";
    }

    public interface Status {
        String Activate = "Activate";
        String ShutDown = "ShutDown";
        String Wait = "Wait";
    }

}
