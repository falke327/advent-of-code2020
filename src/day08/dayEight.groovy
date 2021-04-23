package day08

List<String> testInput = new File("testEight.txt").readLines()
assert getLastAccBeforeLoop(testInput) == 5
assert lastAccWithFixOnTheFly(testInput) == 8

List<String> input = new File("inputEight.txt").readLines()
int result1 = getLastAccBeforeLoop(input)
println("Last Value before loop was $result1")
int result2 = lastAccWithFixOnTheFly(input)
println("Last Value in the fixed run was $result2")

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

/**
 * <p>Bruteforces the given instructions with fixing one jmp or nop in each run.
 * Returns the acc value for the valid run.</p>
 */
static int lastAccWithFixOnTheFly(List<String> input) {
    for (int i = 0; i < input.size(); i++) {
        List<String> modifiedInput = new ArrayList<>(input)
        if (modifiedInput[i].startsWith("jmp")) {
            modifiedInput[i] = modifiedInput[i].replace("jmp", "nop")
        } else if (modifiedInput[i].startsWith("nop")) {
            modifiedInput[i] = modifiedInput[i].replace("nop", "jmp")
        } else {
            continue
        }

        int acc = 0
        int pointer = 0
        List<Integer> usedInstructions = []

        while (!usedInstructions.contains(pointer) && pointer < input.size()) {
            usedInstructions.add(pointer)
            List<String> currentInstruction = modifiedInput.get(pointer).split(" ")

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

        if (pointer == input.size()) {
            return acc
        }
    }
    return -1
}
