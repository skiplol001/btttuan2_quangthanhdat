import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextTonePlayer {

    // --- Cấu hình âm thanh cơ bản ---
    private static final int SAMPLE_RATE = 44100; // Tần số lấy mẫu (samples per second), chuẩn CD
    private static final int BITS_PER_SAMPLE = 16; // Số bit cho mỗi mẫu âm thanh (16-bit)
    private static final int NUM_CHANNELS = 1; // Số kênh âm thanh (1 = mono)
    private static final double MAX_AMPLITUDE = 32767; // Biên độ tối đa cho 16-bit signed integer

    // --- Bảng ánh xạ nốt nhạc và tần số ---
    // Ánh xạ từ số nốt cơ bản (1-7) sang số nửa cung so với C trong cùng octave.
    private static final Map<Integer, Integer> NOTE_OFFSET_FROM_C = new HashMap<>();
    static {
        NOTE_OFFSET_FROM_C.put(1, 0);  // C
        NOTE_OFFSET_FROM_C.put(2, 2);  // D
        NOTE_OFFSET_FROM_C.put(3, 4);  // E
        NOTE_OFFSET_FROM_C.put(4, 5);  // F
        NOTE_OFFSET_FROM_C.put(5, 7);  // G
        NOTE_OFFSET_FROM_C.put(6, 9);  // A
        NOTE_OFFSET_FROM_C.put(7, 11); // B
    }

    // Map chứa tần số (Hz) cho tất cả các nốt MIDI từ 0 đến 127.
    // Đây là phần đã được thêm vào và sửa lỗi để frequency không null.
    private static final Map<Integer, Double> NOTE_FREQUENCIES_MAP = new HashMap<>();
    static {
        // Tần số của A4 (La quãng 4), là MIDI note 69, chuẩn 440 Hz.
        double A4_FREQ = 440.0;
        int A4_MIDI_NUM = 69;

        // Tính toán và điền tần số cho tất cả 128 MIDI note (0-127) vào map.
        for (int midiNum = 0; midiNum <= 127; midiNum++) {
            double freq = A4_FREQ * Math.pow(2, (midiNum - A4_MIDI_NUM) / 12.0);
            NOTE_FREQUENCIES_MAP.put(midiNum, freq);
        }
    }

    // --- Phương thức chính để phát nhạc ---
    public static void main(String[] args) {
        String musicFilePath = "rickroll.txt"; // Tên file nhạc của bạn. Đặt file này cùng thư mục với file .java/.class

        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, NUM_CHANNELS, true, false);
        SourceDataLine line = null; // SourceDataLine là đường dữ liệu để gửi âm thanh ra loa.

        try {
            // Lấy và mở SourceDataLine.
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start(); // Bắt đầu luồng dữ liệu âm thanh.

            // Đọc file nhạc từng dòng.
            try (BufferedReader reader = new BufferedReader(new FileReader(musicFilePath))) {
                String lineContent;
                while ((lineContent = reader.readLine()) != null) {
                    String[] tokens = lineContent.split(" ");

                    for (int i = 0; i < tokens.length; i += 2) {
                        if (i + 1 >= tokens.length) {
                            // Chỉ in lỗi cú pháp nghiêm trọng ra System.err
                            System.err.println("Error: Missing duration for note " + tokens[i]);
                            continue;
                        }

                        String noteSymbol = tokens[i].trim();
                        double duration = Double.parseDouble(tokens[i+1].trim());

                        double frequency = 0.0;
                        double actualNotePlayDuration = 0.0;

                        if (!noteSymbol.equals("_")) { // Nếu không phải dấu lặng thì xử lý nốt
                            try {
                                boolean isSharp = false;
                                if (noteSymbol.contains("#")) {
                                    isSharp = true;
                                    noteSymbol = noteSymbol.replace("#", "");
                                }

                                String[] noteParts = noteSymbol.split("\\.");
                                double baseNoteVal = Double.parseDouble(noteParts[0]);
                                int octave = 4; // Octave mặc định là 4 (Đô trung) nếu không chỉ định.
                                if (noteParts.length > 1) {
                                    octave = Integer.parseInt(noteParts[1]);
                                }

                                int baseNote = (int) baseNoteVal;
                                Integer semitoneOffsetObj = NOTE_OFFSET_FROM_C.get(baseNote);

                                if (semitoneOffsetObj == null) {
                                    System.err.println("Error: Invalid base note " + tokens[i] + ". Skipping.");
                                    continue;
                                }
                                int semitoneOffset = semitoneOffsetObj;

                                if (isSharp) {
                                    semitoneOffset += 1;
                                }
                                
                                // Tính MIDI note number (C0 là MIDI note 12).
                                int midiNoteNumber = (octave * 12) + 12 + semitoneOffset;
                                // Lấy tần số chính xác từ NOTE_FREQUENCIES_MAP.
                                // Dòng này giờ sẽ không còn lỗi NullPointerException nếu Map đã được khởi tạo đúng.
                                 // Lấy tần số chính xác từ NOTE_FREQUENCIES_MAP.
                                Double retrievedFrequency = NOTE_FREQUENCIES_MAP.get(midiNoteNumber); 
                                if (null == retrievedFrequency) { // Kiểm tra null trên đối tượng Double
                                    System.err.println("Error: MIDI note " + midiNoteNumber + " frequency not found. Skipping.");
                                    continue; 
                                }
                                frequency = retrievedFrequency; // Gán giá trị từ Double sang double
                                actualNotePlayDuration = duration * 0.9; // Nốt vang 90% thời gian để có khoảng ngắt.
                                playTone(line, frequency, actualNotePlayDuration); // Gọi phương thức phát âm thanh.

                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                                System.err.println("Error parsing note: " + tokens[i] + ". " + e.getMessage() + ". Skipping.");
                            }
                        }
                        Thread.sleep((long) (duration * 1000)); // Chờ tổng thời gian của nốt.
                    }
                }
            }

        } catch (LineUnavailableException e) {
            System.err.println("CRITICAL ERROR: Audio line unavailable. Check your audio device. " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("CRITICAL ERROR: Playback interrupted. " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR: Could not read music file: " + musicFilePath + ". " + e.getMessage());
        } finally {
            // Đảm bảo đóng đường dữ liệu khi hoàn thành hoặc có lỗi để giải phóng tài nguyên.
            if (line != null) {
                line.drain(); // Chờ tất cả dữ liệu trong buffer được phát hết.
                line.stop();
                line.close();
            }
        }
    }
    // --- Phương thức tạo và phát một nốt nhạc cụ thể ---
    private static void playTone(SourceDataLine line, double frequency, double duration) {
        if (frequency <= 0 || duration <= 0) {
            return; // Không phát nếu tần số hoặc thời lượng không hợp lệ.
        }

        int numSamples = (int) (SAMPLE_RATE * duration);
        byte[] audioBuffer = new byte[numSamples * (BITS_PER_SAMPLE / 8)];

        for (int i = 0; i < numSamples; i++) {
            double time = (double) i / SAMPLE_RATE;
            // Công thức sóng sin: Biên độ * sin(2 * pi * tần số * thời gian)
            double sample = MAX_AMPLITUDE * Math.sin(2 * Math.PI * frequency * time);

            short s = (short) sample;
            // Chuyển đổi short sang 2 byte (little-endian) và lưu vào buffer.
            audioBuffer[i * 2] = (byte) (s & 0xFF);         // Byte thấp
            audioBuffer[i * 2 + 1] = (byte) ((s >> 8) & 0xFF); // Byte cao
        }
        line.write(audioBuffer, 0, audioBuffer.length); // Ghi buffer để phát.
    }
}