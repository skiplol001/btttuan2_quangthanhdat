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
        System.out.println("Đang thêm các sách mẫu vào thư viện...");
        danhSachSach.add(new Sach("S001", "Lập trình Java căn bản", "Nguyễn Văn A", 2022));
        danhSachSach.add(new Sach("S002", "Hướng dẫn học Java nâng cao", "Trần Thị B", 2023));
        danhSachSach.add(new Sach("S003", "Kỹ thuật lập trình C#", "Lê Văn C", 2021));
        danhSachSach.add(new Sach("S004", "Toán rời rạc và ứng dụng", "Phạm Thị D", 2020));
        danhSachSach.add(new Sach("S005", "Cấu trúc dữ liệu và giải thuật", "Nguyễn Văn A", 2023));
        danhSachSach.add(new Sach("S006", "Java Core cho người mới bắt đầu", "Hoàng Văn E", 2022));
        danhSachSach.add(new Sach("S007", "Dấu ấn Rồng thiêng", "Koji Inada", 1990));
        System.out.println("Đã thêm " + danhSachSach.size() + " cuốn sách mẫu.\n");
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
            System.out.println("Không tìm thấy cuốn sách nào chứa từ khóa \"" + tuKhoa + "\".\n");
        } else {
            System.out.println("Kết quả tìm kiếm cho \"" + tuKhoa + "\":");
            for (Sach sach : ketQuaTimKiem) {
                System.out.println("- " + sach.toString());
            }
            System.out.println();
        }
    }

    // Thống kê số lượng sách theo năm xuất bản
    public void thongKeSachTheoNamXuatBan() {
        System.out.println("--- Thống kê sách theo năm xuất bản ---");
        Map<Integer, Integer> thongKeNam = new HashMap<>();

        for (Sach sach : danhSachSach) {
            int nam = sach.getNamXuatBan();
            thongKeNam.put(nam, thongKeNam.getOrDefault(nam, 0) + 1);
        }

        if (thongKeNam.isEmpty()) {
            System.out.println("Thư viện hiện chưa có sách nào để thống kê.\n");
        } else {
            System.out.println("Số lượng sách theo năm xuất bản:");
            for (Map.Entry<Integer, Integer> entry : thongKeNam.entrySet()) {
                System.out.println("- Năm " + entry.getKey() + ": " + entry.getValue() + " cuốn");
            }
            System.out.println();
        }
    }
}
