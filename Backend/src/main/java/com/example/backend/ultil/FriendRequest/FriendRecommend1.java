package com.example.backend.ultil.FriendRequest;

import com.example.backend.dto.UserDTO;
import com.example.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@EnableAsync
public class FriendRecommend1 {
    public static double[][] userSimilarityMatrix;
    public static LinkedList<UserDTO> users = new LinkedList<>();
    @Autowired
    private IUserService userService;

    public static Vector<String> initListHobbies(Vector<String> listHB) {
        listHB.add("Ăn uống");
        listHB.add("Ca nhạc");
        listHB.add("Chơi game");
        listHB.add("Code");
        listHB.add("Coffee");
        listHB.add("Đọc sách");
        listHB.add("Du lịch");
        listHB.add("Nấu ăn");
        listHB.add("Thể thao");
        listHB.add("Xem phim");

        return listHB;
    }

    public static double ratingUI(double[][] userSimilarity, double[][] normalizedMatrix, int numUser, int idUser, int idItem) {
        double rs;
        //Tìm các user đã rate item
        // chú ý thứ tự user thay đổi khi ta xóa bớt 1 user
        // nên phải lấy id
        int k = 10;//lấy k giá trị lớn nhất
        HashMap<Integer, Double> listUserAndValue = new HashMap<>();
        for (int id = 0; id < numUser; id++) {
            if (normalizedMatrix[idItem][id] != 0) {
                listUserAndValue.put(id, userSimilarity[idUser][id]);
            }
        }
        HashMap<Integer, Double> sortlist = sortByValue(listUserAndValue);
        for (Map.Entry<Integer, Double> mapElement : sortlist.entrySet()) {
            Integer key = mapElement.getKey();
            Double value = mapElement.getValue();
        }
        // slove
        int dem = 0;
        double tuso = 0.0;
        double mauso = 0.0;
        for (Map.Entry<Integer, Double> mapElement : sortlist.entrySet()) {
            if (dem >= k) break;
            Integer key = mapElement.getKey();
            Double value = mapElement.getValue();
            tuso += value * normalizedMatrix[idItem][mapElement.getKey()];
            mauso += Math.abs(value);
            dem++;
        }
        if (mauso == 0) return 0;
        rs = tuso / mauso;
        return rs;
    }

