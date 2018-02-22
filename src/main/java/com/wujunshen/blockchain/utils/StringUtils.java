package com.wujunshen.blockchain.utils;

import com.wujunshen.blockchain.entity.Block;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.wujunshen.blockchain.utils.Constants.*;

/**
 * @author: frankwoo(吴峻申) <br>
 * @date: 2018/2/22 <br>
 * @time: 22:17 <br>
 * @mail: frank_wjs@hotmail.com <br>
 */
@Slf4j
public class StringUtils {
    private StringUtils() {
        //no instance
    }

    //Applies Sha256 to a string and returns the result.
    public static String sha256(String input) {
        byte[] hash = new byte[0];
        try {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);

            //Applies sha256 to our input,
            hash = digest.digest(input.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
        }

        StringBuilder hexString = new StringBuilder(); // This will contain hash as hexidecimal
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append(ZERO);
            hexString.append(hex);
        }
        return hexString.toString();
    }

    //Returns difficulty string target, to compare to hash. eg difficulty of 5 will return "00000"
    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace(SLASH_ZERO, ZERO);
    }

    //Calculate new hash based on blocks contents
    public static boolean validateHash(Block block) {
        String hash = sha256(block.getPreviousHash() +
                Long.toString(block.getTimeStamp()) +
                Integer.toString(block.getNonce()) +
                block.getData());

        return block.getHash().equals(hash);
    }
}