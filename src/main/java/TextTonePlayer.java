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
    private static final double VOLUME_MULTIPLIER = 0.9; 

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
        for (int midiNum = 0; midiNum <= 127; midiNum++) {
            double freq = A4_FREQ * Math.pow(2, (midiNum - A4_MIDI_NUM) / 12.0);
            NOTE_FREQUENCIES_MAP.put(midiNum, freq);
        }
    }

    public static void main(String[] args) {
        // Đảm bảo đường dẫn này đúng với vị trí file nhạc của bạn
        String musicFilePath = "src/main/java/tonenhac-tadakoehitotsu.txt"; 

        AudioFormat audioFormat = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, NUM_CHANNELS, true, false);
        SourceDataLine line = null;

        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);

            // Tăng kích thước buffer để tránh khựng âm thanh
            int bufferDurationSeconds = 1; 
            int bufferSizeInBytes = (int)(SAMPLE_RATE * bufferDurationSeconds * (BITS_PER_SAMPLE / 8) * NUM_CHANNELS);
            
            line.open(audioFormat, bufferSizeInBytes);
            
            // Điều chỉnh âm lượng bằng Gain Control để tránh méo tiếng
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
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

                        if (!noteSymbol.equals("_")) { 
                            try {
                                boolean isSharp = false;
                                if (noteSymbol.contains("#")) {
                                    isSharp = true;
                                    noteSymbol = noteSymbol.replace("#", "");
                                }

                                String[] noteParts = noteSymbol.split("\\.");
                                double baseNoteVal = Double.parseDouble(noteParts[0]);
                                int octave = 4; 
                                if (noteParts.length > 1) {
                                    octave = Integer.parseInt(noteParts[1]);
                                }

                                int baseNote = (int) baseNoteVal;
                                Integer semitoneOffsetObj = NOTE_OFFSET_FROM_C.get(baseNote);

                                if (semitoneOffsetObj == null) {
                                    continue; // Bỏ qua nốt không hợp lệ
                                }
                                int semitoneOffset = semitoneOffsetObj;

                                if (isSharp) {
                                    semitoneOffset += 1;
                                }
                                
                                int midiNoteNumber = (octave * 12) + 12 + semitoneOffset;
                                Double retrievedFrequency = NOTE_FREQUENCIES_MAP.get(midiNoteNumber); 
                                if (null == retrievedFrequency) { 
                                    continue; // Bỏ qua nếu không tìm thấy tần số
                                }
                                frequency = retrievedFrequency; 
                                actualNotePlayDuration = duration * 0.9; 
                                playTone(line, frequency, actualNotePlayDuration); 

                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                                // Bỏ qua các lỗi phân tích cú pháp để chương trình không dừng
                            }
                        }
                        Thread.sleep((long) (duration * 1000)); 
                    }
                }
            }

        } catch (LineUnavailableException | InterruptedException | IOException e) {
            // Gộp các ngoại lệ và in lỗi chung nếu có sự cố nghiêm trọng
            System.err.println("An error occurred during music playback: " + e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        } finally {
            if (line != null) {
                line.drain();
                line.stop();
                line.close();
            }
        }
    }

    private static void playTone(SourceDataLine line, double frequency, double duration) {
        if (frequency <= 0 || duration <= 0) {
            return; 
        }

        int numSamples = (int) (SAMPLE_RATE * duration);
        byte[] audioBuffer = new byte[numSamples * (BITS_PER_SAMPLE / 8)];

        for (int i = 0; i < numSamples; i++) {
            double time = (double) i / SAMPLE_RATE;
            double sample = (MAX_AMPLITUDE * VOLUME_MULTIPLIER) * Math.sin(2 * Math.PI * frequency * time);

            short s = (short) sample;
            audioBuffer[i * 2] = (byte) (s & 0xFF);     
            audioBuffer[i * 2 + 1] = (byte) ((s >> 8) & 0xFF); 
        }
        line.write(audioBuffer, 0, audioBuffer.length); 
    }
}