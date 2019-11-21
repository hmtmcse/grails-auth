package com.hmtmcse.gauth.jwt

class AuthenticationDataHolder {

    public static Boolean isEnableJWTAuth = false

    public static Map<String, Map> skipURL = [
            "apiAuthenticationV1": [
                    "postLogin": true,
                    "getLogout": true,
                    "postRenew": true,
            ],
            "swaggerUi": [:]
    ]

}
