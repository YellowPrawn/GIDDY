package ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Data;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("enter file name");
        Scanner path = new Scanner(System.in);
        Data data = new Data("src/main/data/" + path.nextLine());
        System.out.println(data.getData()[0].toString());
        System.out.println(data.getData()[1].toString());
    }
}
