package baitap3;

import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void hienThiMenu() {
        System.out.println("\n===== MENU QUAN LY HINH HOC =====");
        System.out.println("1. Them hinh hoc moi");
        System.out.println("2. In thong tin cac hinh hoc");
        System.out.println("0. Thoat chuong trinh");
        System.out.print("Nhap lua chon cua ban: ");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            QuanLyHinhHoc quanLyHinhHoc = new QuanLyHinhHoc(scanner);
            int luaChon;

            do {
                hienThiMenu(); 
                try {
                    luaChon = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (luaChon) {
                        case 1: 
                            if (quanLyHinhHoc.themHinhHoc()) {
                                System.out.println("Them hinh hoc thanh cong!");
                            }
                            break;
                        case 2: 
                            quanLyHinhHoc.inThongTinHinhHoc();
                            break;
                        case 0:  
                            System.out.println("Dang thoat chuong trinh. Tam biet!");
                            break;
                        default:
                            System.out.println("Lua chon khong hop le. Vui long nhap lai.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Loi: Lua chon phai la mot so nguyen. Vui long nhap lai.");
                    scanner.nextLine(); 
                    luaChon = -1; 
                }

            } while (luaChon != 0); 
        }
    }
}