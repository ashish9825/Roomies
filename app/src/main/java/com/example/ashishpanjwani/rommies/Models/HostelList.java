
package com.example.ashishpanjwani.rommies.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HostelList {

    @SerializedName("hid")
    @Expose
    private Integer hid;
    @SerializedName("hname")
    @Expose
    private String hname;
    @SerializedName("haddress")
    @Expose
    private String haddress;
    @SerializedName("imgurl")
    @Expose
    private String imgurl;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("rent")
    @Expose
    private String rent;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("feature_1")
    @Expose
    private String feature1;
    @SerializedName("feature_2")
    @Expose
    private String feature2;
    @SerializedName("feature_3")
    @Expose
    private String feature3;
    @SerializedName("oid")
    @Expose
    private Integer oid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHaddress() {
        return haddress;
    }

    public void setHaddress(String haddress) {
        this.haddress = haddress;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
