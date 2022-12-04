package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        int purchasedTickets = 0;
        int ticketPrice = 0;
        start(purchasedTickets, ticketPrice);
    }
/**
 *
 */

    public static void start(int purchasedTickets, int ticketPrice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows:\n");
        int numberOfRows = scanner.nextInt(); //количество рядов
        System.out.print("Enter the number of seats in each row:\n");
        int seatsInRow = scanner.nextInt(); //количество мест в ряду
        if ((numberOfRows <= 0) || (seatsInRow <= 0)) {
            System.out.println("Please, enter again");
            start(purchasedTickets, ticketPrice);
        } else {
            //int allSeats = numberOfRows * seatsInRow;
            char[][] array = new char[numberOfRows][seatsInRow]; //создаем массив
            for (int arrayR = 0; arrayR < numberOfRows; arrayR++) { //заполняем массив S
                for (int arrayS = 0; arrayS < seatsInRow; arrayS++) {
                    array[arrayR][arrayS] = 'S';
                }
            }
            goToMenu(array, purchasedTickets, ticketPrice);
        }
    } //получаем количество рядов и мест, проверяем их и создаем массив

    public static void goToMenu(char[][] array, int purchasedTickets, int ticketPrice) { //выводим меню выбора
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.print("");
        Scanner scanner = new Scanner(System.in);
        int selectMenu = scanner.nextInt();
        System.out.println();
        switch (selectMenu) {
            case 1: {
                printMapOfSeats(array); //вызов метода печать мест
                goToMenu(array, purchasedTickets, ticketPrice); //вызов метода меню
                break;
            }
            case 2: {
                ticketPrice = ticketPrice + buyTicket (array); //вызов метода покупки места
                purchasedTickets++;
                goToMenu(array, purchasedTickets, ticketPrice); //вызов метода меню
                break;
            }
            case 3:{
                showStatistics(array, purchasedTickets, ticketPrice); //вызов метода рассчета статистики
                break;
            }
            default: {
                break; //завершение программы
            }
        }
    }

    public static void printMapOfSeats(char[][] array) { //метод показа пустых и занятых мест
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < array[0].length; i++){
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < array.length; i++){
            System.out.print(i + 1 + " ");
            for (int j = 0; j < array[0].length; j++) {
                System.out.print (array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int buyTicket(char[][] array) { //метод покупки билетов
        Scanner scanner = new Scanner(System.in);
        int sumOfTicket = 0;
        System.out.print("Enter a row number:\n"); //номер ряда где покупают билет
        int selectedNumberOfRow = scanner.nextInt(); //принимаем ряд
        System.out.print("Enter a seat number in that row:\n"); //номер места в ряду
        int selectedSeatInRow = scanner.nextInt(); //принимаем номер
        if ((selectedNumberOfRow > array.length) || (selectedSeatInRow > array[0].length)) { //проверка размера массива
            System.out.println("Wrong input!\n");
            buyTicket(array);
        } else if (array[selectedNumberOfRow - 1][selectedSeatInRow - 1] == 'B') { //проверка не занято ли место
            System.out.println("That ticket has already been purchased!\n");
            buyTicket(array);
        } else {
            array[selectedNumberOfRow - 1][selectedSeatInRow - 1] = 'B';
            sumOfTicket = calculateTicketPrice(array, selectedNumberOfRow);//передаем массив мест и выбранный ряд, получаем цену
            System.out.println("\nTicket price: $" + sumOfTicket); //выводим цену за выбраное место
            System.out.print("");
        }
        return  sumOfTicket; //цена билета для суммы всей стоимости
    }

    public static int calculateTicketPrice(char[][] array, int selectedNumberOfRow) { //метод рассчета цены за билет
        int price = 0;
        int allSeats = array.length * array[0].length;
        int maxSeatsForHighPrice = 60;
        if (allSeats < maxSeatsForHighPrice) {
            price = 10;
        }
        if (allSeats >= maxSeatsForHighPrice) {
            int highPricedHalf = array.length / 2;
            int lowPricedHalf = array.length - highPricedHalf;
            if (selectedNumberOfRow < lowPricedHalf) {
                price = 10;
            } else {
                price = 8;
            }
        }
        return price;
    }

    public static void showStatistics(char[][] array, int purchasedTickets, int ticketPrice) { //метод рассчета статистики
        System.out.println("Number of purchased tickets: "+ purchasedTickets);
        float percentage, a, b;
        a = purchasedTickets; //количество купленных мест
        b = array.length * array[0].length; //общее количество мест  зале
        percentage = a * 100 / b; //вычисление процента выкупленных мест
        System.out.printf("Percentage: %.2f%%\n", percentage); //вывод процента выкупленных мест
        System.out.println("Current income: $" + ticketPrice); //общая стоимость выкупленных мест
        System.out.println("Total income: $" + CalcSumOfAllTickets(array.length, array[0].length)); //цена всех мест в зале
        goToMenu(array, purchasedTickets, ticketPrice);
    }

    public static int CalcSumOfAllTickets(int numberOfRows, int seatsInRow) { //метод вычисления общей стоимости мест в зале
        int sumOfAllTickets = 0;
        int maxSeatsForHighPrice = 60;
        int allSeats = numberOfRows * seatsInRow;
        if (allSeats < maxSeatsForHighPrice) {
            int allTicketPrice = 10;
            sumOfAllTickets = allSeats * allTicketPrice;
        }
        if (allSeats >= maxSeatsForHighPrice) {
            int highPriceTicket = 10;
            int lowPriceTicket = 8;
            int h = numberOfRows / 2;
            int l = numberOfRows - h;
            System.out.println("h " + h + ", l " + l);
            sumOfAllTickets = (h * seatsInRow * highPriceTicket) + (l * seatsInRow * lowPriceTicket);
        }
        return sumOfAllTickets;
    }
}
