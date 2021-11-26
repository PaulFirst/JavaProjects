package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        final int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        final int cols = sc.nextInt();

        String[][] room = getRoom(rows, cols);
        int option;

        do {
            showMenu();
            option = sc.nextInt();
            switch (option) {
                case 1:
                    showRoom(room);
                    break;
                case 2:
                    buyTicket(room, sc, rows, cols);
                    showRoom(room);
                case 3:
                    showStatistics(room);
            }
        } while (option != 0);

    }

    private static String[][] getRoom(int rows, int cols) {
        String[][] room = new String[rows + 1][cols + 1];
        room[0][0] = " ";
        for (int i = 1; i < room.length; i++) {
            room[i][0] = Integer.toString(i);
        }
        for (int i = 1; i < room[0].length; i++) {
            room[0][i] = Integer.toString(i);
        }
        for (int i = 1; i < room.length; i++) {
            for (int j = 1; j < room[i].length; j++) {
                room[i][j] = "S";
            }
        }
        return room;
    }

    private static void showRoom(String[][] room) {
        System.out.println("Cinema:");
        for (int i = 0; i < room.length; i++) {
            for (int j = 0; j < room[i].length; j++) {
                System.out.print(room[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static void showStatistics(String[][] room) {
        int tickets = 0;
        final int totalTickets = (room.length - 1) * (room[0].length - 1);
        int income = 0;
        int totalIncome = 0;

        for (int i = 1; i < room.length; i++) {
            for (int j = 1; j < room[i].length; j++) {
                totalIncome += ticketCost(room, i);
                if (room[i][j] == "B") {
                    tickets++;
                    income += ticketCost(room, i);
                }
            }
        }

        final double percent = (double) tickets / totalTickets * 100;

        System.out.printf("Number of purchased tickets: %d\n", tickets);
        System.out.printf("Percentage: %.2f%%\n", percent);
        System.out.printf("Current income: $%d\n", income);
        System.out.printf("Total income: $%d\n", totalIncome);
    }

    private static void buyTicket(String[][] room, Scanner sc, int rows, int cols) {
        while (true) {
            System.out.println("Enter a row number:");
            final int rowN = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            final int colN = sc.nextInt();

            if (rowN < 1 || rowN >= room.length || colN < 1 || colN >= room[rowN].length) {
                System.out.println("Wrong input!");
                continue;
            } else if (room[rowN][colN] == "B") {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            int ticketCost = ticketCost(room, rowN);
            room[rowN][colN] = "B";
            System.out.println("Ticket price: $" + ticketCost + "\n");
            return;
        }
    }

    private static int ticketCost(String[][] room, int rowN) {
        int ticketCost;
        final int rows = (room.length - 1);
        final int cols = (room[0].length - 1);
        final int totalSeats = rows * cols;

        if (totalSeats < 60) {
            ticketCost = 10;
        } else {
            int half = rows / 2;
            ticketCost = (rowN - 1) < half ? 10 : 8;
        }
        return ticketCost;
    }
}
