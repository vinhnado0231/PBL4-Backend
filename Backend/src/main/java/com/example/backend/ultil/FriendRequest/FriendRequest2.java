package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.UserDTO;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@EnableAsync
public class FriendRequest2 {
    @Autowired
    private IUserService userService;
    @Autowired
    private IFriendService friendService;
    public static int[][] graphFriend;
    public static LinkedList<UserDTO> users = new LinkedList<>();
    public static int numberFriendOfUser(int[][] graphFriend, int idUser) { // trả về số lượng bạn bè của user
        int numFriend = 0;
        for (int i = 0; i < graphFriend.length; i++) {
            if (graphFriend[idUser][i] == 1) numFriend++;
        }
//        System.out.println("Numfriend Of User "+idUser+" is : "+numFriend);
        return numFriend;
    }

    public static int findNumberOfCommonFriend(int[][] graphFriend, int idUser1, int idUser2) { // tìm số bạn chung giữa 2 user
        int numberCommonFriend = 0;
        for (int i = 0; i < graphFriend.length; i++) {
            if (i != idUser1 || i != idUser2) {
                if (graphFriend[idUser1][i] == 1 && graphFriend[idUser2][i] == 1) {
                    numberCommonFriend++;
                }
            }
        }

        return numberCommonFriend;
    }

    public static ArrayList<Integer> CommonFriend(int[][] graphFriend, int idUser1, int idUser2) { // trả về danh sách bạn chung giữa 2 user
        ArrayList<Integer> listCommonFriend = new ArrayList<>();

        for (int i = 0; i < graphFriend.length; i++) {
            if (i == idUser1 && i == idUser2) {
                continue;
            } else {
                if (graphFriend[idUser1][i] == 1 && graphFriend[idUser2][i] == 1) {
                    listCommonFriend.add(i);
                }
            }
        }
        return listCommonFriend;
    }

    public static HashMap<Integer, Integer> recomendList(int[][] graphFriend, int idUser) { //HashMap<Integer,Integer>
        LinkedList<Integer> listBanChung = listCommondFiend(graphFriend, idUser); // trả về danh sách những thằng có bạn chung vs idUser
        // từ list bạn chung tim số bạn chung giữa idUser với từng user trong listBanChung
        HashMap<Integer, Integer> listNumberOfCommondFriend = new HashMap<Integer, Integer>();
        Iterator<Integer> i = listBanChung.iterator();
        while (i.hasNext()) {
            int id = i.next();
            int numberCommonFriend = findNumberOfCommonFriend(graphFriend, idUser, id);
            listNumberOfCommondFriend.put(id, numberCommonFriend);
        }
        Set<Integer> set = listNumberOfCommondFriend.keySet();
        return sortByValue(listNumberOfCommondFriend);

    }

    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer>> list
                = new LinkedList<Map.Entry<Integer, Integer>>(
                hm.entrySet());
        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i1, i2) -> i2.getValue().compareTo(i1.getValue()));

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp
                = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static LinkedList<Integer> listCommondFiend(int graphFriend[][], int idUser) { // trả về list bạn chung của một user
        // list này chứa những thằng có bạn chung với user A
//        System.out.println("Dong 25 , list ban chung");
        LinkedList<Integer> list = new LinkedList<Integer>();
        int id = idUser;
        for (int i = 0; i < graphFriend.length; i++) {
            if (graphFriend[id][i] == 1) {
                for (int j = 0; j < graphFriend.length; j++) {
                    if (graphFriend[i][j] == 1 && j != id && j != i && graphFriend[idUser][j] != 1) {
                        list.add(j);
                    }
                }
            }
        }
        return list;
    }

    public static void InitGraph(int graphFriend[][], int numberOfUser) {
        for (int i = 0; i <= numberOfUser; i++) {
            for (int j = 0; j <= numberOfUser; j++) {
                graphFriend[i][j] = 0;
            }
        }
    }

    public static HashMap<Integer, Double> sortByValue2(HashMap<Integer, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list =
                new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static HashMap<Integer, Double> recomendListAlgorithm2(int graphFriend[][], int idUser) {
        HashMap<Integer, Integer> list = recomendList(graphFriend, idUser);// trả về danh sách recommend dựa trên số lượng bạn chung ( đã sort)
        HashMap<Integer, Double> listRecommendScore = new HashMap<>();
        for (Map.Entry<Integer, Integer> mapElement : list.entrySet()) {
            Integer key = mapElement.getKey();
            ArrayList<Integer> listBanChung = CommonFriend(graphFriend, idUser, key);// trả về danh sách bạn chung giữa 2 user
            double score = 0;
            for (Integer id : listBanChung) {
                score += 1.0 / numberFriendOfUser(graphFriend, id);
            }
            listRecommendScore.put(key, score);
        }
        listRecommendScore = sortByValue2(listRecommendScore);
        return listRecommendScore;

    }

    public static LinkedList<UserDTO> ListRecommend(int idUser){
        LinkedList<UserDTO> result = new LinkedList<>();
        HashMap<Integer, Double> listRecommendScore = recomendListAlgorithm2(graphFriend, idUser);// truyền id muốn recoomend vô đây
        for (Map.Entry<Integer, Double> mapElement : listRecommendScore.entrySet()) {
            Integer key = mapElement.getKey();
            result.add(users.get(key));
        }
        return  result;
    }

    @Async
    @Scheduled(fixedRate = 1000 * 30)
    public void checkUsername() {
        System.out.println("Vao roi nay Request2");
        users = userService.getAllUserDTO();
        int numberUser = users.size();
        graphFriend= new int[numberUser + 1][numberUser + 1];// mảng này chứa đồ thị xem giữa các user có phải là bạn bè hay không, lấy ra danh sách bạn của 1 user chỉ cần
        InitGraph(graphFriend, numberUser);
        for (int i = 0; i <= numberUser; i++) {
            for (int j = 0; j <= i; j++) {
                if (friendService.isFriend(i, j)) {
                    graphFriend[i][j] = 1;
                    graphFriend[j][i] = 1;
                }
            }
        }
        LinkedList<UserDTO> result = new LinkedList<>();
        HashMap<Integer, Double> listRecommendScore = recomendListAlgorithm2(graphFriend, 2);// truyền id muốn recoomend vô đây
        for (Map.Entry<Integer, Double> mapElement : listRecommendScore.entrySet()) {
            Integer key = mapElement.getKey();
            result.add(users.get(key));
        }
    }

}
