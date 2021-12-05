/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ToDoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;

/**
 *
 * @author kazuma
 */
public class ToDoList {
    
    //Variable Global
    static String namafile;
    static ArrayList<String> todoList;
    static Boolean isEditing = false;
    static Scanner input;
    
    public static void main(String []args){
        //initialize
        todoList = new ArrayList<>();
        input = new Scanner(System.in);

        String filePatch = System.console() == null ? "/src/todolist.txt" : "/todolist.txt";
        namafile = System.getProperty("user.dir") + filePatch;

        System.out.println("File :" + namafile);


        //Jalanin Program
        while(true){
            TampilanMenu();
        }
        
    }
    
    static void HapusLayar(){
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Linux")){
                Runtime.getRuntime().exec("clear");
                System.out.println("\033[H\033[2J");
                System.out.flush();
            }
        }catch (final Exception e){
            System.out.println("ERROR : " +e.getMessage());
        }
    }
    
    static void TampilanMenu(){
        System.out.println("=== To Do List ===");
        System.out.println("1. Lihat To Do List");
        System.out.println("2. Tambah To Do List");
        System.out.println("3. Edit To Do List");
        System.out.println("4. Hapus To Do List");
        System.out.println("0. Keluar");
        System.out.println("--------------------");
        System.out.println("Pilih menu :");
        
        String selectedMenu = input.nextLine();
        
        if (selectedMenu.equals("1")){
            TampilkanTodoList();
        } else if (selectedMenu.equals("2")){
            TambahTodoList();
        } else if (selectedMenu.equals("3")){
            EditTodoList();
        } else if (selectedMenu.equals("4")){
            HapusTodoList();
        } else if (selectedMenu.equals("0")){
            System.exit(0);
        } else {
            System.out.println("salah pilih menu!");
            BalikMenu();
        }
    }
    
    static void BalikMenu(){
        System.out.println("");
        System.out.println("Tekan Enter untuk kembali");
        input.nextLine();
        HapusLayar();
    }
    
    static void bacaTodoList(){
        try {
            File file = new File(namafile);
            Scanner fileReader = new Scanner(file);
            
            //Load isi file kedalam Array
            todoList.clear();
            while (fileReader.hasNextLine()){
                String data = fileReader.nextLine();
                todoList.add(data);
                }
             } catch (FileNotFoundException e){
            System.out.println("Error :" + e.getMessage());
        }
    }
    
    static void TampilkanTodoList(){
        HapusLayar();
        bacaTodoList();

        if( todoList.size() > 0){
            System.out.println("To Do List :");
            int index = 0;
            //Looping
            for (String data :todoList){
                System.out.println(String.format("[%d] %s", index, data));
                index++;
            }
        } else {
            System.out.println("Gaada data!");
        }
        
        if (!isEditing){
            BalikMenu();
        }
    }
    
    static void TambahTodoList(){
        HapusLayar();
        
        System.out.println("Ada yang ingin dikerjakan? Jawab :");
        String newTodoList = input.nextLine();
        
        try {
            //tulisfile
            FileWriter fileWriter = new FileWriter(namafile, true);
            fileWriter.append(String.format("%s%n", newTodoList));
            fileWriter.close();
            System.out.println("Berhasil menambahkan!");     
        } catch (IOException e){
            System.out.println("Terjadi kesalahan karena :");
        }
        BalikMenu();
    }
    
    static void EditTodoList(){
        isEditing = true;
        TampilkanTodoList();
        
        try {
            System.out.println("Pilih Indeks");
            int index = Integer.parseInt(input.nextLine());

            if(index > todoList.size()){
                throw new IndexOutOfBoundsException("data nya salah nih!");
            } else {
                System.out.println("Data baru ");
                String newData = input.nextLine();

                //update data nya
                todoList.set(index, newData);

                System.out.println(todoList.toString());

                try{
                    FileWriter fileWriter = new FileWriter(namafile, false);

                    //tulis data baru
                    for(String data : todoList){
                        fileWriter.append(String.format("%s%n", data));
                    }
                    fileWriter.close();

                    System.out.println("Berhasil diubah!");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan gegara :" + e.getMessage());
            }
         }    
        } catch (Exception e){
             //TODO: handle exception
            System.out.println(e.getMessage());
    }
        isEditing = false;
           BalikMenu();
    }
    
    static void HapusTodoList(){
        isEditing = true;
        TampilkanTodoList();

        System.out.println("Pilih Indeks :");
        int index = Integer.parseInt(input.nextLine());
        
        try {
            if(index > todoList.size()){
                throw new IndexOutOfBoundsException("Kamu masukan data yang salah!");
            } else {

                System.out.println("Kamu bakal menghapus :" + "Yakin?");
                System.out.println(String.format("[%d] %s" , index, todoList.get(index)));
                System.out.println("Jawab nya Y/T : ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")){
                    //hapus data
                    todoList.remove(index);

                    //nulis ulang file
                    try{
                        FileWriter fileWriter = new FileWriter(namafile, false);

                        //tulis data baru
                        for(String data : todoList){
                            fileWriter.append(String.format("%s%n", data));
                        }
                        fileWriter.close();

                        System.out.println("Dihapus!");
                    } catch (IOException e){
                        System.out.println("Terjadi kesalahan gegara :" +e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e.getMessage());
        }
        isEditing = false;
        BalikMenu();
    }
}
