/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author lap top
 */
public class QuanLySach {
    private List<Sach> danhSachSach; // Nơi lưu trữ tất cả sách

    public QuanLySach() {
        this.danhSachSach = new ArrayList<>();
        themSachMau(); // Tự động thêm sách mẫu khi tạo đối tượng quản lý
    }

    // Thêm các cuốn sách mẫu vào thư viện
    private void themSachMau() {
        danhSachSach.add(new Sach("S001", "Lập trình Java căn bản", "Nguyen Van A", 2022));
        danhSachSach.add(new Sach("S002", "Hướng dẫn học Java nâng cao", "Nguyen Van B", 2023));
        danhSachSach.add(new Sach("S003", "Kỹ thuật lập trình C#", "Nguyen Van C", 2021));
        danhSachSach.add(new Sach("S004", "Toán rời rạc và ứng dụng", "Nguyen Van D", 2020));
        danhSachSach.add(new Sach("S005", "Cấu trúc dữ liệu và giải thuật", "Nguyen Van A", 2023));
        danhSachSach.add(new Sach("S006", "Java Core cho người mới bắt đầu", "Nguyen Van E", 2022));
        danhSachSach.add(new Sach("S007", "Dấu ấn Rồng thiêng", "Koji Inada", 1990));
        System.out.println("Da them " + danhSachSach.size() + "\n");
    }

    // Hiển thị tất cả sách hiện có trong thư viện
    public void hienThiTatCaSach() {
        if (danhSachSach.isEmpty()) {
            System.out.println("Thư viện hiện không có sách nào.");
            return;
        }
        System.out.println("--- Danh sách tất cả sách trong thư viện ---");
        for (Sach sach : danhSachSach) {
            System.out.println("- " + sach.toString());
        }
        System.out.println();
    }

    // Tìm sách theo tên (tìm gần đúng, không phân biệt hoa/thường)
    public void timSachTheoTen(String tuKhoa) {
        String tuKhoaLower = tuKhoa.toLowerCase();
        List<Sach> ketQuaTimKiem = new ArrayList<>();

        for (Sach sach : danhSachSach) {
            if (sach.getTenSach().toLowerCase().contains(tuKhoaLower)) {
                ketQuaTimKiem.add(sach);
            }
        }

        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Khong tim thay cuon chua tu khoa \"" + tuKhoa + "\".\n");
        } else {
            System.out.println("Ket qua tim kiem cho \"" + tuKhoa + "\":");
            for (Sach sach : ketQuaTimKiem) {
                System.out.println("- " + sach.toString());
            }
            System.out.println();
        }
    }

    // Thống kê số lượng sách theo năm xuất bản
    public void thongKeSachTheoNamXuatBan() {
        System.out.println("--- Thong ke sach theo nam xuat ban ---");
        Map<Integer, Integer> thongKeNam = new HashMap<>();

        for (Sach sach : danhSachSach) {
            int nam = sach.getNamXuatBan();
            thongKeNam.put(nam, thongKeNam.getOrDefault(nam, 0) + 1);
        }

        if (thongKeNam.isEmpty()) {
            System.out.println("Thu vien hien chua co sach nao de thong ke.\n");
        } else {
            System.out.println("So luong sach theo nam xuat ban:");
            for (Map.Entry<Integer, Integer> entry : thongKeNam.entrySet()) {
                System.out.println("- Nam " + entry.getKey() + ": " + entry.getValue() + " cuon");
            }
            System.out.println();
        }
    }
}
