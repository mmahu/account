package com.mmahu.account.config;

import java.time.Instant;

public class JwtDto {
    private String sub;
    private String scp;
    private Instant iat;
    private Instant exp;

    public String getSub() {
        return sub;
    }

    public JwtDto setSub(String sub) {
        this.sub = sub;
        return this;
    }

    public String getScp() {
        return scp;
    }

    public JwtDto setScp(String scp) {
        this.scp = scp;
        return this;
    }

    public Instant getIat() {
        return iat;
    }

    public JwtDto setIat(Instant iat) {
        this.iat = iat;
        return this;
    }

    public Instant getExp() {
        return exp;
    }

    public JwtDto setExp(Instant exp) {
        this.exp = exp;
        return this;
    }
}