    public static void fillNormalizedMatrixFull(double[][] userSimilarity, double[][] normalizedMatrix, int numUser, int numItem) {
        for (int i = 0; i < numItem; i++) {
            for (int j = 0; j < numUser; j++) {
                if (normalizedMatrix[i][j] == 0) {
                    double rs = ratingUI(userSimilarity, normalizedMatrix, numUser, j, i);
//                    System.out.println("Du doan ["+i+"]["+j+"] = "+rs);
                    normalizedMatrix[i][j] = rs;
                }
            }
        }
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
        if (Math.sqrt(normA) * Math.sqrt(normB) == 0) return 0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Double>> list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        // put data from sorted list to hashmap
        HashMap<Integer, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void userSimilarity(double[][] normalizedMatrix, int row, int col, double[][] returnMatrix) {
        // returnMatrix là User Similarity Matrix, row= col = số user
        double[] vectorA = new double[row];
        double[] vectorB = new double[row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < row; k++) {
                    vectorA[k] = normalizedMatrix[k][i];
                    vectorB[k] = normalizedMatrix[k][j];
                }
                double similarity = cosineSimilarity(vectorA, vectorB);
                returnMatrix[i][j] = similarity;
            }
        }
    }

    public static void initOriginalMatrix(double[][] originalMatrix, int row, int col, LinkedList<UserDTO> listUser) {
        //row : number of hobbie
        //colum : number user
        for (int i = 0; i < row + 1; i++) {
            for (int j = 0; j < col + 1; j++) {
                originalMatrix[i][j] = 2.5;
            }
        }
        for (int i = 0; i < listUser.size(); i++) {
            originalMatrix[0][i] = listUser.get(i).getAn_uong();
            originalMatrix[1][i] = listUser.get(i).getCa_nhac();
            originalMatrix[2][i] = listUser.get(i).getChoi_game();
            originalMatrix[3][i] = listUser.get(i).getCode();
            originalMatrix[4][i] = listUser.get(i).getCoffee();
            originalMatrix[5][i] = listUser.get(i).getDoc_sach();
            originalMatrix[6][i] = listUser.get(i).getDu_lich();
            originalMatrix[7][i] = listUser.get(i).getNau_an();
            originalMatrix[8][i] = listUser.get(i).getThe_thao();
            originalMatrix[9][i] = listUser.get(i).getXem_phim();
        }

    }

    public static void normalizedMatrix(double[][] matrix, int row, int col) {
        int[] sumArr = new int[col];
        int[] count = new int[col];
        for (int m = 0; m < col; m++) count[m] = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != 2.5) {
                    sumArr[j] += matrix[i][j];
                    count[j]++;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] != 2.5) {
                    double tb = sumArr[j] * 1.0 / count[j];
                    matrix[i][j] -= tb;
                } else {
                    matrix[i][j] = 0;
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

    public static LinkedList<UserDTO> getListRecommendByIdUser(double[][] userSimilarityMatrix, LinkedList<UserDTO> listUser, int idUser) {
        LinkedList<UserDTO> list = new LinkedList<>();
        HashMap<Integer, Double> listUserAndValue = new HashMap<>();
        for (int id = 0; id < listUser.size(); id++) {
            listUserAndValue.put(id, userSimilarityMatrix[idUser - 1][id]);
        }
        HashMap<Integer, Double> sortlist = sortByValue(listUserAndValue);
        for (Map.Entry<Integer, Double> mapElement : sortlist.entrySet()) {
            Integer key = mapElement.getKey();
            Double value = mapElement.getValue();
            if (value > 0) {
                list.add(listUser.get(key));
            }
        }
        return list;
    }

    public static LinkedList<UserDTO> getListRecommendAfterFilter(int idUser) {
        String namsinh = "";
        for (UserDTO user : users) {
            if (user.getIdUser() == idUser) {
                namsinh = user.getDateOfBirthUser();
                break;
            }
        }
        String[] year=namsinh.split("-");
        int DateOfBirthUser = Integer.parseInt(year[0].trim());
        LinkedList<UserDTO> list = getListRecommendByIdUser(userSimilarityMatrix, users, idUser);
        LinkedList<UserDTO> listFilter = new LinkedList<>();
        for (UserDTO userDTO : list) {
            String[] year1=userDTO.getDateOfBirthUser().split("-");
            int DateOfBirth = Integer.parseInt(year1[0].trim());
            if (Math.abs(DateOfBirth - DateOfBirthUser) < 20) {
                listFilter.add(userDTO);
            }
        }
        for (UserDTO userDTO : listFilter) {
            if(userDTO.getIdUser()==idUser){
                listFilter.remove(userDTO);
                break;
            }
        }
        return listFilter;
    }

    @Async
    @Scheduled(fixedRate = 1000 * 30)
    public void FriendRequestTest() {
        users = userService.getAllUserDTO();// get all user from database
        Vector<String> hobbies = new Vector<>();
        hobbies = initListHobbies(hobbies); // init list hobbies for user
        double[][] originalMatrix = new double[hobbies.size() + 1][users.size() + 1]; // ma trận ban đầu
        double[][] normalizedMatrix = new double[hobbies.size() + 1][users.size() + 1]; // ma trận sau khi chuẩn hóa
        userSimilarityMatrix = new double[users.size() + 1][users.size() + 1];// ma trận thể hiện sự giống nhau giữa các user
        int numHobbies = hobbies.size();
        int numUser = users.size();
        initOriginalMatrix(originalMatrix, numHobbies, numUser, users);
        for (int i = 0; i < numHobbies; i++) {
            System.arraycopy(originalMatrix[i], 0, normalizedMatrix[i], 0, numUser);
        }
        normalizedMatrix(normalizedMatrix, numHobbies, numUser);
        userSimilarity(normalizedMatrix, numHobbies, numUser, userSimilarityMatrix);
        fillNormalizedMatrixFull(userSimilarityMatrix, normalizedMatrix, numUser, numHobbies);
        userSimilarity(normalizedMatrix, numHobbies, numUser, userSimilarityMatrix);
    }
}
