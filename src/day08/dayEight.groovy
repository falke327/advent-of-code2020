package day08

List<String> testInput = new File("testEight.txt").readLines()
assert getLastAccBeforeLoop(testInput) == 5

List<String> input = new File("inputEight.txt").readLines()
int result1 = getLastAccBeforeLoop(input)
println("Last Value before loop was $result1")

/**
 * <p>Performs the given Instructions until the first loop and returns the acc value.</p>
 */
static int getLastAccBeforeLoop(List<String> input) {
    int acc = 0
    int pointer = 0
    List<Integer> usedInstructions = []

    while (!usedInstructions.contains(pointer)) {
        usedInstructions.add(pointer)
        List<String> currentInstruction = input.get(pointer).split(" ")

        switch (currentInstruction.get(0)) {
            case 'nop':
                pointer++
                break
            case 'acc':
                int valueModifier = currentInstruction.get(1) as int
                acc += valueModifier
                pointer++
                break
            case 'jmp':
                int jumpStep = currentInstruction.get(1) as int
                pointer += jumpStep
                break
        }
    }

    return acc
}