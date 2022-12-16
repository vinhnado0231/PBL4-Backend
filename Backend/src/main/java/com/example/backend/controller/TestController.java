package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import com.example.backend.model.Account;
import com.example.backend.model.User;
import com.example.backend.model.UserFavorite;
import com.example.backend.repository.IAccountRepository;
import com.example.backend.repository.IUserFavoriteRepository;
import com.example.backend.repository.IUserRepository;
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
public class TestController {
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


    public static Vector<String> initListHobbies(Vector<String> listHB){
        listHB.add("Ăn uống");
        listHB.add("Ca hát");
        listHB.add("Chơi game");
        listHB.add("Code");
        listHB.add("Đi Coffee");
        listHB.add("Đọc sách");
        listHB.add("Du lịch");
        listHB.add("Học");
        listHB.add("Hội họa");
        listHB.add("Mua sắm");
        listHB.add("Nấu ăn");
        listHB.add("Ngủ");
        listHB.add("Động vật");
        listHB.add("Thể thao");
        listHB.add("Make up");
        listHB.add("Xem phim");
        return listHB;
    }
    public static double ratingUI(double userSimilarity[][],double normalizedMatrix[][],int numUser,int numItem,int idUser,int idItem){
        double rs=0.0;
        //Tìm các user đã rate item
        // chú ý thứ tự user thay đổi khi ta xóa bớt 1 user
        // nên phải lấy id
        int k=10;//lấy k giá trị lớn nhất
        Vector<Integer> listUserRated=new Vector<>();
        Vector<Double> listValueSimilarity=new Vector<>();
        HashMap<Integer,Double> listUserAndValue=new HashMap<Integer,Double>();
        for(int id=0;id<numUser;id++){
            if(normalizedMatrix[idItem][id]!=0){
//                System.out.print(normalizedMatrix[idItem][id]+" ");
//                listUserRated.add(id);
                listUserAndValue.put(id,userSimilarity[idUser][id]);
            }
        }


        System.out.println("========");
        HashMap<Integer, Double> sortlist=sortByValue(listUserAndValue);
        for (Map.Entry<Integer, Double> mapElement : sortlist.entrySet()) {
            Integer key = mapElement.getKey();
            Double value = mapElement.getValue();
            System.out.println("User"+key + " : " + value);
        }
        // slove
        int dem=0;
        double tuso=0.0;
        double mauso=0.0;
        for (Map.Entry<Integer, Double> mapElement : sortlist.entrySet()) {
            if(dem>=k) break;
            Integer key = mapElement.getKey();
            Double value = mapElement.getValue();
            tuso+=value*normalizedMatrix[idItem][mapElement.getKey()];
            //*normalizedMatrix[idItem][mapElement.getKey()]
            mauso+=Math.abs(value);
            dem++;
        }
        rs=tuso/mauso;
        System.out.println("Rs = "+rs);

        // vì lấy k giá trị nên chạy tới k thôi ,
//        for(int j=0;j<listUserRated.size();j++){
//            listValueSimilarity.add(userSimilarity[idUser][listUserRated.get(j)]);
//        }
//
//        Collections.sort(listValueSimilarity, Collections.reverseOrder()); // sort list value, lấy k giá trị lớn nhất
//

        return rs;
    }

    public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }
        if(Math.sqrt(normA) * Math.sqrt(normB)==0) return 0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm)
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
    public static void userSimilarity(double normalizedMatrix[][],int row,int col,double returnMatrix[][]){
        // returnMatrix là User Similarity Matrix, row= col = số user
        double[] vectorA= new double[row];
        double[] vectorB=new double[row];
        for(int i=0;i<col;i++){
            for(int j=0;j<col;j++){
                for(int k=0;k<row;k++){
                    vectorA[k]=normalizedMatrix[k][i];
                    vectorB[k]=normalizedMatrix[k][j];
                }
                double similarity=cosineSimilarity(vectorA,vectorB);
                returnMatrix[i][j]=similarity;
            }
        }
    }
    public static void initOriginalMatrix(double originalMatrix[][],int row,int col,LinkedList<UserDTO> listUser){
        //row : number of hobbie
        //colum : number user
        for(int i=0;i<row+1;i++){
            for (int j=0;j<col+1;j++){
                originalMatrix[i][j]=2.5;
            }
        }
        for (int i =0;i<listUser.size();i++) {
            originalMatrix[0][i]=listUser.get(i).getAn_uong();
            originalMatrix[1][i]=listUser.get(i).getCa_nhac();
            originalMatrix[2][i]=listUser.get(i).getChoi_game();
            originalMatrix[3][i]=listUser.get(i).getCode();
            originalMatrix[4][i]=listUser.get(i).getCoffee();
            originalMatrix[5][i]=listUser.get(i).getDoc_sach();
            originalMatrix[6][i]=listUser.get(i).getDu_lich();
            originalMatrix[7][i]=listUser.get(i).getHoc();
            originalMatrix[8][i]=listUser.get(i).getHoi_hoa();
            originalMatrix[10][i]=listUser.get(i).getMua_sam();
            originalMatrix[11][i]=listUser.get(i).getNau_an();
            originalMatrix[12][i]=listUser.get(i).getNgu();
            originalMatrix[13][i]=listUser.get(i).getNuoi_thu();
            originalMatrix[14][i]=listUser.get(i).getThe_thao();
            originalMatrix[15][i]=listUser.get(i).getTrang_diem();
            originalMatrix[16][i]=listUser.get(i).getXem_phim();
        }

    }
    public static void normalizedMatrix(double matrix[][],int row,int col){
        int sumArr[]= new int[col];
        int count[]= new int[col];
        for(int m=0;m<col;m++) count[m]=0;
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if(matrix[i][j]!=2.5){
                    sumArr[j]+=matrix[i][j];
                    count[j]++;
                }
            }
        }
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                if( matrix[i][j]!=2.5){
                    double tb=sumArr[j]*1.0/count[j];
                    matrix[i][j]-=tb;
                }else{
                    matrix[i][j]=0;
                }
            }
        }
