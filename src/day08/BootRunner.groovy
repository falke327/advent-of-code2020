package day08

class BootRunner {
    static final int OPERATION = 0
    static final int VALUE = 1
    private int acc
    private int pointer

    void run(List<String> bootScript) {
        this.acc = 0
        this.pointer = 0
        List<Integer> usedInstructions = []

        while (!usedInstructions.contains(this.pointer) && this.pointer < bootScript.size()) {
            usedInstructions.add(this.pointer)
            List<String> currentInstruction = bootScript.get(this.pointer).split(" ")

            switch (currentInstruction.get(OPERATION)) {
                case 'nop':
                    this.pointer++
                    break
                case 'acc':
                    int valueModifier = currentInstruction.get(VALUE) as int
                    this.acc += valueModifier
                    this.pointer++
                    break
                case 'jmp':
                    int jumpStep = currentInstruction.get(VALUE) as int
                    this.pointer += jumpStep
                    break
            }
        }
    }

    int getAcc() {
        return this.acc
    }

    int getPointer() {
        return this.pointer
    }
}
