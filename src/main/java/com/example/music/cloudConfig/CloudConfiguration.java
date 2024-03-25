package com.example.music.cloudConfig;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudConfiguration {

    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hieuhv203",
                "api_key", "626331438846633",
                "api_secret", "XDVR-B8ZQiCsIZFFqtvkGnh4G6g",
                "secure", true));
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

}
