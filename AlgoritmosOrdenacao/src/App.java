import java.util.Arrays;
import java.util.Random;

public class App {

    // Função de ordenação Bubble Sort
    public static int[] bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    // Função de ordenação Counting Sort
    public static int[] countingSort(int[] arr) {
        int n = arr.length;

        // Encontrar o valor máximo no array
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // Criar um array de contagem
        int[] count = new int[max + 1];

        // Contar as ocorrências de cada elemento
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

         // Reconstruir o array ordenado
        int[] sortedArray = new int[n];
        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                sortedArray[index++] = i;
                count[i]--;
            }
        }
         return sortedArray;
    }

    // Função de ordenação Insertion Sort
    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && key < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        return arr;
    }

    // Função de ordenação Merge Sort
    public static int[] mergeSort(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] leftHalf = Arrays.copyOfRange(arr, 0, mid);
            int[] rightHalf = Arrays.copyOfRange(arr, mid, arr.length);
    
            leftHalf = mergeSort(leftHalf);
            rightHalf = mergeSort(rightHalf);
    
            int i = 0, j = 0, k = 0;
            while (i < leftHalf.length && j < rightHalf.length) {
                if (leftHalf[i] < rightHalf[j]) {
                    arr[k] = leftHalf[i];
                    i++;
                } else {
                    arr[k] = rightHalf[j];
                    j++;
                }
                k++;
            }
    
            while (i < leftHalf.length) {
                arr[k] = leftHalf[i];
                i++;
                k++;
            }
    
            while (j < rightHalf.length) {
                arr[k] = rightHalf[j];
                j++;
                k++;
            }
        }
        return arr.clone();
    }

    // Função de ordenação Quick Sort
    public static int[] quickSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }
    
        int pivot = arr[arr.length / 2];
        int[] left = Arrays.stream(arr).filter(x -> x < pivot).toArray();
        int[] middle = Arrays.stream(arr).filter(x -> x == pivot).toArray();
        int[] right = Arrays.stream(arr).filter(x -> x > pivot).toArray();
    
        return concatenateArrays(quickSort(left), middle, quickSort(right));
    }

    // Função de ordenação Selection Sort
    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return arr;
    }

    // Função para medir o tempo de execução de cada algoritmo
    public static long measureTime(SortingFunction function, int[] arr) {
        long startTime = System.nanoTime();
        function.sort(arr.clone());
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;
    
        double seconds = (double) timeTaken / 1_000_000_000.0;
        String formattedTime = String.format("%.7f", seconds);

        System.out.println(formattedTime + " segundos");
    
        return timeTaken;
    }


    // Função para gerar um array de números aleatórios e únicos
    public static int[] generateRandomArray(int size) {
        int[] dataSet = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            dataSet[i] = random.nextInt(size + 1);
        }

        return dataSet;
    }

    // Função auxiliar para concatenar arrays
    private static int[] concatenateArrays(int[] left, int[] middle, int[] right) {
        int[] result = new int[left.length + middle.length + right.length];
        System.arraycopy(left, 0, result, 0, left.length);
        System.arraycopy(middle, 0, result, left.length, middle.length);
        System.arraycopy(right, 0, result, left.length + middle.length, right.length);
        return result;
    }

    // Função para encontrar o algoritmo mais rápido
    private static String findFastestAlgorithm(long... times) {
        long minTime = Long.MAX_VALUE;
        String fastestAlgorithm = "";

        String[] algorithms = {"Bubble Sort", "Counting Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Selection Sort"};

        for (int i = 0; i < times.length; i++) {
            if (times[i] < minTime) {
                minTime = times[i];
                fastestAlgorithm = algorithms[i];
            }
        }

        return fastestAlgorithm;
    }

    public static void main(String[] args) {
        int[] randomArray = generateRandomArray(200000);

        // Tamanhos dos subarrays a serem testados
        int[] sizes = {100, 500, 1000, 5000, 30000, 80000, 100000, 150000, 200000};

        // Testa cada algoritmo para cada tamanho de subarray
        for (int size : sizes) {
            int[] subarray = Arrays.copyOfRange(randomArray, 0, size);
            System.out.println("\nTamanho do Subarray: " + size);
            

            // Bubble Sort
            System.out.print("Bubble Sort: ");
            long bubbleSortTime = measureTime(App::bubbleSort, subarray);
            
            // Counting Sort
            System.out.print("Counting Sort: ");
            long countingSortTime = measureTime(App::countingSort, subarray);

            // Insertion Sort
            System.out.print("Insertion Sort: ");
            long insertionSortTime = measureTime(App::insertionSort, subarray);

            // Merge Sort
            System.out.print("Merge Sort: ");
            long mergeSortTime = measureTime(App::mergeSort, subarray);

            // Quick Sort
            System.out.print("Quick Sort: ");
            long quickSortTime = measureTime(App::quickSort, subarray);

            // Selection Sort
            System.out.print("Selection Sort: ");
            long selectionSortTime = measureTime(App::selectionSort, subarray);

            // Comparar os tempos e identifica o algoritmo mais rápido
            String fastestAlgorithm = findFastestAlgorithm(
                    bubbleSortTime, countingSortTime, insertionSortTime,
                    mergeSortTime, quickSortTime, selectionSortTime);

            System.out.println("O algoritmo mais rápido foi: " + fastestAlgorithm);
        }
    }

    interface SortingFunction {
        int[] sort(int[] arr);
    }
}