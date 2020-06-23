package advisor;

import java.util.Scanner;

public class MusicAdvisor {
    boolean flag = false;

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
                    getAuth();
                    flag = true;
                    break;
                default:
                    break;
            }
        } while (!result.equalsIgnoreCase("exit"));
        System.out.println("---GOODBYE!---");
    }

    private void getAuth() {
        System.out.println("https://accounts.spotify.com/authorize?client_id=20e82fb696f54ecd8506d918cc527815&redirect_uri=http://localhost:8080&response_type=code");
        System.out.println("---SUCCESS---");
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

        if (flag) {
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
        if (flag) {
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
}