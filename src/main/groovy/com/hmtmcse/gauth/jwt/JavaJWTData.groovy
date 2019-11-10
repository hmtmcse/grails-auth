package com.hmtmcse.gauth.jwt

import com.hmtmcse.caa.jwt.JavaJWT

class JavaJWTData {
    public Integer expireInMinutes = 30
    public Map claims = [:]
    public String secret = "Th1sIsjWtTokenSecRet123@"
    public JavaJWT.ALGORITHM algorithm = JavaJWT.ALGORITHM.HMAC256

    public JavaJWTData addClaim(String key, String value){
        claims.put(key, value)
        return this
    }

}
