package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.FriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FriendRecommendResult {
    @Autowired
    private RecommendHandler1 recommendHandler1;
    @Autowired
    private RecommendHandler2 recommendHandler2;

    public List<FriendDTO> getListRecommendFriend(int idUser) throws InterruptedException, ExecutionException {
        CompletableFuture<List<FriendDTO>> futureData1 = recommendHandler1.getData(idUser);
        CompletableFuture<List<FriendDTO>> futureData2 = recommendHandler2.getData(idUser);
        while (true) {
            if (futureData1.isDone() && futureData2.isDone()) {
                List<FriendDTO> list1 = futureData1.get();
                List<FriendDTO> list2 = futureData2.get();
                List<FriendDTO> result = list2.stream()
                        .filter(element -> list1.contains(element))
                        .collect(Collectors.toList());
                list1.removeAll(list2);
                list2.removeAll(list1);
                result.addAll(list1);
                result.addAll(list2);
                return result;
            }
        }
    }
}
