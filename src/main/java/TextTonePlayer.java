import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextTonePlayer {

    private static final int SAMPLE_RATE = 44100;
    private static final int BITS_PER_SAMPLE = 16;
    private static final int NUM_CHANNELS = 1;
    private static final double MAX_AMPLITUDE = 32767;
    private static final double VOLUME_MULTIPLIER = 0.8; // Giữ ở mức này để tránh méo tiếng
    private static final int PITCH_SHIFT_SEMITONES = 12; 

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

    private static final Map<Integer, Double> NOTE_FREQUENCIES_MAP = new HashMap<>();
    static {
        double A4_FREQ = 440.0;
        int A4_MIDI_NUM = 69;
        // Khởi tạo bản đồ tần số cho tất cả 128 nốt MIDI (0-127)
        for (int midiNum = 0; midiNum <= 127; midiNum++) {
            double freq = A4_FREQ * Math.pow(2, (midiNum - A4_MIDI_NUM) / 12.0);
            NOTE_FREQUENCIES_MAP.put(midiNum, freq);
        }
    }

    public static void main(String[] args) {
        // Đảm bảo đường dẫn này đúng với vị trí file nhạc của bạn
        // Ví dụ: "baihat.txt" nếu file nằm cùng thư mục với TextTonePlayer.java
        // Hoặc đường dẫn đầy đủ: "C:/Users/YourUsername/Desktop/baihat.txt"
        String musicFilePath = "src/main/java/tonenhac-tadakoehitotsu.txt";

        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, NUM_CHANNELS, true, false);
        SourceDataLine line = null;

        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);

            // Tăng kích thước buffer để tránh khựng âm thanh và mất nốt
            int bufferDurationSeconds = 2; // Tăng lên 2 hoặc 3 giây
            int bufferSizeInBytes = (int)(SAMPLE_RATE * bufferDurationSeconds * (BITS_PER_SAMPLE / 8) * NUM_CHANNELS);
            
            line.open(audioFormat, bufferSizeInBytes);
            
            // Điều chỉnh âm lượng bằng Gain Control để tránh méo tiếng
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                // Đặt âm lượng ở mức tối đa có thể để đảm bảo nghe rõ
                gainControl.setValue(gainControl.getMaximum());
            }
            
            line.start();

            try (BufferedReader reader = new BufferedReader(new FileReader(musicFilePath))) {
                String lineContent;
                while ((lineContent = reader.readLine()) != null) {
                    String[] tokens = lineContent.split(" ");

                    for (int i = 0; i < tokens.length; i += 2) {
                        if (i + 1 >= tokens.length) {
                            continue; // Bỏ qua nếu thiếu thời lượng
                        }

                        String noteSymbol = tokens[i].trim();
                        double duration = Double.parseDouble(tokens[i+1].trim());

                        double frequency = 0.0;
                        double actualNotePlayDuration = 0.0;

                        if (!noteSymbol.equals("_")) { // Nếu không phải là dấu lặng
                            try {
                                boolean isSharp = false;
                                if (noteSymbol.contains("#")) {
                                    isSharp = true;
                                    noteSymbol = noteSymbol.replace("#", "");
                                }

                                String[] noteParts = noteSymbol.split("\\.");
                                double baseNoteVal = Double.parseDouble(noteParts[0]);
                                int octave = 4; // Quãng tám mặc định
                                if (noteParts.length > 1) {
                                    octave = Integer.parseInt(noteParts[1]);
                                }

                                int baseNote = (int) baseNoteVal;
                                Integer semitoneOffsetObj = NOTE_OFFSET_FROM_C.get(baseNote);

                                if (semitoneOffsetObj == null) {
                                    System.err.println("Cảnh báo: Ký hiệu nốt không hợp lệ: " + noteSymbol + ". Bỏ qua.");
                                    continue; // Bỏ qua nốt không hợp lệ
                                }
                                int semitoneOffset = semitoneOffsetObj;

                                if (isSharp) {
                                    semitoneOffset += 1;
                                }

                                // Tính toán số MIDI note và ÁP DỤNG PITCH SHIFT
                                int midiNoteNumber = (octave * 12) + 12 + semitoneOffset + PITCH_SHIFT_SEMITONES;
                                
                                // GIỚI HẠN MIDI NOTE TRONG KHOẢNG 0-127
                                midiNoteNumber = Math.max(0, Math.min(127, midiNoteNumber));

                                Double retrievedFrequency = NOTE_FREQUENCIES_MAP.get(midiNoteNumber);
                                if (null == retrievedFrequency) { // Trường hợp này giờ ít xảy ra hơn do đã giới hạn
                                    System.err.println("Cảnh báo: Không tìm thấy tần số cho MIDI note " + midiNoteNumber + ". Bỏ qua nốt: " + noteSymbol);
                                    continue;
                                }
                                frequency = retrievedFrequency;
                                actualNotePlayDuration = duration * 0.8; // Giảm nhẹ thời lượng phát để tạo độ tách biệt giữa các nốt
                                playTone(line, frequency, actualNotePlayDuration);

                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                                System.err.println("Lỗi phân tích cú pháp nốt hoặc thời lượng: '" + tokens[i] + "' " + (i + 1 < tokens.length ? "'" + tokens[i+1] + "'" : "") + " - " + e.getMessage());
                            }
                        }
                        // Dành thời gian nghỉ giữa các nốt
                        Thread.sleep((long) (duration * 1000));
                    }
                }
            }

        } catch (LineUnavailableException | InterruptedException | IOException e) {
            System.err.println("Đã xảy ra lỗi trong quá trình phát nhạc: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt(); // Đặt lại trạng thái ngắt
            }
        } finally {
            // Đảm bảo đóng Line một cách gọn gàng
            if (line != null) {
                line.drain(); // Chờ tất cả âm thanh trong buffer được phát hết
                line.stop();
                line.close();
            }
        }
    }

    private static void playTone(SourceDataLine line, double frequency, double duration) {
        if (frequency <= 0 || duration <= 0) {
            return; // Không phát nếu tần số hoặc thời lượng không hợp lệ
        }

        int numSamples = (int) (SAMPLE_RATE * duration);
        byte[] audioBuffer = new byte[numSamples * (BITS_PER_SAMPLE / 8)];

        for (int i = 0; i < numSamples; i++) {
            double time = (double) i / SAMPLE_RATE;
            // Tính toán mẫu sóng sin
            double sample = (MAX_AMPLITUDE * VOLUME_MULTIPLIER) * Math.sin(2 * Math.PI * frequency * time);

            // Chuyển đổi mẫu từ double sang short (16-bit)
            short s = (short) sample;
            // Ghi 16-bit short vào 2 byte theo định dạng little-endian
            audioBuffer[i * 2] = (byte) (s & 0xFF);         // Byte thấp
            audioBuffer[i * 2 + 1] = (byte) ((s >> 8) & 0xFF); // Byte cao
        }
        line.write(audioBuffer, 0, audioBuffer.length); // Ghi dữ liệu vào audio line
    }
}