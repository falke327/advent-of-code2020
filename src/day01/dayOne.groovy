package day01

List<Integer> testInput = [1721, 979, 366, 299, 675, 1456]
int testResult1 = get2020ProductOfTwo(testInput)
assert testResult1 == 514579
int testResult2 = get2020ProductOfThree(testInput)
assert testResult2 == 241861950


List<Integer> input = new File("inputOne.txt").readLines().collect{line -> line.toInteger()}
int result1 = get2020ProductOfTwo(input)
println("Solution for first Puzzle is $result1")
int result2 = get2020ProductOfThree(input)
println("Solution for first Puzzle is $result2")

static int get2020ProductOfTwo(List<Integer> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = i; j < input.size(); j++) {
            int first = input.get(i)
            int second = input.get(j)

            if (first + second == 2020) {
                return first * second
            }
        }
    }
    return 0
}

static int get2020ProductOfThree(List<Integer> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = i; j < input.size(); j++) {
            for (int k = j; k < input.size(); k++) {
                int first = input.get(i)
                int second = input.get(j)
                int third = input.get(k)

                if (first + second + third == 2020) {
                    return first * second * third
                }
            }
        }
    }
    return 0
}
