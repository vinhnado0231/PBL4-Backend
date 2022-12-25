package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.FriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FriendRecommendResult {
    @Autowired
    private RecommendHandler1 recommendHandler1;
    @Autowired
    private RecommendHandler2 recommendHandler2;

    public List<FriendDTO> getListRecommendFriend(int idUser) throws InterruptedException, ExecutionException {
        CompletableFuture<List<FriendDTO>> futureData1 = recommendHandler1.getData(idUser);
        CompletableFuture<List<FriendDTO>> futureData2 = recommendHandler2.getData(idUser);
        List<FriendDTO> result = new LinkedList<>();
        while (true){
            if(futureData1.isDone() && futureData2.isDone()){
                return  futureData1.get();
            }
            Thread.sleep(50);
        }
    }
}
