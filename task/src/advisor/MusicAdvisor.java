package advisor;

import java.util.Scanner;

public class MusicAdvisor {

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
        System.out.println("---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    private void getNew() {
        System.out.println(
                "---NEW RELEASES---\n" +
                        "Mountains [Sia, Diplo, Labrinth]\n" +
                        "Runaway [Lil Peep]\n" +
                        "The Greatest Show [Panic! At The Disco]\n" +
                        "All Out Life [Slipknot]\n");
    }
}