//        for(int i=0;i<row;i++){
//            for (int j=0;j<col;j++){
//                System.out.print(matrix[i][j]+" ");
//            }
//            System.out.println("");
//        }


    }
    public  static HashSet<User> initListUser(HashSet<User> users){

        return users;
    }


    public static void mainTest(){

    }
    @GetMapping("/test")
    public ResponseEntity<Object> checkUsername()  {
//        LinkedList<UserDTO> users1 = userService.getAllUserDTO();
//        LinkedList<UserDTO> users=new LinkedList<>();
//        for(int i=0;i<50;i++){
//            users.add(users1.get(i));
//        }
//        Vector<String> hobbies =new Vector<>();
//        hobbies = initListHobbies(hobbies);
//        double originalMatrix[][] = new double[hobbies.size()+1][users.size()+1]; // ma trận ban đầu
//        double normalizedMatrix[][] = new double[hobbies.size()+1][users.size()+1]; // ma trận sau khi chuẩn hóa
//        double userSimilarityMatrix[][]=new double[users.size()+1][users.size()+1];// ma trận thể hiện sự giống nhau giữa các user
//        int numHobbies=hobbies.size();
//        int numUser=users.size();
//        initOriginalMatrix(originalMatrix,numHobbies,numUser,users);
//        for(int i=0;i<numHobbies;i++){
//            for(int j=0;j<numUser;j++){
//                normalizedMatrix[i][j]=originalMatrix[i][j];
//            }
//        }
//        normalizedMatrix(normalizedMatrix,numHobbies,numUser);
//        for(int i=0;i<numHobbies;i++){
//            for(int j=0;j<numUser;j++){
//                System.out.print(normalizedMatrix[i][j]+" ");
//            }
//            System.out.println(" ");
//        }
//        System.out.println("USER SIMILARITY");
//        userSimilarity(normalizedMatrix,numHobbies,numUser,userSimilarityMatrix);
//        for(int i=0;i<numUser;i++){
//            for(int j=0;j<numUser;j++){
//                System.out.print(userSimilarityMatrix[i][j]+ " ");
//            }
//            System.out.println("");
//        }
////        for (int i=0;i<listSoThich.size();i++ ) {
////            System.out.println(listSoThich.get(i));
////        }
//        System.out.println("TEST");
//        ratingUI(userSimilarityMatrix,normalizedMatrix,numUser,numHobbies,0,0);






        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/test1")
    public ResponseEntity<Object> Ok() throws FileNotFoundException  {
        String url = "E:\\data.txt";
        // Đọc dữ liệu từ File với Scanner
        FileInputStream fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);
        try {
            while (scanner.hasNextLine()) {
                String line =scanner.nextLine();
                String[] words = line.split("/");
                User user = new User();
                user.setNameUser(words[0]);
                user.setDateOfBirthUser(words[1]);
                user.setAddressUser(words[2]);
                user.setHomeTownUser(words[3]);
                user.setGenderUser(Boolean.parseBoolean(words[4]));
                UserFavorite userFavorite = new UserFavorite();

            }
        } finally {
            try {
                scanner.close();
                fileInputStream.close();
            } catch (IOException ex) {

            }
        }
        for(int i=0;i<100;i++){

//
//            userFavorite.setAn_uong(5.0f);
//            userFavorite.setCode();
//            userFavorite.setAn_uong();
//            userFavorite.setXem_phim();
//            userFavorite.setDoc_sach();
//            userFavorite.setThe_thao();
//            userFavorite.setCa_nhac();
//            userFavorite.setDu_lich();
//            userFavorite.setCoffee();
//            userFavorite.setChoi_game();
//            userFavorite.setHoi_hoa();
//            userFavorite.setHoc();
//            userFavorite.setNgu();
//            userFavorite.setMua_sam();
//            userFavorite.setNuoi_thu();
//            userFavorite.setMua_sam());
//            userFavorite.setNau_an();
//
//            user.setUserFavorite(userFavorite);
//            userRepository.save(user);
        }



        return new ResponseEntity<>(HttpStatus.OK);
    }
}
