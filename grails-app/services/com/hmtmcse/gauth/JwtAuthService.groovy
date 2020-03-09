package com.hmtmcse.gauth

import com.hmtmcse.caa.jwt.JavaJWT
import com.hmtmcse.common.AuthSetting
import com.hmtmcse.gauth.jwt.JavaJWTData
import com.hmtmcse.gcommon.TMDateTimeUtil
import com.hmtmcse.gcommon.TMGUtil

import javax.servlet.http.HttpServletRequest


class JwtAuthService {

    AuthSettingService authSettingService

    def getToken(String issuer, JavaJWTData javaJWTData) {
        JavaJWT javaJWT = JavaJWT.hmackInstance(javaJWTData.algorithm, javaJWTData.secret).tokenValidUntilUTCMinutes(javaJWTData.expireInMinutes)
        if (javaJWTData.claims) {
            javaJWTData.claims.each { String key, def value ->
                javaJWT.privateClaims(key, value)
            }
        }
        return javaJWT.token(issuer)
    }


    def isValidToken(String token, JavaJWTData javaJWTData) {
        JavaJWT javaJWT = JavaJWT.hmackInstance(javaJWTData.algorithm, javaJWTData.secret)
        try {
            javaJWT.tokenValidate(token)
            return true
        } catch (Exception e) {
            return false
        }
    }


    def isAuthenticated(HttpServletRequest request, JavaJWTData javaJWTData) {
        String jwtToken = request.getParameter("jwtToken")
        if (jwtToken) {
            return isValidToken(jwtToken, javaJWTData)
        }

        String authorizationHeader = request.getHeader("Authorization")
        if (!authorizationHeader || !authorizationHeader.startsWith("Bearer")) {
            return false
        }
        String[] splittedBearer = authorizationHeader.split("Bearer")
        if (splittedBearer.length < 2) {
            return false
        }
        return isValidToken(splittedBearer[1].trim(), javaJWTData)
    }

    def isAuthenticated(HttpServletRequest request) {
        JavaJWTData javaJWTData = getJavaJWTData()
        return isAuthenticated(request, javaJWTData)
    }

    def getJavaJWTData() {
        AuthSetting settings = authSettingService.getSetting(AuthConstant.JWT_SETTING_GROUP, AuthConstant.JWT_KEY)
        JavaJWTData javaJWTData = new JavaJWTData();
        if (settings && settings.dataValue) {
            javaJWTData.secret = settings.dataValue
        }
        return javaJWTData
    }


    Map jwtTokenGen(User user) {
        Map response = [:]
        TokenHolder tokenHolder = new TokenHolder(timeMillis: TMDateTimeUtil.currentMillisecond())
        tokenHolder.token = TMGUtil.uuid()
        tokenHolder.user = user
        response.refreshToken = tokenHolder.token
        tokenHolder.save()
        JavaJWTData javaJWTData = getJavaJWTData()
        response.accessToken = getToken("${user.firstName} ${user.lastName ?: ""}", javaJWTData.addClaim("id", user.uuid))
        return response
    }

}
