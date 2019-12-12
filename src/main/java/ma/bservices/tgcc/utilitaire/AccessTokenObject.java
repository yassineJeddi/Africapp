/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.utilitaire;

/**
 *
 * @author airaamane
 */
/*
 * Class that corresponds to the JSON
 * returned by google OAuth2 token generator
 */


import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenObject {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public int getExpiresIn() { return expiresIn; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setExpiresIn(int expiresIn) { this.expiresIn = expiresIn; }
}