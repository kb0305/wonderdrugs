package com.example.Wonderdrug.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class GetConnection {
    @JsonProperty("responseStatus")
    public String responseStatus;

    @JsonProperty("sessionId")
    public String sessionId;

    @JsonProperty("userId")
    public long userId;

    @JsonProperty("vaultIds")
    public List<Vault> vaultIds;

    @JsonProperty("vaultId")
    public int vaultId;
}
