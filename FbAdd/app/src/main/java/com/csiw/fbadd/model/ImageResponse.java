package com.csiw.fbadd.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {

@SerializedName("img_url")
@Expose
private String imgUrl;
@SerializedName("type")
@Expose
private String type;

public String getImgUrl() {
return imgUrl;
}

public void setImgUrl(String imgUrl) {
this.imgUrl = imgUrl;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

}