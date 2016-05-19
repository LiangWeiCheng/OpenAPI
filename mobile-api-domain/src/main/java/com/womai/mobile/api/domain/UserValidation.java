package com.womai.mobile.api.domain;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jijun.yang
 * Date: 13-7-10
 * Time: 上午9:33
 * To change this template use File | Settings | File Templates.
 */
public class UserValidation extends ValidationInfo{

    private String Usersession = "";

    public String getUsersession() {
        return Usersession;
    }

    public void setUsersession(String usersession) {
        Usersession = usersession;
    }

    @Override
    public Map<String,String> toMap(){
        Map<String,String> map = super.toMap();
        map.put("Usersession",this.Usersession);
        return map;
    }

}
