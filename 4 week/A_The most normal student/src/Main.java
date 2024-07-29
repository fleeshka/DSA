// Ulyana Chaikouskaya

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);

        int studentNumber = scan.nextInt();
        int []scoresArray = new int[studentNumber];

        HashMap<Integer, String> hashMap = new HashMap<>(1001);
        for (int i = 0; i < studentNumber; i++){
            int score = scan.nextInt();
            scoresArray[i] = score;
            String firsName = scan.next();
            String lastName = scan.next();
            hashMap.put(score, firsName + " " + lastName);
        }



        System.out.println(hashMap.get(findMedianOfMedians(scoresArray)));
        findMedianOfMedians(scoresArray);



        }

    private static int findMedianOfMedians(int[] arr) {
        int median = findMedianUtil(arr, (arr.length)/2+1, 0, arr.length-1);
        return median;
    }

    private static int findMedianUtil(int[] arr, int k, int low, int high) {
        int m = partition(arr, low, high);
        int length = m - low + 1;
        if(length == k){
            return arr[m];
        }

        if(length > k){
            return findMedianUtil(arr, k, low, m-1);
        }else{
            return findMedianUtil(arr, k-length, m+1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {

        int pivot = getPivotValue(arr, low, high);
        while (low < high){
            while (arr[low]<pivot){
                low++;
            }
            while (arr[high] > pivot){
                high--;
            }

            if(arr[low] == arr[high]){
                low++;
            }

            //swap
            if (low < high){
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
            }

        }

        return high;

    }

    private static int getPivotValue(int[] arr, int low, int high) {
        if (high-low+1 <= 5){
            insertSort(arr);
            return arr[arr.length/2];
        }

        int[] temp = null;
        int medians[] = new int [(int)Math.ceil((double)(high-low+1)/5)];

        int medianIndex = 0;
        while(high >= low){
            temp = new int[Math.min(5, high-low+1)];
            for (int i = 0; i < temp.length  && low <= high; i++){
                temp[i] = arr[low];
                low++;
            }

            insertSort(temp);

            medians[medianIndex] = temp[temp.length/2];
            medianIndex++;

        }


        return getPivotValue(medians, 0 , medians.length-1);

    }

    private static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

}


interface Map <K, V> {
    V get(K key);
    void put(K key, V value);
    void remove(K key);
    int size();
    boolean isEmpty();
    ArrayList<KeyValueTable<K, V>> entrySet();

}

class KeyValueTable<K, V>{
    K key;
    V value;
    public KeyValueTable (K key, V value){
        this.key = key;
        this.value = value;

    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

class HashMap<K, V> implements Map<K, V>{

    List<KeyValueTable<K, V>>[] hashTable;
    int capacity;
    int mapSize;


    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode() % this.capacity);
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                return kv.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        // determine the place of our key
        int index = Math.abs(key.hashCode() % this.capacity);
        for (KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                kv.value = value;
                return;
            }
        }

        this.hashTable[index].add(
                new KeyValueTable<>(key, value)
        );
        ++this.mapSize;
    }

    @Override
    public void remove(Object key) {
        int index = Math.abs(this.hashCode() % this.capacity);
        for(KeyValueTable<K, V> kv : this.hashTable[index]){
            if (kv.key.equals(key)){
                kv.value = null;
                break;
            }
        }

    }

    @Override
    public int size() {
        return this.mapSize;
    }

    @Override
    public boolean isEmpty() {
        return (this.mapSize == 0);
    }

    @Override
    public ArrayList<KeyValueTable<K, V>> entrySet() {
        ArrayList<KeyValueTable<K, V>> entrySet = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            if (this.hashTable[i] != null) {
                List<KeyValueTable<K, V>> linkedList = this.hashTable[i];
                for (KeyValueTable<K, V> kv : linkedList) {
                    entrySet.add(kv);
                }
            }
        }
        return entrySet;
    }

    public HashMap(int capacity){
        this.capacity = capacity;
        this.mapSize = 0;
        hashTable = new List[capacity];
        for (int i = 0; i < this.capacity; ++i){
            this.hashTable[i] = new LinkedList<>();
        }

    }

    public List<KeyValueTable<K, V>>[] getHashTable() {
        return this.hashTable;
    }

}