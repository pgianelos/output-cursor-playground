package my.daysleeper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static my.daysleeper.AnsiColors.ANSI_CYAN;
import static my.daysleeper.AnsiColors.ANSI_GREEN;
import static my.daysleeper.AnsiColors.ANSI_RESET;

public class FileDownloaderSimulator {

    private static final String DOWNLOAD_PROGRESS_TEMPLATE = "%s%% %s | %s, %d/%d bytes";
    private static final int DOWNLOAD_PROGRESS_BAR_SIZE = 50;

    public static void main(String[] args) throws InterruptedException {

        Map<String, Integer> filesMap = new HashMap<>();
        filesMap.put("myNotes.txt", 52341);
        filesMap.put("pictures.zip", 1252345);
        filesMap.put("myCode.war", 423876);

        System.out.println("Downloading " + filesMap.size() + " files...");
        downloadProgress(filesMap);
        System.out.println("Download complete!");

    }

    private static void downloadProgress(Map<String, Integer> filesMap) {

        Random random = new Random();

        filesMap.forEach((file, size) -> {

            int byteRate = (size / 1000);

            for (int i=0; i < size; i += byteRate) {

                int percent = (int) (((float)i / (float)size) * 100);

                String bar = String.format(DOWNLOAD_PROGRESS_TEMPLATE, printPercent(percent), printProgressBar(percent), file, i, size);
                System.out.print("\r" + bar);
                try {
                    Thread.sleep(random.nextInt(30));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String bar = String.format(DOWNLOAD_PROGRESS_TEMPLATE, printPercent(100), printProgressBar(100), file, size, size);
            System.out.println('\r' + ANSI_GREEN + bar + ANSI_RESET);
        });

    }

    private static String printProgressBar(int percent) {

        StringBuilder sb = new StringBuilder( percent == 100 ? "" : ANSI_CYAN);
        for (int i=0; i < DOWNLOAD_PROGRESS_BAR_SIZE; i++) {

            int barSizePercent = (int) (((float)i / (float)DOWNLOAD_PROGRESS_BAR_SIZE) * 100);

            if (barSizePercent < percent) {
                sb.append('█');
            } else {
                sb.append('░');
            }
        }

        sb.append(percent == 100 ? "" : ANSI_RESET);
        return sb.toString();
    }

    private static String printPercent(int percent) {
        String percentString = "";

        if (percent < 0 ) {
            return percentString;
        }

        if (percent < 10){
            percentString = "  " + percent;
        } else if (percent < 100) {
            percentString = " " + percent;
        } else if (percent == 100) {
            percentString = "" + percent;
        }

        return percentString;


    }
}
