package com.wujunshen.blockchain.utils;

import lombok.Data;

/**
 * User:Administrator(吴峻申)
 * Date:2015-11-30
 * Time:11:12
 * Mail:frank_wjs@hotmail.com
 */
@Data
public class BaseResponse {
    private String message;
    private Object data;
}