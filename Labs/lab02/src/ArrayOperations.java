public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        } else {
            for (int i = pos; i < values.length - 1; i++) {
                values[i] = values[i + 1];
            }
            values[values.length - 1] = 0;
        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        } else {
            int[] result = new int[values.length];
            for (int i = values.length - 1; i >= pos; i--) {
                result[i] = values[i-1];
            }
            values[pos] = newInt;

        }
    }

    /**
     * Returns a new array consisting of the elements of A followed by the
     * the elements of B.
     */
    public static int[] catenate(int[] A, int[] B) {
        int[] dest_arr = new int[A.length + B.length];
        for (int i = 0; i < A.length; i++) {
            dest_arr[i] = A[i];
        }
        for (int i = 0; i < B.length; i++) {
            dest_arr[A.length + i] = B[i];
        }
        return dest_arr;
    }
}
