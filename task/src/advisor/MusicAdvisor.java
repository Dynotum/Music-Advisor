package advisor;


import java.io.IOException;
import java.util.Scanner;

public class MusicAdvisor {
    private final String REDIRECT_URI = "redirect_uri=http://localhost:5656&response_type=code";
    private final String CLIENT_ID = "client_id=20e82fb696f54ecd8506d918cc527815";
    private final String CLIENT_SECRET = "client_secret=08fefe1baf834216a4551def8e5756ff";
    private final String API_TOKEN = "/api/token";
    private String access_token = "";
    private final String access_server;
    private String auth = "/authorize?" + CLIENT_ID + "&" + REDIRECT_URI;
    private SingletonWeb webRq = SingletonWeb.getInstance();
    private boolean isAuth = false;

    MusicAdvisor(String[] args) {
        if (args.length == 0) {
            this.access_server = "https://accounts.spotify.com";
        } else {
            this.access_server = args[1];
        }
    }

    public void getReco() {
        final Scanner scanner = new Scanner(System.in);
        String result;
        do {
            result = scanner.nextLine();

            switch (result.toLowerCase()) {
                case "new":
                    getNew();
                    break;
                case "featured":
                    getFeatured();
                    break;
                case "categories":
                    getCateg();
                    break;
                case "playlists mood":
                    getPlayMood();
                    break;
                case "auth":
                    try {
                        getAuth();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        } while (!result.equalsIgnoreCase("exit"));
        System.out.println("---GOODBYE!---");
    }

    private void getPlayMood() {
        System.out.println("---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll\n");
    }

    private void getCateg() {
        System.out.println("---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin\n");
    }

    private void getFeatured() {

        if (isAuth) {
            System.out.println("---FEATURED---\n" +
                    "Mellow Morning\n" +
                    "Wake Up and Smell the Coffee\n" +
                    "Monday Motivation\n" +
                    "Songs to Sing in the Shower");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void getNew() {
        if (isAuth) {
            System.out.println(
                    "---NEW RELEASES---\n" +
                            "Mountains [Sia, Diplo, Labrinth]\n" +
                            "Runaway [Lil Peep]\n" +
                            "The Greatest Show [Panic! At The Disco]\n" +
                            "All Out Life [Slipknot]\n");
        } else {
            System.out.println("Please, provide access for application.");
        }
    }

    private void getAuth() throws IOException, InterruptedException {
        String code = "";
        myHttpSvr svr = new myHttpSvr();
        svr.webStart();

        System.out.println("use this link to request the access code:");
        System.out.println(access_server + auth);
        System.out.println("waiting for code...");

        do {
            Thread.sleep(2000);
            code = svr.getQuery();
            if (code == null || !code.startsWith("code=")) {
                Thread.sleep(100);
            } else {
                System.out.println("code received");
                svr.webStop(1);
                Thread.sleep(100);
                System.out.println("making http request for access_token...\nreponse:");
                access_token = webRq.getToken(access_server + API_TOKEN, code, CLIENT_ID, CLIENT_SECRET);

                if (!access_token.isEmpty()) {
                    isAuth = true;
                    System.out.println(access_token);
                    System.out.println("---SUCCESS---");
                }
                break;
            }
        } while (true);
    }
}