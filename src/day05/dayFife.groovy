package day05

import java.util.stream.Collectors

List<String> input = new File("input.txt").readLines()
List<Integer> seatList = input.stream()
        .map({ line ->
            int row = getRow(line)
            int col = getCol(line)
            return row * 8 + col
        })
        .collect(Collectors.toList())
        .sort()

int highestSeat = seatList.last()
println("Highest available seat is $highestSeat")

int first = seatList.first()
int last = seatList.last()
// subtract used seats from all available seats should result in a list containing only one seat that has to be mine
int mySeat = (first..last).toList().minus(seatList).first()
println("My seat is $mySeat")


int getRow(String input) {
    return findPosition(64, input, 0, 7, "B")
}

int getCol(String input) {
    return findPosition(4, input, 7, input.length(), "R")
}

int findPosition(int stepsize, String phrase, int startIndex, int endIndex, String upperDigit) {
    String phrasePosition = phrase.substring(startIndex, endIndex)

    int pos = 0
    phrasePosition.each {digit ->
        //println("Current digit is + digit)
        if (digit == upperDigit) {
            pos += stepsize
        }
        stepsize = (int) (stepsize / 2)
    }

    return pos
}
