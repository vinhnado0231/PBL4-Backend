package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IAccountRepository;
import com.example.backend.repository.IUserFavoriteRepository;
import com.example.backend.repository.IUserRepository;
import com.example.backend.service.IFriendService;
import com.example.backend.service.impl.AccountService;
import com.example.backend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class TestController2 {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUserFavoriteRepository userFavoriteRepository;
    @Autowired
    private IFriendService friendService;
    public static int numberFriendOfUser(int graphFriend[][],int idUser){ // trả về số lượng bạn bè của user
        int numFriend=0;
        for(int i=0;i<graphFriend.length;i++){
            if(graphFriend[idUser][i]==1) numFriend++;
        }
//        System.out.println("Numfriend Of User "+idUser+" is : "+numFriend);
        return numFriend;
    }
    public static int findNumberOfCommonFriend(int graphFriend[][],int idUser1,int idUser2){ // tìm số bạn chung giữa 2 user
        int numberCommonFriend=0;
        for(int i=0;i<graphFriend.length;i++){
            if(i== idUser1 && i== idUser2  ){
                continue;
            }else{
                if(graphFriend[idUser1][i]==1&&graphFriend[idUser2][i]==1){
                    numberCommonFriend++;
                }
            }
        }

        return numberCommonFriend;
    }
    public static  ArrayList<Integer> CommonFriend(int graphFriend[][],int idUser1,int idUser2){ // trả về danh sách bạn chung giữa 2 user
        ArrayList<Integer> listCommonFriend=new ArrayList<Integer>();

        for(int i=0;i<graphFriend.length;i++){
            if(i== idUser1 && i== idUser2  ){
                continue;
            }else{
                if(graphFriend[idUser1][i]==1&&graphFriend[idUser2][i]==1){
                    listCommonFriend.add(i);
                }
            }
        }
        return listCommonFriend;
    }
    public static HashMap<Integer, Integer> recomendList( int graphFriend[][],int idUser){ //HashMap<Integer,Integer>
        LinkedList<Integer> listBanChung= listCommondFiend( graphFriend, idUser); // trả về danh sách những thằng có bạn chung vs idUser
        // từ list bạn chung tim số bạn chung giữa idUser với từng user trong listBanChung
        HashMap<Integer,Integer> listNumberOfCommondFriend=new HashMap<Integer,Integer>();
        Iterator<Integer> i=listBanChung.iterator();
        while(i.hasNext())
        {
            int id= i.next();
            int numberCommonFriend= findNumberOfCommonFriend( graphFriend, idUser,id);
            listNumberOfCommondFriend.put(id,numberCommonFriend);
        }
//        System.out.println("LIST RECOMEND");
        Set<Integer> set = listNumberOfCommondFriend.keySet();
//        for (Integer key : set) {
//            System.out.println("User"+key + " : " + listNumberOfCommondFriend.get(key));
//        }
//        System.out.println(" SORT LIST RECOMEND");
//        LinkedHashMap<Integer,Integer> sortlist= sortHashMapByValues(listNumberOfCommondFriend);
        HashMap<Integer, Integer> sortlist=sortByValue(listNumberOfCommondFriend);
//        for (Map.Entry<Integer, Integer> mapElement : sortlist.entrySet()) {
//            Integer key = mapElement.getKey();
//            Integer value = mapElement.getValue();
//            System.out.println("User"+key + " : " + value);
//        }
        return sortlist;

    }
    public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer> > list
                = new LinkedList<Map.Entry<Integer, Integer> >(
                hm.entrySet());
        // Sort the list using lambda expression
        Collections.sort(
                list,
                (i1,i2) -> i2.getValue().compareTo(i1.getValue()));

        // put data from sorted list to hashmap
        HashMap<Integer, Integer> temp
                = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static LinkedList<Integer> listCommondFiend(int graphFriend[][],int idUser){ // trả về list bạn chung của một user
        // list này chứa những thằng có bạn chung với user A
//        System.out.println("Dong 25 , list ban chung");
        LinkedList<Integer> list= new LinkedList<Integer>();
        int id = idUser;
        for(int i=0;i<graphFriend.length;i++){
            if(graphFriend[id][i]==1){
                for(int j=0;j<graphFriend.length;j++){
                    if(graphFriend[i][j]==1 && j!=id && j!=i &&graphFriend[idUser][j]!=1){
                        list.add(j);
                        System.out.print(j+" ");
                    }
                }
            }
        }
        return list;
    }
    public static void InitGraph( int graphFriend[][],int numberOfUser){
        for(int i=0;i<=numberOfUser;i++){
            for(int j=0;j<=numberOfUser;j++){
                graphFriend[i][j]=0;
            }
        }

    }
    public static HashMap<Integer, Double> sortByValue2(HashMap<Integer, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double> > list =
                new LinkedList<Map.Entry<Integer, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double> >() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public static   HashMap<Integer, Double> recomendListAlgorithm2( int graphFriend[][],int idUser){
        HashMap<Integer, Integer> list= recomendList(graphFriend,idUser);// trả về danh sách recommend dựa trên số lượng bạn chung ( đã sort)
        HashMap<Integer, Double> listRecommendScore=new HashMap<>();
        for (Map.Entry<Integer, Integer> mapElement : list.entrySet()) {
            Integer key = mapElement.getKey();
            ArrayList<Integer> listBanChung=CommonFriend(graphFriend,idUser,key);// trả về danh sách bạn chung giữa 2 user
            double score=0;
            for(Integer id:listBanChung){
                score+=1.0/numberFriendOfUser(graphFriend,id);
            }
            listRecommendScore.put(key,score);
//            System.out.println("User"+key +" , Score = "+score);
        }
        listRecommendScore=sortByValue2(listRecommendScore);
        return listRecommendScore;

    }

    @GetMapping("/test2")
    public ResponseEntity<Object> checkUsername()  {
        LinkedList<UserDTO> users = userService.getAllUserDTO();
        int numberUser=users.size();
        int graphFriend[][]=new int[numberUser+1][numberUser+1];// mảng này chứa đồ thị xem giữa các user có phải là bạn bè hay không, lấy ra danh sách bạn của 1 user chỉ cần
        InitGraph(graphFriend,numberUser);
        for(int i=0;i<=numberUser;i++){
            for(int j=0;j<=i;j++){
                if(friendService.isFriend(i,j)){
                    graphFriend[i][j]=1;
                    graphFriend[j][i]=1;
                }
            }
        }
//        for(int i=0;i<=numberUser;i++){
//            for(int j=0;j<=numberUser;j++){
//                System.out.print( graphFriend[i][j]+" ");
//            }
//            System.out.println("");
//        }
        LinkedList<UserDTO> result=new LinkedList<>();
        HashMap<Integer, Double> listRecommendScore=recomendListAlgorithm2(graphFriend,2);// truyền id muốn recoomend vô đây
        for (Map.Entry<Integer, Double> mapElement : listRecommendScore.entrySet()) {
            Integer key = mapElement.getKey();
            result.add(users.get(key));
        }
        for(int i=0;i<result.size();i++){
            System.out.printf("ID : "+result.get(i).getIdUser()+", Ngay Sinh :"+result.get(i).getDateOfBirthUser());
        }
        return new ResponseEntity<>(friendService.isFriend(1,2),HttpStatus.OK);
    }

}
