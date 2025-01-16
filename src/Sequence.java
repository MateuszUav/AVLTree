public class Sequence {
     final Tree tree;
     int pointer;  // This is the 0-based index that simulates P

    public Sequence(int[] initialSequence) {
        this.tree = new Tree();
        this.pointer = 0;
        // Build the tree from the initial array
        for (int j : initialSequence) {
            tree.insertAt(tree.size(), j);
        }
    }

    public void doOperation() {
        if (tree.size() == 0) return;

        int currentValue = getValueAt(pointer);

        if (currentValue % 2 == 1) {
            doAdd(currentValue);
        } else {
            doDelete();
        }
    }

    private void doAdd(int x) {
        if (tree.size() == 0) return;

        int sz = tree.size();
        int insertPos;
        if (pointer == sz -1){
            insertPos = sz;
        }else{
            insertPos = (pointer +1) %sz;
        }
        tree.insertAt(insertPos, x - 1);

        // Now the size has changed by +1
        sz = tree.size();
        // Move pointer by x steps, mod the new size
        pointer = (pointer + x) % sz;
    }

    private void doDelete() {
        if (tree.size() == 0) return;

        int sz = tree.size();
        pointer = (pointer) % (sz);
        int removePos;
        if (pointer+1 == sz ){
             removePos = 0;
        }else {
             removePos  =(pointer+1) % sz;
        }

        int x = tree.get(removePos);

        tree.removeAt(removePos);


        // Now the size has changed by -1
         sz = tree.size();
        if (sz > 0) {
            // If removePos was before the pointer in the cyclic sequence,
            // decrement the pointer to account for the shift caused by removal
            if (removePos <= pointer) {
                pointer = (pointer - 1 + sz) % sz;
            }

            pointer = (pointer + x) % (sz);
        } else {
            pointer = 0; // Reset pointer if tree becomes empty
        }

    }

    private int getValueAt(int idx) {
        if (tree.size() == 0) {
            throw new IllegalStateException("Tree is empty.");
        }
        idx = idx % tree.size();
        return tree.get(idx);
    }

    public void performKOperations(int k) {
        for (int i = 0; i < k; i++) {
            doOperation();
        }
    }

    public String toCyclicString() {
        if (tree.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        int sz = tree.size();

        for (int i = 0; i < sz; i++) {
            int idx = (pointer + i) % sz;
            sb.append(tree.get(idx));
            if (i < sz - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }


    // Debugging helper
    private void debug(String message, Object... args) {
        System.out.print("DEBUG: " + message + " - ");
        for (Object arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
