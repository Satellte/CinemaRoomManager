package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        int purchasedTickets = 0;
        int ticketPrice = 0;
        start(purchasedTickets, ticketPrice);
    }
/**
 * Начало программы.
 * @param purchasedTickets - количество купленных билетов равно нулю
 * @param ticketPrice - стоимость всех купленных билетов равно нулю
 * Метод принимает количество рядов (numberOfRows) и количество мест в каждом ряду (seatsInRow),
 * проверяя, что бы небыло введено 0 или отрицательное значение. Затем создает массив размером количество рядов
 * умноженное на количество мест, и заполняет каждую ячейку массива буквой S. Затем переходит в метод Главное меню
 */

    public static void start(int purchasedTickets, int ticketPrice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of rows:\n");
        int numberOfRows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row:\n");
        int seatsInRow = scanner.nextInt();
        if ((numberOfRows <= 0) || (seatsInRow <= 0)) {
            System.out.println("Please, enter again");
            start(purchasedTickets, ticketPrice);
        } else {
            char[][] array = new char[numberOfRows][seatsInRow];
            for (int arrayR = 0; arrayR < numberOfRows; arrayR++) {
                for (int arrayS = 0; arrayS < seatsInRow; arrayS++) {
                    array[arrayR][arrayS] = 'S';
                }
            }
            goToMenu(array, purchasedTickets, ticketPrice);
        }
    }

    /**
     * Метод выводит на экран главное меню, затем принимает номер выбранного меню и переходит к необходимому методу.
     * @param array - получает массив мест в зале
     * @param ticketPrice - получает стоимость всех купленных билетов
     * @param purchasedTickets - получает количество купленных билетов
     * 1. переходит к методу вывода на печать массива мест (printMapOfSeats)
     * 2. переходит к методу покупки билетов (buyTicket), по окончании получает сумму билета и добавляет ее в общую
     * стоимость, затем увеличивает количество проданных билетов на 1
     * 3. переходит к методу подсчета статистики (showStatistic)
     * Любое другое число завершает программу. По окончании каждого метода выводится главное меню
     */
    public static void goToMenu(char[][] array, int purchasedTickets, int ticketPrice) {
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
                printMapOfSeats(array);
                goToMenu(array, purchasedTickets, ticketPrice);
                break;
            }
            case 2: {
                ticketPrice = ticketPrice + buyTicket (array);
                purchasedTickets++;
                goToMenu(array, purchasedTickets, ticketPrice);
                break;
            }
            case 3:{
                showStatistics(array, purchasedTickets, ticketPrice);
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Метод выводит на экран пронумерованный массив рядов и мест, заполненных буквами S (пустые места) и B (занятые)
     * @param array - получает массив мест в зале
     */
    public static void printMapOfSeats(char[][] array) {
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

    /**
     * Метод покупки билетов.
     * @param array - получает массив мест в зале
     * Принимает номер ряда и номер места в этом ряду.
     * Если вводится значение, превышающее количество рядов или мест выводит предупреждение и предлагает ввести новое
     * значение.
     * Если место уже куплено, то выводит предупреждение и предлагает ввести новое значение.
     * Если место свободное, то переходит к методу, вычисляющему и возвращающему стоимость билета, затем выводит на
     * экран стоимость.
     * @return sumOfTickets - возвращает стоимость купленного билета
     */
    public static int buyTicket(char[][] array) {
        Scanner scanner = new Scanner(System.in);
        int sumOfTicket = 0;
        System.out.print("Enter a row number:\n");
        int selectedNumberOfRow = scanner.nextInt();
        System.out.print("Enter a seat number in that row:\n");
        int selectedSeatInRow = scanner.nextInt();
        if ((selectedNumberOfRow > array.length) || (selectedSeatInRow > array[0].length)) {
            System.out.println("Wrong input!\n");
            buyTicket(array);
        } else if (array[selectedNumberOfRow - 1][selectedSeatInRow - 1] == 'B') {
            System.out.println("That ticket has already been purchased!\n");
            buyTicket(array);
        } else {
            array[selectedNumberOfRow - 1][selectedSeatInRow - 1] = 'B';
            sumOfTicket = calculateTicketPrice(array, selectedNumberOfRow);
            System.out.println("\nTicket price: $" + sumOfTicket);
            System.out.print("");
        }
        return  sumOfTicket;
    }

    /**
     * Метод выполняет подсчет мест и стоимость билета.
     * @param array - получает массив мест в зале
     * @param selectedNumberOfRow - получает номер ряда, где будет выкуплено место
     * Если количество мест 60 или меньше, то каждый билет стоит 10$
     * Если количество мест больше 60, то делит количество рядов пополам. Первая часть рядов стоит 10$ за место. Вторая
     * часть стоит 8$ за место.
     * @return price - возвращает стоимость купленного билета
     */
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

    /**
     * Метод подсчитывает и выводит статистику.
     * @param array - получает массив мест в зале
     * @param purchasedTickets - получает количество купленных билетов
     * @param ticketPrice - получает общую стоимость выкупленных билетов
     * Выводит количество проданных мест
     * Подсчитывает и выводит процент выкупленных мест
     * Выводит общую стоимость выкупленных мест
     * Переходит к методу подсчета общей стоимости всех мест в зале и затем выводит эту стоимость на печать
     */
    public static void showStatistics(char[][] array, int purchasedTickets, int ticketPrice) {
        System.out.println("Number of purchased tickets: "+ purchasedTickets);
        float percentage, a, b;
        a = purchasedTickets;
        b = array.length * array[0].length;
        percentage = a * 100 / b;
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + ticketPrice);
        System.out.println("Total income: $" + CalcSumOfAllTickets(array.length, array[0].length));
        goToMenu(array, purchasedTickets, ticketPrice);
    }

    /**
     * Метод подсчета общей стоимости всех мест в зале. Получает следующие переменные
     * @param numberOfRows - количество рядов
     * @param seatsInRow - количество мест в ряду
     * Если общее количество мест меньше 60, то каждое место умножается на 10.
     * Если общее количество больше 60, то каждое место в половине зала умножается на 10,
     * места в другой половине умножаются на 8.
     * @return sumOfAllTickets - возвращает общую стоимость всех мест в зале
     */
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
