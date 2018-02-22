package com.wujunshen.blockchain.controller;

import com.wujunshen.blockchain.entity.Block;
import com.wujunshen.blockchain.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wujunshen.blockchain.utils.Constants.*;
import static com.wujunshen.blockchain.utils.StringUtils.*;

/**
 * @author: frankwoo(吴峻申) <br>
 * @date: 2018/2/22 <br>
 * @time: 22:36 <br>
 * @mail: frank_wjs@hotmail.com <br>
 */
@RestController
@Slf4j
public class MineBlockController {

    @GetMapping(value = "/mine")
    public BaseResponse mine() {
        List<Block> blockChain = new ArrayList<>();

        log.info("\n====================start mining====================\n");
        Long startTime = new Date().getTime();
        Block firstBlock = new Block("I am the first block", INIT_HASH);
        mineBlock(firstBlock, DIFFICULTY);
        blockChain.add(firstBlock);
        Long endTime = new Date().getTime();
        log.info(DURATION_TIME, endTime - startTime);

        startTime = new Date().getTime();
        Block secondBlock = new Block("I am the second block", firstBlock.getHash());
        mineBlock(secondBlock, DIFFICULTY);
        blockChain.add(secondBlock);
        endTime = new Date().getTime();
        log.info(DURATION_TIME, endTime - startTime);

        startTime = new Date().getTime();
        Block thirdBlock = new Block("I am the third block", secondBlock.getHash());
        mineBlock(thirdBlock, DIFFICULTY);
        blockChain.add(thirdBlock);
        endTime = new Date().getTime();
        log.info(DURATION_TIME, endTime - startTime);
        log.info("\n====================end mining====================\n");

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(isValidChain(blockChain).toString());
        baseResponse.setData(blockChain);

        return baseResponse;
    }

    private Boolean isValidChain(List<Block> blockChain) {
        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!validateHash(currentBlock)) {
                log.info("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                log.info("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.getHash().substring(0, DIFFICULTY).
                    equals(getDifficultyString(DIFFICULTY))) {
                log.info("This block hasn't been mined");
                return false;
            }

        }
        return true;
    }

    //Increases nonce value until hash target is reached.
    private void mineBlock(Block block, int difficulty) {
        while (!block.getHash().substring(0, difficulty).
                equals(getDifficultyString(difficulty))) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(sha256(block.getPreviousHash() +
                    Long.toString(block.getTimeStamp()) +
                    Integer.toString(block.getNonce()) +
                    block.getData()));
        }
        log.info("\nblock is mined!!! : {}\n", block.getHash());
    }
}