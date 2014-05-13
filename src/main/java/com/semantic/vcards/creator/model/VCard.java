package com.semantic.vcards.creator.model;

/**
 * @author Tomasz Lelek
 * @since 2014-05-11
 */
public class VCard {
    private String givenName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFullName() {

        return fullName;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return "VCard{" +
                "fullName='" + fullName + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    private String fullName;
    private String nickName;

    public String getGivenName() {
        return fullName.split(" ")[0];
    }

    public String getFamilyName() {
        try{
            return fullName.split(" ")[1];
        }catch(Exception e ){
            return "";
        }
    }
}
