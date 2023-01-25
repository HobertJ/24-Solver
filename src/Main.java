import java.util.*;
import java.io.*;

public class Main{
    private static InputStreamReader streamReader = new InputStreamReader(System.in);
    private static BufferedReader bufferedReader = new BufferedReader(Main.streamReader);

    public static int converter(String x){
        int num;
        char y = x.charAt(0);
        int index = y;
        if(index == 65){        // A
            num = 1;
        }else if(index == 74){  // J
            num = 11;
        }else if(index== 81){   // Q
            num = 12;
        }else if(index == 75){  // K
            num = 13;
        }else{
            num = Integer.parseInt(x);
        }
        return num;
    }

    public static boolean validator(String y){
        boolean hasil = false;
        char x = y.charAt(0);
        int index = x;
        if(index == 65){        // A
            hasil = true;
        }else if(index == 74){  // J
            hasil = true;
        }else if(index == 81){  // Q
            hasil = true;
        }else if(index == 75){  // K
            hasil = true;
        }else{
            try{
                if(Integer.parseInt(y) > 0 && Integer.parseInt(y) < 14){
                    hasil = true;
                }
            }catch(NumberFormatException ex){
                System.out.println("Salah masukan");
            }
            
        }
        return hasil;
    }

    public static int[] generateRandom(){
        Random rand = new Random();
        int num1 = rand.nextInt((13-1)+1)+1;
        int num2 = rand.nextInt((13-1)+1)+1;
        int num3 = rand.nextInt((13-1)+1)+1;
        int num4 = rand.nextInt((13-1)+1)+1;
        int[] result = {num1, num2, num3, num4};
        return result;
    }

    public static void showGeneratedCard(int[] num){
        System.out.print("Kartu random: ");
        for(int i:num){
            if(i == 13){
                System.out.print("K");
            }else if(i == 12){
                System.out.print("Q");
            }else if(i == 11){
                System.out.print("J");
            }else if(i == 1){
                System.out.print("A");
            }else{
                System.out.print(i);
            }
            System.out.print(" ");
        }
        System.out.println();
        
    }

    public static int[] getFromUser() {
        boolean inputValid = false; 
        System.out.print("Masukkan 4 kartu: ");
        String input = "test";
        try{
            input = bufferedReader.readLine();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Ada kesalahan dalam masukan");
        }
        String[] hasil = input.split(" ");
        int len = hasil.length;
        if(len == 4){
            inputValid = validator(hasil[0]) && validator(hasil[1]) && validator(hasil[2]) && validator(hasil[3]);
        }

        while(!inputValid){
            System.out.println("Masukan Anda salah!");
            System.out.print("Silakan Masukkan kembali 4 kartu: ");
            try{
                input = bufferedReader.readLine();
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Ada kesalahan dalam masukan");
            }
            hasil = input.split(" ");
            len = hasil.length;
            if(len == 4){
                inputValid = validator(hasil[0]) && validator(hasil[1]) && validator(hasil[2]) && validator(hasil[3]);
            }
        }

        int num1 = converter(hasil[0]);
        int num2 = converter(hasil[1]);
        int num3 = converter(hasil[2]);
        int num4 = converter(hasil[3]);

        int[] result = {num1, num2, num3, num4};
        return result;
}

    public static double operate(double num1, double num2, String op){
        double result = 0;
        if(op == "+"){
            result = num1 + num2;
        }else if(op == "-"){
            result = num1 - num2;
        }else if(op == "*"){
            result = num1 * num2;
        }else if(op == "/"){
            result = num1 / num2;
        }
        return result;
    }

