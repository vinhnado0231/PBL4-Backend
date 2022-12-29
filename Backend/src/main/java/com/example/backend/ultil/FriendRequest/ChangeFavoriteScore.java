package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.FriendDTO;
import com.example.backend.model.UserFavorite;
import com.example.backend.service.IFriendService;
import com.example.backend.service.IUserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChangeFavoriteScore {
    @Autowired
    private IUserFavoriteService userFavoriteService;
    @Autowired
    private IFriendService friendService;

    public void changeFavoriteScore(long idUSer){
        UserFavorite user1= userFavoriteService.findUserFavoriteByIdUser(idUSer);
        ArrayList<FriendDTO> listFriend= friendService.getAllFriendByIdUser(idUSer);
        Float an_uong1=user1.getAn_uong();
        Float ca_nhac1=user1.getCa_nhac();
        Float choi_game1=user1.getChoi_game();
        Float code1=user1.getCode();
        Float coffee1=user1.getCoffee();
        Float doc_sach1=user1.getDoc_sach();
        Float du_lich1=user1.getDu_lich();
        Float nau_an1=user1.getNau_an();
        Float the_thao1=user1.getThe_thao();
        Float xem_phim1=user1.getXem_phim();
        float[] arrHobbiesUser={an_uong1,ca_nhac1,choi_game1,code1,coffee1,doc_sach1,du_lich1,nau_an1,the_thao1,xem_phim1};
        int[] arrCountHB={0,0,0,0,0,0,0,0,0,0};
        int indexhb1=0,indexhb2=0,indexhb3=0,indexhb4=0,indexhb5=0;
        int counthb1=0,counthb2=0,counthb3=0,counthb4=0,counthb5=0;
        int count=1;
        for (int m=0;m<arrHobbiesUser.length;m++){
            System.out.printf(arrHobbiesUser[m]+" ");
            if(arrHobbiesUser[m]!=2.5){
                if(count==1){
                    indexhb1=m;
                    count++;
                }else if(count==2){
                    indexhb2=m;
                    count++;
                }else if(count==3){
                    indexhb3=m;
                    count++;
                }else if(count==4){
                    indexhb4=m;
                    count++;
                }else if(count==5){
                    indexhb5=m;
                    count++;
                }
            }
        }
        for(int i=0;i<listFriend.size();i++){
            UserFavorite userFriend=userFavoriteService.findUserFavoriteByIdUser(listFriend.get(i).getIdFriend());
            Float an_uong=userFriend.getAn_uong();
            Float ca_nhac=userFriend.getCa_nhac();
            Float choi_game=userFriend.getChoi_game();
            Float code=userFriend.getCode();
            Float coffee=userFriend.getCoffee();
            Float doc_sach=userFriend.getDoc_sach();
            Float du_lich=userFriend.getDu_lich();
            Float nau_an=userFriend.getNau_an();
            Float the_thao=userFriend.getThe_thao();
            Float xem_phim=userFriend.getXem_phim();
            float[] arrHobbies={an_uong,ca_nhac,choi_game,code,coffee,doc_sach,du_lich,nau_an,the_thao,xem_phim};
            if(arrHobbies[indexhb1]!=2.5) {
                counthb1++;
            }
            if(arrHobbies[indexhb2]!=2.5) {
                counthb2++;
            }
            if(arrHobbies[indexhb3]!=2.5) {
                counthb3++;
            }
            if(arrHobbies[indexhb4]!=2.5) {
                counthb4++;
            }
            if(arrHobbies[indexhb5]!=2.5) {
                counthb5++;
            }
        }
        int[] arr={counthb1,counthb2,counthb3,counthb4,counthb5};
        System.out.println(counthb1+" "+counthb2+" "+ counthb3+" "+counthb4+" "+counthb5);
        int max=counthb1;
        int indexMax=0;
        Float value=5f;
        for(int h=0;h<arr.length;h++){
            if(arr[h]>max){
                max=arr[h];
                indexMax=h;

            }
        }
        System.out.println("Max0="+indexMax+" ValueMaxAfter = "+max);
        arr[indexMax]=-1000;
        max= arr[indexMax];
        System.out.println("Value1 ="+value+", ValueMaxBefore = ");

        if(indexMax==0){
            switch (indexhb1){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==1){
            switch (indexhb2){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==2){
            switch (indexhb3){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==3){
            switch (indexhb4){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==4){
            switch (indexhb5){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }

        for(int h=0;h<arr.length;h++){
            if(arr[h]>max){
                max=arr[h];
                indexMax=h;
            }
        }
        System.out.println("Max1="+indexMax+" ValueMax = "+max);
        arr[indexMax]=-1000;
        value=value-1;
        max= arr[indexMax];
        System.out.println("Value1 ="+value+", ValueMax = "+max);
        if(indexMax==0){
            switch (indexhb1){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==1){
            switch (indexhb2){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==2){
            switch (indexhb3){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==3){
            switch (indexhb4){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==4){
            switch (indexhb5){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }

        for(int h=0;h<arr.length;h++){
            if(arr[h]>max){
                max=arr[h];
                indexMax=h;


            }
        }
        System.out.println("Max2="+indexMax+" ValueMax = "+max);
        arr[indexMax]=-1000;
        value=value-1;
        max= arr[indexMax];
        System.out.println("Value2="+value+", ValueMax = "+max);
        if(indexMax==0){
            switch (indexhb1){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==1){
            switch (indexhb2){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==2){
            switch (indexhb3){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==3){
            switch (indexhb4){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==4){
            switch (indexhb5){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }

        for(int h=0;h<arr.length;h++){
            if(arr[h]>max){
                max=arr[h];
                indexMax=h;


            }
        }
        System.out.println("Max3="+indexMax+" ValueMax = "+max);
        arr[indexMax]=-1000;
        value=value-1;
        max= arr[indexMax];
        System.out.println("Value3="+value+", ValueMax = "+max);

        if(indexMax==0){
            switch (indexhb1){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==1){
            switch (indexhb2){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==2){
            switch (indexhb3){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==3){
            switch (indexhb4){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==4){
            switch (indexhb5){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }

        for(int h=0;h<arr.length;h++){
            if(arr[h]>=max){
                max=arr[h];
                indexMax=h;

            }
        }
        System.out.println("Max4="+indexMax+" ValueMax = "+max);
        arr[indexMax]=-1000;
        value=value-1;
        max= arr[indexMax];
        System.out.println("Value4="+value+", ValueMax = "+max);

        if(indexMax==0){
            switch (indexhb1){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==1){
            switch (indexhb2){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==2){
            switch (indexhb3){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==3){
            switch (indexhb4){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }else if(indexMax==4){
            switch (indexhb5){
                case 0:
                    user1.setAn_uong(value);
                    break;
                case 1:
                    user1.setCa_nhac(value);
                    break;
                case 2:
                    user1.setChoi_game(value);
                    break;
                case 3:
                    user1.setCode(value);
                    break;
                case 4:
                    user1.setCoffee(value);
                    break;
                case 5:
                    user1.setDoc_sach(value);
                    break;
                case 6:
                    user1.setDu_lich(value);
                    break;
                case 7:
                    user1.setNau_an(value);
                    break;
                case 8:
                    user1.setThe_thao(value);
                    break;
                case 9:
                    user1.setXem_phim(value);
                    break;
            }
        }
        userFavoriteService.SaveUserFavorite(user1);

    }
}
