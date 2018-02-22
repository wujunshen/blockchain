package com.wujunshen.blockchain.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static com.wujunshen.blockchain.utils.StringUtils.sha256;

@Data
@Slf4j
public class Block {
    private String hash;
    private String previousHash;
    private String data; //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    //Block Constructor.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        //Making sure we do this after we set the other values.
        this.hash = sha256(previousHash +
                Long.toString(timeStamp) + Integer.toString(nonce) + data);
    }
}