    public static void solve(int[] num) throws IOException{

        long begin = System.currentTimeMillis();
        List<String> solution=new ArrayList<String>(); 
        int goal = 24;
        int count_solution = 0;
        String[] op = {"+", "-", "*", "/"};
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(j != i){
                    for(int k = 0; k < 4; k++){
                        if(k != i && k != j){
                            for(int l = 0; l < 4; l++){
                                if(l != i && l != j && l != k){
                                    
                                    for(int m = 0; m < 4; m++){
                                        for(int n = 0; n < 4; n++){
                                            for(int o = 0; o < 4; o++){

                                                double a = num[i];
                                                double b = num[j];
                                                double c = num[k];
                                                double d = num[l];

                                                String op1 = op[m];
                                                String op2 = op[n];
                                                String op3 = op[o];
                                                
                                                String sol;
                                                // variasi 1 : ((A op B) op C) op D
                                                if(operate(operate(operate(a,b,op1),c,op2),d,op3) == goal){
                                                    count_solution++;
                                                    sol = String.format("((%d %s %d) %s %d) %s %d",num[i],op1,num[j],op2,num[k],op3,num[l]);
                                                    solution.add(sol);

                                                }

                                                // variasi 2 : (A op (B op C)) op D

                                                if(operate(operate(a,operate(b,c,op2),op1),d,op3) == goal){
                                                    count_solution++;
                                                    sol = String.format("(%d %s (%d %s %d)) %s %d",num[i],op1,num[j],op2,num[k],op3,num[l]);
                                                    solution.add(sol);

                                                }

                                                // variasi 3 : A op ((B op C) op D)

                                                if(operate(a,operate(operate(b,c,op2),d,op3),op1) == goal){
                                                    count_solution++;
                                                    sol = String.format("%d %s ((%d %s %d) %s %d)",num[i],op1,num[j],op2,num[k],op3,num[l]);
                                                    solution.add(sol);
                                                }

                                                // variasi 4 : A op (B op (C op D))

                                                if(operate(a,operate(b,operate(c,d,op3),op2),op1) == goal){
                                                    count_solution++;
                                                    sol = String.format("%d %s (%d %s (%d %s %d))",num[i],op1,num[j],op2,num[k],op3,num[l]);
                                                    solution.add(sol);
                                                }

                                                // variasi 5 : (A op B) op (C op D)

                                                if(operate(operate(a,b,op1),operate(c,d,op2),op3) == goal){
                                                    count_solution++;
                                                    sol = String.format("(%d %s %d) %s (%d %s %d)",num[i],op1,num[j],op2,num[k],op3,num[l]);
                                                    solution.add(sol);

                                                }
                                            }
                                        }
                                    }
                                    
                                    
                                }
                            }
                        }
                    }
                }
            }
        }
        if(count_solution == 0){
            System.out.println();
            System.out.println("Tidak ada solusi yang ditemukan untuk kombinasi angka tersebut");
            System.out.println();
        }else{
            System.out.println();
            System.out.printf("Terdapat %d solusi",count_solution);
            System.out.println();
            for(String sols:solution){
                System.out.println(sols);
            }

            long end = System.currentTimeMillis();
            long time = end - begin;
            System.out.println();
            System.out.println("Waktu Eksekusi: " + time + "ms");
            
            String filename = "testing"; // temporary name
            int jawab = 0;
            System.out.print("Apakah anda ingin menyimpan solusi ke dalam file? (1:yes/0:no): ");
            try{
                jawab = Integer.parseInt(bufferedReader.readLine());
            }catch(IOException e){
                e.printStackTrace();
            }

            if(jawab == 1){
                System.out.print("Masukkan nama file (tanpa .txt): ");
                filename = bufferedReader.readLine();
                FileWriter writer = new FileWriter("Tucil1_13521079/test/" + filename + ".txt");
                int len = solution.size();
                for(int i = 0; i< len; i++){
                    writer.write(solution.get(i) + "\n");
                }
                writer.close();
            }
            
        }
        
    }

    public static void printMenu(){
        System.out.println();
        System.out.println("###################################");
        System.out.println("  1. Input manual ");
        System.out.println("  2. Generate 4 kartu secara acak ");
        System.out.println("  0. Keluar");
        System.out.println();
    }

    public static int askInput() throws IOException{
        int action = 3;
        
        while(action < 0 || action > 2){
            System.out.print("Pilih (1/2/0): ");
            try{
                action = Integer.parseInt(bufferedReader.readLine());
            }catch(IOException e){
                System.out.println("Masukkan salah");
            }
            
        }
        return action;
    }

    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.println("########################################################################");
        System.out.println("                Selamat datang di program 24 Solver!");
        System.out.println("Anda dapat melihat solusi untuk permainan kartu 24 dengan program ini.");
        printMenu();
        int aksi = askInput();
        int[] kartu;
        while(aksi != 0){
            if(aksi == 1){
                kartu = getFromUser();

            }else{
                kartu = generateRandom();
                showGeneratedCard(kartu);
            }
            solve(kartu);
            System.out.println();
            System.out.println();

            printMenu();
            aksi = askInput();
        }

        System.out.println();
        System.out.println();
        System.out.println("############################################");
        System.out.println("Terima kasih telah menggunakan program ini");
        System.out.println("############################################");
        
    }

}